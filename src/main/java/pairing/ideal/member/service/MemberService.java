package pairing.ideal.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pairing.ideal.member.dto.MemberIconDTO;
import pairing.ideal.member.dto.ProfileDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import pairing.ideal.member.entity.Hobby;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.entity.Photo;
import pairing.ideal.member.repository.HobbyRepository;
import pairing.ideal.member.repository.MemberRepository;
import pairing.ideal.member.repository.PhotoRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final HobbyRepository hobbyRepository;
    private final PhotoRepository photoRepository;
    private final RestTemplate restTemplate;
    @Value("${cloud.ncp.storage.end-point}")
    private String storageEndPoint;
    @Value("${cloud.ncp.storage.bucket-name-member}")
    private String storageMemberBucketName;

    @Transactional
    public ProfileDTO postProfile(ProfileDTO profileDTO, String email) {

        Member byEmail = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        // 기존의 Hobby와 Photo를 조회
        Hobby hobby = hobbyRepository.findByMember(byEmail)
                .orElse(new Hobby(byEmail, profileDTO.getHobby()));
        hobby.updateHobby(profileDTO.getHobby()); // 새로운 정보로 업데이트
        hobbyRepository.save(hobby);

        Photo photo = photoRepository.findByMember(byEmail)
                .orElse(new Photo(byEmail, profileDTO.getImages()));

        List<String> photos = new ArrayList<>();
        for (String image: profileDTO.getImages()) {
            photos.add(generateS3KeyFormUrl(image));
        }

        photo.updatePhoto(photos); // 새로운 정보로 업데이트
        photoRepository.save(photo);

        Member detail = byEmail.createDetail(hobby, photo);
        Member member = detail.addInfo(profileDTO);
        memberRepository.save(member);
        return new ProfileDTO().from(member, hobby, photo);
    }

    // S3 Key 생성
    public String generateS3KeyFormUrl(String imageUrl) {
        return storageEndPoint + "/" + storageMemberBucketName + "/" + imageUrl;
    }


    public ProfileDTO getProfile(String email) {
        Member byEmail = findByEmail(email);
        Hobby hobby = hobbyRepository.findByMember(byEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hobby"));
        Photo photo = photoRepository.findByMember(byEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid photo"));
        ProfileDTO profileDTO = new ProfileDTO();
            return profileDTO.from(byEmail, hobby, photo);
    }

    public ProfileDTO getOtherProfile(long userId) {
        Member byId = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member"));
        calHeart(byId, 1);
        return getProfile(byId.getEmail());
    }

    public String deleteProfile(String email) {
        Member byEmail = findByEmail(email);
        memberRepository.delete(byEmail);
        return "success";
    }

    @Async
    public void calHeart(Member member, long score) {
        member.calHeart(score);
        memberRepository.save(member);
    }

    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));
    }

    public boolean compareImage(MultipartFile file, Member member) throws IOException {
        String url = "http://localhost:8000/member/face/";

        Photo photo = photoRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("Invalid photo"));
        List<String> urls = photo.getPhoto();

        List<String> fullUrls = urls.stream()
                .map(photoUrl -> storageEndPoint + "/" + storageMemberBucketName + "/" + photoUrl)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        requestBody.add("urls", String.join(",", fullUrls));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is3xxRedirection()) {
            String redirectUrl = response.getHeaders().getLocation().toString();
            response = restTemplate.exchange(redirectUrl, HttpMethod.POST, requestEntity, String.class);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.path("is_same_person").asBoolean();
        } else {
            throw new RuntimeException("Failed to compare images: " + response.getStatusCode());
        }
    }

    public MemberIconDTO getMemberIcon(long userId) {
        Member byId = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member"));
        return MemberIconDTO.fromEntity(byId);
    }
}

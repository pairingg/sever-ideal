package pairing.ideal.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pairing.ideal.member.dto.ProfileDTO;
import pairing.ideal.member.dto.requset.CompareFace;
import pairing.ideal.member.entity.Hobby;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.entity.Photo;
import pairing.ideal.member.repository.HobbyRepository;
import pairing.ideal.member.repository.MemberRepository;
import pairing.ideal.member.repository.PhotoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String postProfile(ProfileDTO profileDTO, String email){
        Member byEmail = findByEmail(email);
        Hobby hobby = new Hobby(byEmail, profileDTO.getHobby());
        hobbyRepository.save(hobby);

        Photo photo = new Photo(byEmail, profileDTO.getImages());
        photoRepository.save(photo);

        Member detail = byEmail.createDetail(hobby, photo);
        memberRepository.save(detail);
        return "success";
    }

    public ProfileDTO getProfile(String email){
        Member byEmail = findByEmail(email);
        Hobby hobby = hobbyRepository.findByMember(byEmail)
                .orElseThrow(()->new IllegalArgumentException("Invalid hobby"));
        Photo photo = photoRepository.findByMember(byEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid photo"));
        ProfileDTO profileDTO = new ProfileDTO();
        return profileDTO.from(byEmail, hobby, photo, storageEndPoint, storageMemberBucketName);
    }

    public ProfileDTO getOtherProfile(long userId){
        Member byId = memberRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Invalid member"));
        calHeart(byId, 1);
        return getProfile(byId.getEmail());
    }

    public String deleteProfile(String email){
        Member byEmail = findByEmail(email);
        memberRepository.delete(byEmail);
        return "success";
    }

    @Async
    public void calHeart(Member member,long score){
        member.calHeart(score);
        memberRepository.save(member);
    }

    private Member findByEmail(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));
    }

    public String compareImage(CompareFace compareFace, Member member) {
        String url = "http://face-compare:8000/member/face"; // 다른 컨테이너 이름을 사용

        Photo photo = photoRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("Invalid photo"));
        List<String> urls = photo.getPhoto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("file", compareFace.getFileData());
        requestBody.put("urls", urls);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // 응답 처리
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody(); // 성공 시 응답 본문 반환
        } else {
            throw new RuntimeException("Failed to compare images: " + response.getStatusCode());
        }
    }
}

package pairing.ideal.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pairing.ideal.member.dto.ProfileDTO;
import pairing.ideal.member.entity.Hobby;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.entity.Photo;
import pairing.ideal.member.repository.HobbyRepository;
import pairing.ideal.member.repository.MemberRepository;
import pairing.ideal.member.repository.PhotoRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final HobbyRepository hobbyRepository;
    private final PhotoRepository photoRepository;

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
        return profileDTO.from(byEmail, hobby, photo);
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
}

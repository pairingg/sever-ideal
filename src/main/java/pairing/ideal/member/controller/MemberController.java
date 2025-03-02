package pairing.ideal.member.controller;

import com.amazonaws.HttpMethod;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.headers.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pairing.ideal.member.config.S3Config;
import pairing.ideal.member.dto.MemberIconDTO;
import pairing.ideal.member.dto.ProfileDTO;
import pairing.ideal.member.dto.response.DrinkAndSmokeResponse;
import pairing.ideal.member.entity.CustomUserDetails;
import pairing.ideal.member.service.MemberService;
import pairing.ideal.member.staticData.entity.HobbyList;
import pairing.ideal.member.staticData.repository.HobbyListRepository;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final HobbyListRepository hobbyListRepository;
    private final S3Config s3Config;

    //    @GetMapping("/profile/region")
//    public Member profile(@RequestBody ProfileCreate profileCreate) {
//
//    }
    @GetMapping("/profile/hobby")
    public List<HobbyList> getHobbyList(){
        return hobbyListRepository.findAll();
    }

    @GetMapping("/profile/drinkandsmoke")
    public DrinkAndSmokeResponse getDrinkAndSmoke(){
        DrinkAndSmokeResponse drinkAndSmokeResponse = new DrinkAndSmokeResponse();
        return drinkAndSmokeResponse;
    }

    @PostMapping("/profile")
    public ResponseEntity<String> postProfile(@RequestBody ProfileDTO profileDTO,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // Photo 엔티티가 이미 존재하는지 확인
//        if (memberService.photoExists(customUserDetails.getMember())) {
//            return ResponseEntity.badRequest().body("Photo already exists for this member.");
//        }
        return ResponseEntity.ok(memberService.postProfile(profileDTO, customUserDetails.getMember().getEmail()));
    }

    @GetMapping("/profile")
    public ProfileDTO getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return memberService.getProfile(customUserDetails.getMember().getEmail());
    }

    @GetMapping("/profile/{userId}")
    public ProfileDTO getOtherProfile(@PathVariable long userId) {
        return memberService.getOtherProfile(userId);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> putProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(memberService.postProfile(profileDTO, customUserDetails.getMember().getEmail()));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(memberService.deleteProfile(customUserDetails.getMember().getEmail()));
    }

    @GetMapping("presigned-url")
    public Map<String, String> getPresignedUrl(@RequestParam String fileName, @RequestParam String contentType) {
        String presignedUrl = s3Config.generatePresignedUrl(fileName, HttpMethod.PUT, 600000, contentType);
        return Map.of("url", presignedUrl);
    }

    @PostMapping("face")
    public boolean compareImage(@RequestParam("file") MultipartFile file,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        return true;
//        return memberService.compareImage(file, customUserDetails.getMember());
    }

    @GetMapping("icon")
    public MemberIconDTO getMemberIcon(@RequestHeader("X-Authorization-memberId") long userId){
        return memberService.getMemberIcon(userId);
    }
}

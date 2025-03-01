package pairing.ideal.member.controller;

import com.amazonaws.HttpMethod;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pairing.ideal.member.config.S3Config;
import pairing.ideal.member.dto.ProfileDTO;
import pairing.ideal.member.dto.requset.CompareFace;
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
    public Map<String, String> getPresignedUrl(@RequestParam String fileName) {
        String presignedUrl = s3Config.generatePresignedUrl(fileName, HttpMethod.PUT, 600000);
        return Map.of("url", presignedUrl);
    }

    @PostMapping("face")
    public String compareImage(@RequestBody CompareFace compareFace,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return memberService.compareImage(compareFace, customUserDetails.getMember());
    }
}

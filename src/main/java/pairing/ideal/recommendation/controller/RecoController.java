package pairing.ideal.recommendation.controller;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pairing.ideal.member.entity.CustomUserDetails;
import pairing.ideal.member.entity.Member;
import pairing.ideal.recommendation.dto.request.EnrollRequest;
import pairing.ideal.recommendation.dto.response.KeywordResponse;
import pairing.ideal.recommendation.dto.response.RecoResponse;
import pairing.ideal.recommendation.entity.IdealType;
import pairing.ideal.recommendation.service.RecoService;

@RestController
@RequestMapping("ideal")
@RequiredArgsConstructor
public class RecoController {
    private final RecoService recoService;

    /**
     * 이상형 등록 시 정보 입력
     */
    @PostMapping
    public IdealType enrollIdeal(@RequestBody EnrollRequest enrollRequest,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return recoService.enrollIdeal(enrollRequest, customUserDetails.getMember());
    }

    /* 기본 추천 */
    /* 메인 페이지에서 바로 뜨는데 2명 추천 결과랑, 키워드 리스트 보내줘야함 */
    @GetMapping("/recommend")
    public List<Member> getRecoResults(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return recoService.getRecoResults(customUserDetails.getMember());

    }


    @GetMapping("/recommend/keyword")
    public List<KeywordResponse> getKeyword(){
        return recoService.getRecoKeywords();
    }
//
//    /* 키워드 추천 */
//    @GetMapping("/recommend/{keywordId")
//    public List<RecoResponse> getRecoResults(@PathVariable String keywordId) {
//        return recoService.getKeywordResults;
//    }

}

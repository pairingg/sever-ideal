package pairing.ideal.recommendation.controller;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pairing.ideal.recommendation.dto.response.RecoResponse;
import pairing.ideal.recommendation.service.RecoService;

@RestController
@RequestMapping("ideal")
@RequiredArgsConstructor
public class RecoController {
    private final RecoService recoService;

    /* 기본 추천 */
    /* 메인 페이지에서 바로 뜨는데 2명 추천 결과랑, 키워드 리스트 보내줘야함 */
//    @GetMapping("/recommend")
//    public List<RecoResponse> getRecoResults() {
//        return recoService.getRecoResults;
//
//    }
//
//    /* 키워드 추천 */
//    @GetMapping("/recommend/{keywordId")
//    public List<RecoResponse> getRecoResults(@PathVariable String keywordId) {
//        return recoService.getKeywordResults;
//    }

}

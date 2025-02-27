package pairing.ideal.recommendation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pairing.ideal.recommendation.dto.response.KeywordResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecoServiceTest {

    @Autowired
    private RecoService recoService;

    @Test
    @DisplayName("키워드 목록 제공 예시")
    void recoServiceTest() {
        List<KeywordResponse> recoKeywords = recoService.getRecoKeywords();
        recoKeywords.forEach(System.out::println);
    }
}
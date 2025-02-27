package pairing.ideal.recommendation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordResponse {
    private String keyword;
    private String keywordIconUrl;

    public static List<KeywordResponse> getKeywordList() {
        List<KeywordResponse> keywordResponseList = new ArrayList<>();
        keywordResponseList.add(new KeywordResponse("같은 취미", "https://kr.object.ncloudstorage.com/pairing-static/%EA%B0%99%EC%9D%80%20%EC%B7%A8%EB%AF%B8.svg"));
        keywordResponseList.add(new KeywordResponse("같은 위치", "https://kr.object.ncloudstorage.com/pairing-static/%EC%9C%84%EC%B9%98.svg"));
        keywordResponseList.add(new KeywordResponse("연상", "https://kr.object.ncloudstorage.com/pairing-static/%EC%97%B0%EC%83%81.svg"));
        keywordResponseList.add(new KeywordResponse("연하", "https://kr.object.ncloudstorage.com/pairing-static/%EC%97%B0%ED%95%98.svg"));
        keywordResponseList.add(new KeywordResponse("동갑", "https://kr.object.ncloudstorage.com/pairing-static/%EB%8F%99%EA%B0%91.svg"));
        keywordResponseList.add(new KeywordResponse("같은 성별", "https://kr.object.ncloudstorage.com/pairing-static/%EC%84%B1%EA%B2%A9.svg"));
        return keywordResponseList;
    }

    @Override
    public String toString() {
        return "KeywordResponse{" +
                "keyword='" + keyword + '\'' +
                ", keywordIconUrl='" + keywordIconUrl + '\'' +
                '}';
    }
}

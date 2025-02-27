package pairing.ideal.recommendation.dto.response;

import java.net.URL;
import java.util.List;
import pairing.ideal.member.entity.Member;

/* 추천 결과 */
/* 이거 2개 리스트로 만들어서 반환 */
public record RecoResponse(
        String name,
        int age,
        String city,
        String district,
        List<URL> images
) {

    public static RecoResponse fromEntity(Member member) {
        return new RecoResponse(
                member.getName(),
                member.getAge(),
                member.getCity(),
                member.getDistrict(),
                member.getPhoto().getPhoto()
        );
    }

}

//export interface idealRecommendList {
//  name: string;
//  age: number;
//  city: string;
//  district: string;
//  images: string[];
//}
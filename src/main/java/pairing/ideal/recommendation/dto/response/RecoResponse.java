package pairing.ideal.recommendation.dto.response;

/* 추천 결과 */
/* 이거 2개 리스트로 만들어서 반환 */
public record RecoResponse(
        String name,
        int age,
        String city,
        String district,
        String images
) {

}


//export interface idealRecommendList {
//  name: string;
//  age: number;
//  city: string;
//  district: string;
//  images: string[];
//}
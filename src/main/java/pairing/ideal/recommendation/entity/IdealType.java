package pairing.ideal.recommendation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ideal_type")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/* 이상형 정보 */
public class IdealType {

    @Id
    @Column(name = "ideal_type_id")
    private Long idealTypeId;

    private String mbti;

    private String city;

    private String district;

}

//export interface idealTypeContent {
//    mbti?: string[];
//    address?: {
//        city: string;
//        district: string;
//    }[];
//    age?: {
//        min: number;
//        max: number;
//    };
//    hobby?: string[]; ----> 이거는 피그마에 없음
//    drink?: string;
//    smoke?: string;
//}

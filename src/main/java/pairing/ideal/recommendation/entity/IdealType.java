package pairing.ideal.recommendation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pairing.ideal.member.common.Drinking;
import pairing.ideal.member.common.Smoking;
import pairing.ideal.member.converter.StringListConverter;
import pairing.ideal.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idealTypeId;

    @Convert(converter = StringListConverter.class)
    @Builder.Default
    private List<String> mbti = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<AddressEntity> address = new ArrayList<>();

    private int ageStart;

    private int ageEnd;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    private List<String> hobby = new ArrayList<>();

    private Drinking drink;

    private Smoking smoke;

    @OneToOne
    private Member member;
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

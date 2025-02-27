package pairing.ideal.recommendation.dto.request;

import lombok.Data;
import lombok.Getter;
import pairing.ideal.member.common.Drinking;
import pairing.ideal.member.common.Smoking;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.staticData.entity.HobbyList;
import pairing.ideal.recommendation.entity.AddressEntity;
import pairing.ideal.recommendation.entity.IdealType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EnrollRequest {
    private List<String> mbti;
    private List<Address> address;
    private AgeRange age;
    List<String> hobby = new ArrayList<>();
    Drinking drink;
    Smoking smoke;


    @Data
    public static class Address {
        private String city;
        private String district;
    }

    @Data
    public static class AgeRange {
        private int min;
        private int max;
    }

    public IdealType toEntity(Member member) {
        List<AddressEntity> addressList = new ArrayList<>();
        address.forEach(address -> {
            AddressEntity entityAddress = new AddressEntity();
            entityAddress.setCity(address.city);
            entityAddress.setDistrict(address.district);
            addressList.add(entityAddress);
        });
        return IdealType.builder()
                .mbti(mbti)
                .address(addressList)
                .ageStart(age.getMin())
                .ageEnd(age.getMax())
                .hobby(hobby)
                .drink(drink)
                .smoke(smoke)
                .build();
    }
}

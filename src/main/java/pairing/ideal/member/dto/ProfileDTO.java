package pairing.ideal.member.dto;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import pairing.ideal.member.common.Drinking;
import pairing.ideal.member.common.Gender;
import pairing.ideal.member.common.Smoking;
import pairing.ideal.member.entity.Hobby;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.entity.Photo;

@Getter
public class ProfileDTO {
    private String name;

    private int age;

    private Gender gender;

    private LocalDate birth;

    private String mbti;

    private Drinking drink;

    private Smoking smoking;

    private String city;

    private String district;

    private List<String> hobby = new ArrayList<>();

    private List<String> images = new ArrayList<>();


    public ProfileDTO from(Member member, Hobby hobby, Photo photo,@Value("${cloud.ncp.storage.end-point}") String endPoint, @Value("${cloud.ncp.storage.bucket-name-member}")String bucketName) {
        List<String> fullImageUrls = new ArrayList<>();
        this.name = member.getName();
        this.age = member.getAge();
        this.gender = member.getGender();
        this.birth = member.getBirth();
        this.mbti = member.getMbti();
        this.drink = member.getDrink();
        this.smoking = member.getSmoking();
        this.city = member.getCity();
        this.district = member.getDistrict();
        this.hobby = hobby.getHobby();

        List<String> images = photo.getPhoto();
        for (String image : images) {
            fullImageUrls.add(endPoint + "/" + bucketName + "/" + image);
        }
        this.images = fullImageUrls;
        return this;
    }
}

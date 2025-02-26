package pairing.ideal.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import pairing.ideal.community.entity.Participant;
import pairing.ideal.community.entity.Post;
import pairing.ideal.member.common.Drinking;
import pairing.ideal.member.common.Gender;
import pairing.ideal.member.common.Smoking;
import pairing.ideal.member.dto.ProfileDTO;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String name;

    private int age;

    @Builder.Default()
    private long heart = 0;

    private Gender gender;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private String mbti;

//    @Enumerated(EnumType.STRING)
    private Drinking drink;

//    @Enumerated(EnumType.STRING)
    private Smoking smoking;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime joinAt;

    private String city;

    private String district;

    @Builder.Default()
    private int claimCount = 0;

    @Builder.Default()
    private boolean enrolled = false;

    @OneToOne
    private Hobby hobby;;

    @OneToOne
    private Photo photo;

    /* Post 연관 관계 */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    /* Participant 연관 관계 */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Participant> participants= new ArrayList<>();

    public Member addInfo(ProfileDTO profileDTO) {
        this.name = profileDTO.getName();
        this.age = profileDTO.getAge();
        this.gender = profileDTO.getGender();
        this.birth = profileDTO.getBirth();
        this.mbti = profileDTO.getMbti();
        this.drink = profileDTO.getDrink();
        this.smoking = profileDTO.getSmoking();
        this.city = profileDTO.getCity();
        this.district = profileDTO.getDistrict();
        return this;
    }

    public Member createDetail(Hobby hobby, Photo photo) {
        this.hobby = hobby;
        this.photo = photo;
        return this;
    }

    public void calHeart(long score){
        this.heart += score;
    }

    private void enroll() {
        this.enrolled = true;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", heart=" + heart +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", mbti='" + mbti + '\'' +
                ", drink=" + drink +
                ", smoking=" + smoking +
                ", joinAt=" + joinAt +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", claimCount=" + claimCount +
                ", enrolled=" + enrolled +
                ", hobby=" + hobby +
                ", photo=" + photo +
                '}';
    }
}

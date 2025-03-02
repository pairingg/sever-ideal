package pairing.ideal.member.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pairing.ideal.member.converter.StringListConverter;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hobbyId;

    @Convert(converter = StringListConverter.class)
    @Builder.Default
    private List<String> hobby = new ArrayList<>();

    @OneToOne
    private Member member;

    public Hobby(Member member, List<String> hobby) {
        this.member = member;
        this.hobby = hobby;
    }

    public void updateHobby(List<String> hobby) {
        this.hobby = hobby;
    }
}

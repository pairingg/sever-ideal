package pairing.ideal.member.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pairing.ideal.member.converter.StringListConverter;
import pairing.ideal.member.converter.UrlListConverter;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long photoId;

    @Convert(converter = StringListConverter.class)
    @Builder.Default
    private List<String> photo = new ArrayList<>();

    @OneToOne
    private Member member;

    public Photo(Member save, List<String> photo) {
        this.member = save;
        this.photo = photo;
    }

    public void updatePhoto(List<String> photo) {
        this.photo = photo;
    }
}

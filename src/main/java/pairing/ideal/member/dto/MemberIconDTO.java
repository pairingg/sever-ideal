package pairing.ideal.member.dto;

import lombok.Builder;
import lombok.Getter;
import pairing.ideal.member.entity.Member;

@Getter
@Builder
public class MemberIconDTO {
    private String name;
    private String profileImage;

    public static MemberIconDTO fromEntity(Member member) {
        return MemberIconDTO.builder()
                .name(member.getName())
                .profileImage(member.getHobby().getHobby().get(0))
                .build();
    }
}

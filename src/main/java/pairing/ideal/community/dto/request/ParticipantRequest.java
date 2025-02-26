package pairing.ideal.community.dto.request;

import pairing.ideal.community.entity.Participant;
import pairing.ideal.community.entity.Post;
import pairing.ideal.member.entity.Member;

public record ParticipantRequest(
        Post post,
        Long userId
) {
    public Participant toEntity(Post post, Member member) {
        return Participant.builder()
                .post(post)
                .member(member)
                .build();
    }
}

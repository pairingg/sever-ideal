package pairing.ideal.community.dto.request;

import pairing.ideal.community.entity.Participant;
import pairing.ideal.community.entity.Post;

public record ParticipantRequest(
        Post post,
        Long memberId
) {
    public Participant toEntity(Post post, Long userId) {
        return Participant.builder()
                .post(post)
                .userId(userId)
                .build();
    }
}

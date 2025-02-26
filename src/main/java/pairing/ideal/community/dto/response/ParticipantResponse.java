package pairing.ideal.community.dto.response;

import java.util.List;
import pairing.ideal.community.entity.Participant;
import pairing.ideal.community.entity.Post;

public record ParticipantResponse(
        Long participantId,
        Long userId,
        Long postId

) {
    public static ParticipantResponse fromEntity(Participant participant) {
        return new ParticipantResponse(
                participant.getParticipantId(),
                participant.getUserId(),
                participant.getPost().getPostId()
        );
    }
}

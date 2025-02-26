package pairing.ideal.community.dto.response;
import pairing.ideal.community.entity.Participant;

public record ParticipantResponse(
        String name,
        int age,
        String city,
        Long participantId,
        Long userId,
        Long postId

) {
    public static ParticipantResponse fromEntity(Participant participant) {
        return new ParticipantResponse(
                participant.getMember().getName(),
                participant.getMember().getAge(),
                participant.getMember().getCity(),
                participant.getParticipantId(),
                participant.getMember().getUserId(),
                participant.getPost().getPostId()
        );
    }
}

//export interface MeListItem {
//    name: string; (저요 누른 사람 이름)
//    age: number; (저요 누른 사람 나이)
//    city: string; (저요 누른 사람 거주지)
//    participantId: 1; (인덱스)
//    userId: 2222; (저요 누른 사람 아이디)
//    postId: 1 (해당 게시글 아이디)
package pairing.ideal.community.dto.request;

import pairing.ideal.community.entity.Post;
import pairing.ideal.member.entity.Member;

public record PostRequest (
        Long userId,
        String content,
        String imageUrl
) {
    public Post toEntity(Member member, String content, String imageUrl) {
        return Post.builder()
                .member(member)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}

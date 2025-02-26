package pairing.ideal.community.dto.request;

import pairing.ideal.community.entity.Post;

public record PostRequest (
        Long userId,
        String content,
        String imageUrl
) {
    public Post toEntity(Long userId, String content, String imageUrl) {
        return Post.builder()
                .userId(userId)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}

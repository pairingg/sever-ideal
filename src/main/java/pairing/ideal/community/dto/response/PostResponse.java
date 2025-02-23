package pairing.ideal.community.dto.response;

import pairing.ideal.community.entity.Post;

public record PostResponse(
        Long id,
        String content,
        String imageUrl,
        String createdAt
) {
    public static PostResponse fromEntity(Post post, String formattedCreatedAt) {
        return new PostResponse(
                post.getPostId(),
                post.getContent(),
                post.getImageUrl(),
                formattedCreatedAt
        );
    }
}

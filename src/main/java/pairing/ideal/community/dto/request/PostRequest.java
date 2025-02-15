package pairing.ideal.community.dto.request;

import pairing.ideal.community.entity.Post;

public record PostRequest (
        String content,
        String imageUrl
) {
    public Post toEntity(String content, String imageUrl) {
        return Post.builder()
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}

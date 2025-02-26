package pairing.ideal.community.dto.response;

import pairing.ideal.community.entity.Post;

public record PostResponse(
        Long id,
        String name,
        int age,
        String city,
        String content,
        String imageUrl,
        String createdAt
) {
    public static PostResponse fromEntity(Post post, String formattedCreatedAt) {
        return new PostResponse(
                post.getPostId(),
                post.getMember().getName(),
                post.getMember().getAge(),
                post.getMember().getCity(),
                post.getContent(),
                post.getImageUrl(),
                formattedCreatedAt
        );
    }
}

// id: number;
// name: string;
// age: number;
// city: string;
// content: string;
//  imageUrl? string;
// createdAt: Date | string

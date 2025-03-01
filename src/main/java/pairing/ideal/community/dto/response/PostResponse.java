package pairing.ideal.community.dto.response;

import java.time.LocalDateTime;
import pairing.ideal.community.entity.Post;

public record PostResponse(
        Long id,
        String name,
        int age,
        String city,
        String content,
        String imageUrl,
        LocalDateTime createdAt,
        String profileImg
) {
    public static PostResponse fromEntity(Post post) {
        String profileImg = (post.getMember().getPhoto() != null && !post.getMember().getPhoto().getPhoto().isEmpty())
                ? post.getMember().getPhoto().getPhoto().get(0)
                : null; // 첫 번째 사진이 없으면 null 반환

        return new PostResponse(
                post.getPostId(),
                post.getMember().getName(),
                post.getMember().getAge(),
                post.getMember().getCity(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                profileImg
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

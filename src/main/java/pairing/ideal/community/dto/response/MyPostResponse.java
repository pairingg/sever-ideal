package pairing.ideal.community.dto.response;
import pairing.ideal.community.entity.Post;

public record MyPostResponse(
        Long id,
        Long userId,
        String name,
        int age,
        String city,
        String content,
        String imageUrl,
        String createdAt
) {
    public static MyPostResponse fromEntity(Post post, String formattedCreatedAt) {
        return new MyPostResponse(
                post.getPostId(),
                post.getMember().getUserId(),
                post.getMember().getName(),
                post.getMember().getAge(),
                post.getMember().getCity(),
                post.getContent(),
                post.getImageUrl(),
                formattedCreatedAt
        );
    }
}

//export interface MyPost {
//    id: number;
//    userId: number;
//    name: string;
//    age: number;
//    city: string;
//    content: string;
//    imageUrl: string;
//    createdAt: Date | string;
//}
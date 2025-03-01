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
        String createdAt,
        String profileImg
) {
    public static MyPostResponse fromEntity(Post post, String formattedCreatedAt) {
        String profileImg = (post.getMember().getPhoto() != null && !post.getMember().getPhoto().getPhoto().isEmpty())
                ? post.getMember().getPhoto().getPhoto().get(0)
                : null; // 첫 번째 사진이 없으면 null 반환

        return new MyPostResponse(
                post.getPostId(),
                post.getMember().getUserId(),
                post.getMember().getName(),
                post.getMember().getAge(),
                post.getMember().getCity(),
                post.getContent(),
                post.getImageUrl(),
                formattedCreatedAt,
                profileImg

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
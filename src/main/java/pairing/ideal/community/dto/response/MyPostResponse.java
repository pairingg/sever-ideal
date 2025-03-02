package pairing.ideal.community.dto.response;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import pairing.ideal.community.entity.Participant;
import pairing.ideal.community.entity.Post;

public record MyPostResponse(
        Long id,
        Long userId,
        String name,
        int age,
        String city,
        String content,
        String imageUrl,
        Date createdAt,
        String profileImg
) {
    public static MyPostResponse fromEntity(Post post, @Value("${cloud.ncp.storage.end-point}") String endPoint, @Value("${cloud.ncp.storage.bucket-name-member}")String bucketName) {
        String profileImg = (post.getMember().getPhoto() != null && !post.getMember().getPhoto().getPhoto().isEmpty())
                ? post.getMember().getPhoto().getPhoto().get(0)
                : null; // 첫 번째 사진이 없으면 null 반환

        String profileImgUrl = endPoint + "/" + bucketName + "/" + profileImg;

        return new MyPostResponse(
                post.getPostId(),
                post.getMember().getUserId(),
                post.getMember().getName(),
                post.getMember().getAge(),
                post.getMember().getCity(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                profileImgUrl

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
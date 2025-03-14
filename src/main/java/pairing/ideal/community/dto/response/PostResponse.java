package pairing.ideal.community.dto.response;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import pairing.ideal.community.entity.Post;
import pairing.ideal.member.entity.Photo;

public record PostResponse(
        Long id,
        String name,
        int age,
        String city,
        String content,
        String imageUrl,
        Date createdAt,
        List<String> profileImg
) {
    public static PostResponse fromEntity(Post post) {
//        String profileImg = (post.getMember().getPhoto() != null && !post.getMember().getPhoto().getPhoto().isEmpty())
//                ? post.getMember().getPhoto().getPhoto().get(0)
//                : null; // 첫 번째 사진이 없으면 null 반환

//        String profileImgUrl = storageEndPoint + "/" + storageMemberBucketName + "/" + profileImg;

        return new PostResponse(
                post.getPostId(),
                post.getMember().getName(),
                post.getMember().getAge(),
                post.getMember().getCity(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getMember().getPhoto().getPhoto()
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

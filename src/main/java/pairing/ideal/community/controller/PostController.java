package pairing.ideal.community.controller;

import java.util.List;
import java.util.Map;

import com.amazonaws.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pairing.ideal.community.config.S3ConfigCommunity;
import pairing.ideal.community.dto.request.PostRequest;
import pairing.ideal.community.dto.response.MyPostResponse;
import pairing.ideal.community.dto.response.ParticipantResponse;
import pairing.ideal.community.dto.response.PostResponse;
import pairing.ideal.community.service.PostService;
import pairing.ideal.member.entity.CustomUserDetails;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final S3ConfigCommunity s3Config;

    /* 게시글 생성*/
    @PostMapping
    public ResponseEntity<String> addPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @RequestBody PostRequest postRequest) {
        postService.savePost(postRequest, customUserDetails.getMember().getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body("게시글이 등록되었습니다.");
    }

    /* 게시글 목록 조회 */
    @GetMapping("")
    public List<PostResponse> getPosts() {
        return postService.getAllPosts();
    }

    /* 내가 쓴 게시글 조회 */
    @GetMapping("/myposts")
    public List<MyPostResponse> getMyPosts(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return postService.getMyPosts(customUserDetails.getMember().getUserId());
    }

    /* 게시글 조회 */
    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable(name = "postId") Long postId) {
        return postService.getPost(postId);
    }

    /* 게시글 삭제 */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                             @PathVariable(name = "postId") Long postId) {
        postService.deletePost(postId, customUserDetails.getMember().getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body("게시글이 삭제되었습니다.");
    }

    /* 게시글 수정 */
    @PutMapping("/{postId}")
    public PostResponse updatePost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @PathVariable(name = "postId") Long postId,
                                   @RequestBody PostRequest postRequest) {
        return postService.updatePost(customUserDetails.getMember().getUserId(), postId, postRequest);
    }

    /* 저요 버튼 클릭 요청 */
    @PostMapping("/{postId}/participations")
    public ResponseEntity<String> addParticipation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @PathVariable(name = "postId") Long postId) {
        Long userId = customUserDetails.getMember().getUserId();

        try {
            postService.addParticipant(postId, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("참여가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }
//    @PostMapping("/{postId}/participations")
//    public ResponseEntity<String> addParticipation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
//                                                   @PathVariable(name = "postId") Long postId) {
//        postService.addParticipant(postId, customUserDetails.getMember().getUserId());
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("참여가 완료되었습니다.");
//
//    }

    /* 저요 목록 조회 */
    @GetMapping("/{postId}/participations")
    public List<ParticipantResponse> getParticipants(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                     @PathVariable(name = "postId") Long postId) {

        return postService.getParticipants(postId, customUserDetails.getMember().getUserId());
    }

    @GetMapping("presigned-url")
    public Map<String, String> getPresignedUrl(@RequestParam String fileName, @RequestParam String contentType) {
        String presignedUrl = s3Config.generatePresignedUrl(fileName, HttpMethod.PUT, 600000, contentType);
        return Map.of("url", presignedUrl);
    }
}


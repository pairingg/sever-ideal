package pairing.ideal.community.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pairing.ideal.community.dto.request.PostRequest;
import pairing.ideal.community.dto.response.PostResponse;
import pairing.ideal.community.service.PostService;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    /* 게시글 생성*/
    @PostMapping
    public ResponseEntity<String> addPost(@RequestBody PostRequest postRequest) {
        postService.savePost(postRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body("게시글이 등록되었습니다.");
    }

    /* 게시글 목록 조회 */
    @GetMapping("")
    public List<PostResponse> getPost() {
        return postService.getAllPosts();
    }

    /* 게시글 조회 */
    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable(name = "postId") Long postId) {
        return postService.getPost(postId);
    }

    /* 게시글 삭제 */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("게시글이 삭제되었습니다.");
    }

    /* 게시글 수정 */
    @PutMapping("/{postId}")
    public PostResponse updatePost(@PathVariable(name = "postId") Long postId, @RequestBody PostRequest postRequest) {
        return postService.updatePost(postId, postRequest);
    }
}


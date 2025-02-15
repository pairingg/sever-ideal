package pairing.ideal.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pairing.ideal.community.dto.request.PostRequest;
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
}



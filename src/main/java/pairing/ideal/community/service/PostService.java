package pairing.ideal.community.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairing.ideal.community.dto.request.PostRequest;
import pairing.ideal.community.dto.response.PostResponse;
import pairing.ideal.community.entity.Post;
import pairing.ideal.community.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /* 게시글 저장 */
    public Post savePost(PostRequest postRequest) {
        Post post = postRequest.toEntity(postRequest.content(), postRequest.imageUrl());
        return postRepository.save(post);
    }

    /* 모든 게시글 조회 */
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            postResponses.add(PostResponse.fromEntity(post, formatCreatedAt(post.getCreatedAt())));
        }
        return postResponses;
    }

    /* 게시글 조회 */
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        return PostResponse.fromEntity(post, formatCreatedAt(post.getCreatedAt()));
    }

    /* 게시글 삭제 */
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시물은 존재하지 않습니다."));
        // 삭제 권한 설정
//        if (!post.getMemberId().equals(memberId)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("삭제 권한이 없습니다.");
//        }
        postRepository.delete(post);
    }

    /* 게시글 수정 */
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new RuntimeException("해당 게시물은 존재하지 않습니다."));
        post.update(postRequest.content(), postRequest.imageUrl());
        return PostResponse.fromEntity(postRepository.save(post), formatCreatedAt(post.getCreatedAt()));
    }

    /* 글 생성 날짜 formatting */
    private String formatCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.ENGLISH);
        return createdAt != null ? createdAt.format(formatter) : null;
    }
}

package pairing.ideal.community.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new RuntimeException("해당 카테고리가 존재하지 않습니다."));
        // createdAt 형식 변경
        return PostResponse.fromEntity(post, formatCreatedAt(post.getCreatedAt()));
    }

    /* 글 생성 날짜 formatting */
    private String formatCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.ENGLISH);
        return createdAt != null ? createdAt.format(formatter) : null;
    }
}

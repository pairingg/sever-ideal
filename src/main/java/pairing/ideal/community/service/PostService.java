package pairing.ideal.community.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pairing.ideal.community.dto.request.PostRequest;
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

    /* 글 생성 날짜 formatting */
    private String formatCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.ENGLISH);
        return createdAt != null ? createdAt.format(formatter) : null;
    }
}

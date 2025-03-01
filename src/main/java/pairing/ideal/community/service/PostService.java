package pairing.ideal.community.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairing.ideal.community.dto.request.PostRequest;
import pairing.ideal.community.dto.response.MyPostResponse;
import pairing.ideal.community.dto.response.ParticipantResponse;
import pairing.ideal.community.dto.response.PostResponse;
import pairing.ideal.community.entity.Participant;
import pairing.ideal.community.entity.Post;
import pairing.ideal.community.repository.ParticipantRepository;
import pairing.ideal.community.repository.PostRepository;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.repository.MemberRepository;
import pairing.ideal.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ParticipantRepository participantRepository;
    private final MemberRepository memberRepository;

    /* 게시글 저장 */
    public Post savePost(PostRequest postRequest, Long userId) {
        Member member = memberRepository.findById(userId).orElse(null);
        Post post = postRequest.toEntity(member, postRequest.content(), postRequest.imageUrl());
        return postRepository.save(post);
    }

    /* 모든 게시글 조회 */
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            postResponses.add(PostResponse.fromEntity(post));
        }
        return postResponses;
    }

    /* 내가 쓴 게시글 조회 */
    public List<MyPostResponse> getMyPosts(Long userId) {
        List<Post> myPosts = postRepository.findByMember_UserId(userId);
        List<MyPostResponse> postResponses = new ArrayList<>();
        for (Post post : myPosts) {
            postResponses.add(MyPostResponse.fromEntity(post));
        }
        return postResponses;
    }

    /* 게시글 조회 */
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        return PostResponse.fromEntity(post);
    }

    /* 게시글 삭제 */
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시물은 존재하지 않습니다."));
        // 삭제 권한 설정
        if (!(post.getMember().getUserId() == userId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    /* 게시글 수정 */
    public PostResponse updatePost(Long userId, Long postId, PostRequest postRequest) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new RuntimeException("해당 게시물은 존재하지 않습니다."));
        // 수정 권한 검사
        if (!(post.getMember().getUserId() == userId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        post.update(postRequest.content(), postRequest.imageUrl());
        return PostResponse.fromEntity(postRepository.save(post));
    }

    /* 글 생성 날짜 formatting */
//    private String formatCreatedAt(LocalDateTime createdAt) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.ENGLISH);
//        return createdAt != null ? createdAt.format(formatter) : null;
//    }

    /* 저요 -> 참여자 생성 */
    public Participant addParticipant(Long postId, Long userId) {
        Member member = memberRepository.findById(userId).orElse(null);
        Post post  = postRepository.findByPostId(postId).orElseThrow(() -> new RuntimeException("해당 게시물은 존재하지 않습니다."));
        Participant participant = Participant.builder().post(post).member(member).build();
        System.out.println(participant.toString());
        return participantRepository.save(participant);
    }

    /* 저요 -> 저요 목록 조회 */
    public List<ParticipantResponse> getParticipants(Long postId, Long userId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(()-> new RuntimeException("해당 게시물은 존재하지 않습니다."));
//        if (!post.getUserId().equals(userId)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("조회 권한이 없습니다.")";
//        }
        List <Participant> Participants = post.getParticipants();
        List <ParticipantResponse> participantResponses = new ArrayList<>();
        for (Participant participant : Participants) {
            participantResponses.add(ParticipantResponse.fromEntity(participant));
        }
        return participantResponses;
    }
}

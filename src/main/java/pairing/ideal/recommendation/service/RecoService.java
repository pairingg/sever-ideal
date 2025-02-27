package pairing.ideal.recommendation.service;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.repository.MemberRepository;
import pairing.ideal.recommendation.dto.request.EnrollRequest;
import pairing.ideal.recommendation.dto.response.KeywordResponse;
import pairing.ideal.recommendation.dto.response.RecoResponse;
import pairing.ideal.recommendation.entity.IdealType;
import pairing.ideal.recommendation.entity.Keyword;
import pairing.ideal.recommendation.repository.IdealRepository;
import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecoService {

    private final IdealRepository idealRepository;
    private final MemberRepository memberRepository;

    public IdealType enrollIdeal(EnrollRequest enrollRequest, Member member){
        IdealType entity = enrollRequest.toEntity(member);
        IdealType save = idealRepository.save(entity);
        return save;
    }


//    public List<RecoResponse> getRecoResults (Member member) {
    public List<Member> getRecoResults (Member member) {

        // 지금 요청 보낸사람의 이상형 정보
        IdealType idealType = idealRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 이 이상형 정보랑 맞는 사람 2명 찾기
        // mbti, hobby 정보는 포함 시키지 않음
        List<Member> matchingMembers = memberRepository.findMatchingMembers(idealType.getMember().getUserId())
                .orElseThrow(() -> new RuntimeException("조건에 맞는 이상형이 없습니다."));
        Collections.shuffle(matchingMembers);
        return matchingMembers.subList(0, 2);
    }

    public List<KeywordResponse> getRecoKeywords () {
        return KeywordResponse.getKeywordList();

    }

    public List<RecoResponse> getKeywordResults(String keywordId, Member member) {
        IdealType idealType = idealRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("이상형 정보가 없습니다."));

        List<Member> matchingMembers;
        switch (keywordId) {
            case "같은 취미":
                matchingMembers = memberRepository.findByHobby(idealType.getHobby());
                break;
            case "같은 위치":
                matchingMembers = memberRepository.findByLocation(idealType.getAddress().stream()
                        .map(a -> a.getCity() + ", " + a.getDistrict()).toList());
                break;
            case "연상":
                matchingMembers = memberRepository.findOlderThan(idealType.getAgeStart());
                break;
            case "연하":
                matchingMembers = memberRepository.findYoungerThan(idealType.getAgeEnd());
                break;
            case "동갑":
                matchingMembers = memberRepository.findSameAge((idealType.getAgeStart() + idealType.getAgeEnd()) / 2);
                break;
            case "같은 성별":
                matchingMembers = memberRepository.findByGender(member.getGender());
                break;
            default:
                throw new IllegalArgumentException("Invalid keyword");
        }

        Collections.shuffle(matchingMembers);
        List<RecoResponse> recoResponses = new ArrayList<>();
        for (Member m : matchingMembers.stream().limit(2).toList()) {
            recoResponses.add(RecoResponse.fromEntity(m));
        }
        return recoResponses;
    }

}

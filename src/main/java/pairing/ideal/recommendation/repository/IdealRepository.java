package pairing.ideal.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pairing.ideal.member.entity.Member;
import pairing.ideal.recommendation.entity.IdealType;

import java.util.Optional;

public interface IdealRepository extends JpaRepository<IdealType, Long> {
    Optional<IdealType> findByMember(Member member);
}

package pairing.ideal.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pairing.ideal.member.entity.Hobby;
import pairing.ideal.member.entity.Member;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Optional<Hobby> findByMember(Member byEmail);
}

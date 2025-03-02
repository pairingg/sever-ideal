package pairing.ideal.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pairing.ideal.member.entity.Member;
import pairing.ideal.member.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByMember(Member byEmail);
    boolean existsByMember(Member member);
}

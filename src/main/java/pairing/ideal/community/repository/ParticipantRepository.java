package pairing.ideal.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pairing.ideal.community.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}

package pairing.ideal.member.staticData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pairing.ideal.member.staticData.entity.HobbyList;

@Repository
public interface HobbyListRepository extends JpaRepository<HobbyList, Long> {
}

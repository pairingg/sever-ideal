package pairing.ideal.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pairing.ideal.member.entity.Member;
import pairing.ideal.recommendation.entity.IdealType;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

//    @Query("SELECT m " +
//            "FROM Member m" +
//            "WHERE m.age >= :idaelType")
//    List<Member> findIdeal(@Param("idealType") IdealType idealType);


    @Query("SELECT m FROM Member m " +
            "JOIN IdealType i ON i.member = m " +
            "WHERE i.member.userId = :userId " +
            "AND m.age BETWEEN i.ageStart AND i.ageEnd " +
            "AND (i.drink IS NULL OR m.drink = i.drink) " +
            "AND (i.smoke IS NULL OR m.smoking = i.smoke) " +
            "AND EXISTS (SELECT 1 FROM IdealType it JOIN it.address a " +
            "            WHERE it.member.userId = :userId " +
            "            AND a.city = m.city AND a.district = m.district)")
    Optional<List<Member>> findMatchingMembers(@Param("userId") Long userId);
}

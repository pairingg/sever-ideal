package pairing.ideal.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pairing.ideal.community.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}

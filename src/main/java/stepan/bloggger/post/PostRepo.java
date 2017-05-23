package stepan.bloggger.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepo extends JpaRepository<Post, Long>{

	@Query("SELECT distinct p from Post p JOIN FETCH  p.owner o where o.username = ?1 ")
	List<Post> findAllByUsername(String username);

}

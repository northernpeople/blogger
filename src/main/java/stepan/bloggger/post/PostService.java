package stepan.bloggger.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
	
	@Autowired 
	PostRepo repo;
	
	public Post create(Post post){
		return repo.saveAndFlush(post);
	}

	public List<Post> findAllByUsername(String name) {
		return repo.findAllByUsername(name);
	}

	public Post findById(Long id) {
		return repo.findOne(id);
	}

	public Post update(Post post) {
		return repo.saveAndFlush(post);
	}

}

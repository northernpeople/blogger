package stepan.bloggger.post;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stepan.bloggger.user.UserService;

@Service
public class PostService {
	
	@Autowired 
	PostRepo repo;
	
	@Autowired
	UserService userService;
	
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

	public List<Post> findAllByUserId(Long id) {
		return findAllByUsername(userService.byId(id).getUsername())
				.stream().sorted( (Post x , Post y) -> x.getCreated().compareTo(y.getCreated()))
				.collect(Collectors.toList());
	}

}

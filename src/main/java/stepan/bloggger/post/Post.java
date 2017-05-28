package stepan.bloggger.post;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import stepan.bloggger.user.User;

@Entity
public class Post {
	
	public Post() {
		this.created = LocalDateTime.now();
	}
	
	@Id @GeneratedValue
	private Long id;
	
	private String title;
	private String content;
	
	private LocalDateTime created;
	
	@ManyToOne
	private User owner;

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreated() {
		return created;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

}

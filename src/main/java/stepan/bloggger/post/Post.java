package stepan.bloggger.post;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import stepan.bloggger.image.Image;

@Entity
public class Post {
	
	@Id @GeneratedValue
	private Long id;
	
	private String title;
	private String content;
	
	private LocalDateTime created;
	
	@OneToOne
	private Image image;
	
	

}

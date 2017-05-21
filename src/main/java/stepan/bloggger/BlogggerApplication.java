package stepan.bloggger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BlogggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogggerApplication.class, args);
	}
}

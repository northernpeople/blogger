package stepan.bloggger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@EnableScheduling
@SpringBootApplication
public class BlogggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogggerApplication.class, args);
	}
	
	@Bean
	public SpringSecurityDialect securityDialect() {
	  return new SpringSecurityDialect();
	}
}

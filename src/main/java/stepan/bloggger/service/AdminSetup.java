package stepan.bloggger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import stepan.bloggger.user.Role;
import stepan.bloggger.user.User;
import stepan.bloggger.user.UserService;




@Component
@Profile({"production", "dev"})
public class AdminSetup {
	
	@Value("${adminEmail}")
	String adminEmail;
	
	@Value("${adminPassword}")
	String adminPassword;
	
	@Autowired
	UserService userService;
	
	@Scheduled(fixedRate = Long.MAX_VALUE)
	public void setUpAdmin(){
		userService.create(new User(adminEmail, adminPassword), Role.ROLE_ADMIN);
		userService.create(new User("a@a.a", "asdfasdf"), Role.ROLE_USER);

		System.out.println("admin account set up: "+adminEmail +" : ******"+ adminPassword.substring(6));
	}
}

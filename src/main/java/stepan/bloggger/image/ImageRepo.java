package stepan.bloggger.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long>{

	Image findByFileName(String fileName);

}

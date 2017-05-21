package stepan.bloggger.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

		
		private static final String UPLOAD_ROOT = "/uploads"; // folder on absolute path
		
		@Autowired
		ImageRepo repo;
		
		@Autowired
		ResourceLoader loader;
		
		public Resource getBy(String fileName){
			return loader.getResource("file:" + UPLOAD_ROOT +"/"+fileName);
		}
		
		public void create(MultipartFile file) throws IOException{
			if(! file.isEmpty()){
				Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
				repo.saveAndFlush(new Image(file.getOriginalFilename(), file.getContentType(), file.getSize()));
			}
		}
		
		public void delete(String fileName) throws IOException{
			repo.delete(repo.findByFileName(fileName));
			Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
		}

		public String typeByFileName(String fileName) {
			return repo.findByFileName(fileName).getContentType();
		}

		public List<Image> getAll() {
			return repo.findAll();
		}
		
		

}

package stepan.bloggger.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import stepan.bloggger.post.Post;
import stepan.bloggger.post.PostRepo;

@Service
public class ImageService {

		private static final String UPLOAD_ROOT = "/Users/stepan/uploads"; // folder on absolute path
		
		@Autowired
		ImageRepo repo;
		
		@Autowired 
		PostRepo postRepo;
		
		@Autowired
		ResourceLoader loader;
		
		public Resource get(Image image){
			return loader.getResource("file:" + UPLOAD_ROOT +"/"+image.getId());
		}
		
		public Image create(MultipartFile file, Long postId) throws IOException{
			if(! file.isEmpty()){
				Post post = postRepo.findOne(postId);
				Image image = repo.saveAndFlush(new Image(post, file.getOriginalFilename(), file.getContentType(), file.getSize()));
				Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, image.getId().toString()));
			}
			return null;
		}
		
		
		public void delete(Image image) throws IOException{	
			if(image != null){
				repo.delete(image);
				Files.deleteIfExists(Paths.get(UPLOAD_ROOT, image.getId().toString()));
			}
		}

		
		public Image findByPostId(Long postId) {
			return repo.findFirstByPostId(postId);
		}

		public Image getById(Long id) {
			return repo.findOne(id);
		}
		
		

}

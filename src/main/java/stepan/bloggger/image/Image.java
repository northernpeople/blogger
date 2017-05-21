package stepan.bloggger.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String fileName;

	private String contentType;

	// size in bytes
	private long size;
	
	private java.time.LocalDateTime uploaded;

	public Image(String fileName) {
		this();
		this.fileName = fileName;
	}
	
	public Image() { uploaded = LocalDateTime.now(); }

	public Image(String fileName, String contentType, long size) {
		this(fileName);
		this.contentType = contentType;
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

	public java.time.LocalDateTime getUploaded() {
		return uploaded;
	}

	public void setUploaded(java.time.LocalDateTime uploaded) {
		this.uploaded = uploaded;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Image [id=").append(id).append(", fileName=").append(fileName).append(", contentType=")
				.append(contentType).append(", size=").append(size).append("]");
		return builder.toString();
	}

	
	
	

}

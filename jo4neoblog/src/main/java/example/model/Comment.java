package example.model;

import java.util.Date;
import jo4neo.*;

public class Comment extends NeoBean<Comment> {
	
	
	@neo private String content;
	@neo private Date createdAt;
	@neo private Post post;

	public Comment() {
		createdAt = new Date();
	}	
	
	public Comment(String s) {
		this();
	}	
	
	public Comment(Post p) {
		this();
		post = p;
	}
	public long getId() {
		return neo.id();
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
}

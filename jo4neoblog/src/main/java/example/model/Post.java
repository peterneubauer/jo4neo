package example.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

import jo4neo.*;

@neo(recency=true)
public class Post extends NeoBean<Post> {
	
	@neo private Collection<Comment> comments = new LinkedList<Comment>();
	@neo("hasTag") private Collection<Tag> tags = new HashSet<Tag>();
	@neo User author;
	@neo Date createdAt;
	@neo String title;
	@neo(fulltext=true) public String content;

	public Post(String s) {
		this();
	}
	public Post() {
		createdAt = new Date();
	}
	
	public Collection<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	public Post add(Comment c) {
		comments.add(c); return this;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Collection<Tag> getTags() {
		return tags;
	}
	
	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}
	
	public void addTag(Tag t) {
		tags.add(t);
	}
	
	public int getCommentsCount() {
		if ( comments != null)
			return comments.size();
		else 
			return 0;
	}


}
package test;

import java.util.Date;

import jo4neo.neo;


public class BlogPost extends ContentItem implements Taggable {
	
	@neo
	String title;
	@neo
	Date publishDate;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

}

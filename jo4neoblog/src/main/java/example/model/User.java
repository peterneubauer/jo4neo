package example.model;

import java.util.Collection;
import java.util.LinkedList;
import jo4neo.*;

public class User extends NeoBean<User>{
	
	@neo(index=true) 
	public String screenName;	
	@neo public String encryptedPassword;
	@neo public String email;
	@neo public Collection<Post> posts = new LinkedList<Post>();
	
	public Collection<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}
	
	public void addPost(Post p) {
		posts.add(p);
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean equals(Object o) {
		if (! (o instanceof User)) return false;
		if (this == o) return true;
		return (screenName.equals(((User)o).screenName));
	}
}

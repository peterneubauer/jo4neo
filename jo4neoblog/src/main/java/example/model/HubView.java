package example.model;

import java.util.Collection;


public interface HubView {
	Collection<Post> getPosts();
	Collection<Tag> getTags();
	boolean isSinglePost();
	boolean isEditable();

}

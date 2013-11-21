package action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import example.model.HubView;
import example.model.Post;
import example.model.Tag;

@UrlBinding("/blog/search")
public class SearchAction extends BaseAction implements HubView {
	
	private Collection<Post> posts;
	private boolean editable = false;
	private String query;
	

	@DefaultHandler
	public Resolution show() {
		Post post = new Post();
		posts = pm().find(post).where(post.content).is(query).results();
		return new ForwardResolution("/hub.jsp");
	}

	public String getQuery() {	return query;}
	public void setQuery(String query) { this.query = query;}
	public Collection<Post> getPosts() {return posts;}
	public Collection<Tag> getTags() {return pm().get(Tag.class);}

	public boolean isSinglePost() {
		return false;
	}

	public boolean isEditable() {
		return editable;
	}
	
	
}

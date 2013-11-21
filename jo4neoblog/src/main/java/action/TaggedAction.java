package action;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import example.model.HubView;
import example.model.Post;
import example.model.Tag;

@UrlBinding("/blog/tags")
public class TaggedAction extends BaseAction implements HubView, Comparator<Post> {
	
	private List<Post> posts;
	private Tag tag;
	
	@DefaultHandler
	public Resolution show() {
		String pathInfo = context.getRequest().getPathInfo();
		if ( pathInfo.length() > 6) {
			selected = Long.valueOf(pathInfo.substring(6));		
			posts = new LinkedList<Post>();
			tag = load(Tag.class, selected);
			posts.addAll(tag.posts);
			Collections.sort(posts, this);
		} else
			posts =  new LinkedList<Post>(pm().getMostRecent(Post.class, 5));
		return new ForwardResolution("/hub.jsp");
	}
	public Collection<Post> getPosts() {return tag.posts;}
	public long getP() {return -1;}
	public Collection<Tag> getTags() {return pm().get(Tag.class);}
	public boolean isSinglePost() {
		return false;
	}
	@Override
	public int compare(Post o1, Post o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isEditable() {
		return false;
	}

}

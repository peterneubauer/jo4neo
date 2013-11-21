package action;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Transaction;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import example.model.Post;
import example.model.Tag;

@UrlBinding("/blog/post")
public class PostAction extends BaseAction {

	private Post post;
	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	private Collection<String> splitTags() {
		Set<String> results = new HashSet<String>();
		if (tag!=null)
			for (String	str : tag.split(","))
				results.add(str.trim());
		return results;
	}


	@Before(stages = LifecycleStage.EventHandling)
	public Resolution secure() throws Exception {
		return ( context.getLogin() == null) ?
			new RedirectResolution(LoginAction.class) : null;
	}

	@DefaultHandler
	public Resolution start() {
		return new ForwardResolution("/post.jsp");
	}
	
	@HandlesEvent("addTag")
	public Resolution addTag() {
		return new ForwardResolution("/post.jsp"); 
	}

	@HandlesEvent("save")
	public Resolution post() {
		Transaction t = pm().beginTx();
		try {
			post.setAuthor(context.getLogin());
			for (String tagname : splitTags())
				post.addTag(forName(tagname));
			post.save();
			t.success();
		} finally {
			t.finish();
		}
		return new RedirectResolution(HubAction.class);
	}
	
	private Tag forName(String str) {
		Tag tag = new Tag();
		tag = pm().find(tag).where(tag.name).is(str).result();
		if (tag==null)
			tag = new Tag(str);
		return tag;
	}

	@ValidateNestedProperties({
        @Validate(field="title", required=true, maxlength=75, on = "post"),
        @Validate(field="content", required=true, maxlength=5000, on = "post")
    })
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}

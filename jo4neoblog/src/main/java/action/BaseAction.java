package action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import jo4neo.ObjectGraph;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import example.ContextListener;
import example.StripesContext;
import example.model.Post;

public class BaseAction implements ActionBean {
	protected StripesContext context;
	protected long selected = -1;
	
	public long getSelected() {
		return selected;
	}
	
	public void setSelected(long l) {
		selected = l;
	}
	
	public StripesContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext c) {
		context = (StripesContext)c;
	}

	protected String hashPassword(String s) throws NoSuchAlgorithmException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		md.update(s.getBytes());
		return Hex.toHex(md.digest());
	}

	
	protected <T> Collection<T> load(Class<T> cls) {
		return pm().get(cls);
	}
	
	protected <T> T load(Class<T> t, long id) {
		return pm().get(t, id);
	}
	
	protected ObjectGraph pm() {
		return ContextListener.pm;
	}
	
	protected boolean myPost(Post post) {
		return post.getAuthor().equals(context.getLogin());
	}
	

}

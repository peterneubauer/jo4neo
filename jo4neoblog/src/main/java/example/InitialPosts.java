package example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.neo4j.graphdb.Transaction;

import action.Hex;

import example.model.Post;
import example.model.Tag;
import example.model.User;

import jo4neo.ObjectGraph;

public class InitialPosts {

	ObjectGraph graph;
	
	public InitialPosts(ObjectGraph graph) {
		this.graph = graph;
		Transaction t = graph.beginTx();
		try {
			Tag tag = new Tag();
			tag = graph.find(tag).where(tag.name).is("welcome").result();
			if (tag != null)
				return;
			
			User user = new User();
			user.email = "thewebsemantic@gmail.com";
			user.encryptedPassword = hash("admin");
			user.screenName = "admin";
			user.save();
			
			tag = new Tag("welcome");
			tag.save();			
			
			Post p = new Post();
			p.setAuthor(user);
			p.setContent("This is a toy blog app to demonstrate jo4neo. Yes, you can inject JavaScript, it's a toy.  Default user admin/admin");
			p.setTitle("neo4j can store data for a stripes app");
			p.setCreatedAt(new Date());
			p.addTag(tag);
			p.save();
			
			t.success();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.finish();
		}
	}
	
	public String hash(String s) throws NoSuchAlgorithmException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		md.update(s.getBytes());
		return Hex.toHex(md.digest());
	}

}

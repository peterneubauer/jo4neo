package test.uri;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Collection;

import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import test.BaseTest;

public class TestUriType extends BaseTest{
	
	@Test
	public void basic() {
		URI uri = URI.create("http://neo4j.org");
		Review r = new Review();
		r.link = uri;
		r.content = "slick";
		r.stars = 5;
		
		
		graph.persist(r);
		
		Collection<Review> reviews = graph.getMostRecent(Review.class, 1);
		assertEquals(1, reviews.size());
		
		r = reviews.iterator().next();
		assertEquals(uri, r.link);
		
		Node n = graph.get(uri);
		assertEquals(n.getProperty("uri"), "http://neo4j.org");
		int counter = 0;
		for (Relationship rel : n.getRelationships(Direction.INCOMING)) {
			counter++;
			assertEquals("link", rel.getType().name());
		}
		assertEquals(1, counter);
		
		reviews = graph.find(r).where(r.stars).is(5).results();
		assertEquals(1, reviews.size());
		
	}
	
	@Test
	public void simple() {
		Transaction t = graph.beginTx();
		try {
			URI u = URI.create("http://foo.com");
			graph.persist(u);
			t.success();
		} finally {
			t.finish();
		}
	}

}

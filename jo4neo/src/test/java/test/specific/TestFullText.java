package test.specific;

import java.util.Collection;

import org.junit.Test;
import static org.junit.Assert.*;
import org.neo4j.graphdb.Transaction;

import test.BaseTest;

public class TestFullText extends BaseTest {

	@Test
	public void basic() {
		Transaction t = neo.beginTx();
		try {
			Article a = new Article();
			a.author = "Johnson";
			a.content = "this is a story bout a man name Jed, a poor mountaineer nearly kept his family fed";
			a.tags = new String[] {"a", "b", "c", "tag"};
			
			Article b = new Article();
			b.author = "Tolkein";
			b.content = "the hobbit, having had his tea, ran from the Orks and Trolls";
			b.tags = new String[] {"a", "b", "c", "tag"};
			
			graph.persist(b,a);
			t.success();
		} finally {
			t.finish();
		}
	
		Article a = new Article();
		Collection<Article> articles = graph.find(a).where(a.content).is("+his").results();
		assertEquals(2, articles.size());
		
		
		articles = graph.fullTextQuery(Article.class, "test.specific.Article.content_INDEX", "nearly Jed");
		assertEquals(1, articles.size());
		
		
		articles = graph.find(a).where(a.content).is("mountaineer").results();
		for (Article article : articles)
			graph.delete(article);

		
		articles = graph.find(a).where(a.content).is("mountaineer").results();
		assertEquals(0, articles.size());
		
		
	}

}

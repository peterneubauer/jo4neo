package test.specific;

import java.util.Arrays;

import jo4neo.ObjectGraph;
import jo4neo.ObjectGraphFactory;

import org.junit.Test;
import static org.junit.Assert.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class TestSoftRef {
	
	@Test
	public void garbageCollected() {
		GraphDatabaseService neo = new EmbeddedGraphDatabase("neo_store");
		ObjectGraph graph = ObjectGraphFactory.instance().get(neo);		

		Transaction t = graph.beginTx();
		try {
			
			for (Orchard o : graph.get(Orchard.class))
				graph.delete(o);
			Apple a = new Apple();
			a.state = "WA";
			a.type = "Red Delicious";
			
			Orchard o = new Orchard();
			o.grows = Arrays.asList(a);
			o.name = "Johnson Orchard";
			graph.persist(o);
			t.success();
		} finally {
			t.finish();
		}
		Orchard o = new Orchard();
		o = graph.find(o).where(o.name).is("Johnson Orchard").result();
		
		graph.close();
		neo.shutdown();
		neo = null;
		graph = null;
		
		System.gc();
		
		Apple a = new Apple();
		a.state = "CA";
		a.type = "Jonah Gold";
		
		boolean caught = false;
		try {
			o.grows.add(a);
		} catch (UnsupportedOperationException e) {
			caught = true;
		}
		assertTrue(caught);
		
	}

}

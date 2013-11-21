package test.bugs;

import java.util.ArrayList;
import java.util.Collection;

import jo4neo.ObjectGraph;
import jo4neo.ObjectGraphFactory;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Jo4neoTest {

	@Test
	public void noop() {
		
		GraphDatabaseService neo = new EmbeddedGraphDatabase("bugs");
		ObjectGraph graph = ObjectGraphFactory.instance().get(neo);

		Transaction t = graph.beginTx();
		try {
			
			Collection<ExampleObject> examples = graph.get(ExampleObject.class);
			for (ExampleObject example : examples) {
				graph.delete(example);
			}

			ExampleObject to = new ExampleObject("Hello", "World", 1);

			graph.persist(to);
			// to will always be added

			ExampleObject to2 = new ExampleObject("Hello", "World", 1);
			graph.persist(to2);

			ExampleObject to3 = new ExampleObject("Hello", "World", 2);

			graph.persist(to3);

			// to3 should be added as is different

			to3.objects.add(new ExampleObject("goodbye", "cruel world", 3));
			to3.objects.add(new ExampleObject("goodbyes", "cruel worlds", 4));

			graph.persist(to3);

			t.success();
		} finally {
			t.finish();
		}
		
		Collection<ExampleObject> nodes = graph.get(ExampleObject.class);
		
		Assert.assertEquals(5, nodes.size());
		
		ArrayList<Integer> numberFields = new ArrayList<Integer>(5);   
		for (ExampleObject o : nodes) {
			numberFields.add(o.number);
		}
		
		Integer ia[] = new Integer[numberFields.size()];
		ia = numberFields.toArray(ia);

		Assert.assertArrayEquals(
				new Integer[] {1,1,2,3,4}, 
				numberFields.toArray(new Integer[numberFields.size()])
		);
		
		graph.close();
		neo.shutdown();
	}
}

package test.specific;

import org.junit.Test;
import org.neo4j.graphdb.Node;

import test.BaseTest;

public class TestLoader extends BaseTest {
	
	@Test
	public void basic() {
		for (Node n :neo.getAllNodes())
			graph.get(n);
	}
}

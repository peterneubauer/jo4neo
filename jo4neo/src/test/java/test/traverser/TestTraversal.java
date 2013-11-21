package test.traverser;

import java.util.Collection;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;

import test.BaseTest;

import static org.junit.Assert.*;

public class TestTraversal extends BaseTest {
	
	@Test
	public void basic() {
		Transaction t = neo.beginTx();
		try {
			Module root = new Module("root");
			Module child = new Module("child");
			BasicItem item = new BasicItem("item");
			root.addBasicItem(item);
			root.addModule(child);
			graph.persist(root);
			t.success();
		} finally {
			t.finish();
		}	
		
		Collection<Module> modules = graph.get(Module.class);
		assertEquals(modules.size(),2);
		for (Module module : modules) {
			if (module.getName().equals("root")) {
				assertEquals(1, module.getBasicItems().size());
				assertEquals(1, module.getBasicItems().size());
				assertEquals(2, module.getElements().size());
			} else {
				assertEquals(0, module.getBasicItems().size());
				assertEquals(0, module.getModules().size());
				assertEquals(0, module.getElements().size());
			}

			for (@SuppressWarnings("unused") Object obj : module.getElements()) {
				// only root gets here
				assertEquals(module.getName(), "root");
			}
		}
	}
}

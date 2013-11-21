package jo4neo;



import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Traverser;

public class DefaultTraverserProvider implements TraverserProvider {
	public Traverser get(Node n) {
		return null;
	}
}

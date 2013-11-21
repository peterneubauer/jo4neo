package test.traverser;

import jo4neo.TraverserProvider;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

public class ModuleTraverser implements TraverserProvider {
	public ModuleTraverser() {}
	public Traverser get(Node n) {
		return n.traverse(Traverser.Order.BREADTH_FIRST,
				StopEvaluator.END_OF_GRAPH,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				RelationShips.HAS_BASIC_ITEM, Direction.OUTGOING,
				RelationShips.HAS_MODULE, Direction.OUTGOING);
	}
}
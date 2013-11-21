package example;

import jo4neo.TraverserProvider;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

public class RoleMembers implements TraverserProvider {

	public Traverser get(Node n) {
		return n.traverse( 
				Order.BREADTH_FIRST, 
				StopEvaluator.END_OF_GRAPH, 
				ReturnableEvaluator.ALL_BUT_START_NODE, 
				UserRoleRelationships.role, 
				Direction.INCOMING,
				UserRoleRelationships.parent,
				Direction.INCOMING);
	}
}

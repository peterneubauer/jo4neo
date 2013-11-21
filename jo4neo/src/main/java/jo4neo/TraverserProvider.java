package jo4neo;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Traverser;

/**
 * Used along with {@link neo} annotation to create a collection
 * based on nodes selected by a standard neo4j traverser.
 * 
 * <pre>
 * <code>
 * {@literal @}neo(traverser=MyTraverserProvider.class) public Collection&lt;DomainType&gt; members;
 * </code>
 * </pre>
 * 
 * The nodes selected by your traversal are filtered by the generic type so that in the 
 * example, only nodes that can be seen as a "DomainType" are included.  Changes to this type of
 * object property are never saved or persisted (adding or removing members for example).  
 * It is effectively passive and should be 
 * used as a way to query your graph.
 * 
 * @author Taylor Cowan
 *
 */
public interface TraverserProvider {
	public Traverser get(Node n);
}

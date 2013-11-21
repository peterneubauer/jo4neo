package jo4neo;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import jo4neo.fluent.Where;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

/**
 * Interface used to interact with neo4j in an object oriented manner.
 * 
 * An <code>ObjectGraph</code> instance is associated with a particular
 * <code>NeoService</code> instance.  It is thread safe and therefore you can
 * only have one ObjectGraph instance per NeoService.
 * 
 * @author Taylor Cowan
 * 
 * @see ObjectGraphFactory
 *
 */
public interface ObjectGraph {

	
	/**
	 * As in neo4j, starts a new transaction and associates it with the current thread.
	 * @return a transaction from NeoService.
	 */
	Transaction beginTx();

	/**
	 * Mirror a java object within the neo4j graph.  Only fields annotated with {@code}neo
	 * will be considered.
	 * @param o
	 */
	<A> void persist(A... o);

	/**
	 * removes all data representing an object from the graph.
	 * 
	 * @param o an object retrieved from this {@link ObjectGraph}
	 */
	void delete(Object... o);
	
	
	/**
	 * Looks up a neo4j graph node using it's java object
	 * mirror. 
	 * @param o an object retrieved from this {@link ObjectGraph}
	 * @return neo4j node represented by o
	 */
	Node get(Object o);
	
	/**
	 * Looks up all instances of {@code}type in the graph.
	 * 
	 * @param type a type previously stored in the graph
	 * @return a Collection of {@code}type instances.
	 */
	<T> Collection<T> get(Class<T> type);

	/**
	 * Type safe lookup of object given it's neo4j nodeid.
	 * Your domain classes may use {@link Nodeid#id()} to discover
	 * their neo4j nodeid.   
	 *  
	 * @param t
	 * @param key neo4j node id.
	 * @return
	 */
	<T> T get(Class<T> t, long key);

	
	/**
	 * Return an object represented by <code>node</code> provided
	 * the node was created by jo4neo.
	 * 
	 * @param node
	 * @return an object that mirrors node.
	 */
	Object get(Node node);
	
	/**
	 * Looks up the node representation of a given 
	 * uri.  This method may update your graph if no node
	 * was previously allocated for the uri.
	 * 
	 * @return the node representation of uri.
	 */
	Node get(URI uri);


	/**
	 * Unmarshal a collections of nodes into objects.
	 * 
	 */
	<T> Collection<T> get(Class<T> type, Iterable<Node> nodes);

	/**
	 * Closes this ObjectGraph after which it will be unavailable 
	 * for use.  Calling close is necessary, and should be called even
	 * if the application is halted suddenly. ObjectGraph's maintain 
	 * a lucene index which may become corrupt without proper shutdown.
	 * 
	 */
	void close();

	/**
	 * Begin fluent interface find.  <code>a</code> should be 
	 * a newly constructed instance as it's contents will be modified/initialized
	 * by this call.
	 * <pre>
	 * <code>
	 *   Customer customer = new Customer();
	 *   customer = graph.find(customer).where(customer.id).is(123).result();
	 * </code>
	 * </pre>
	 * 
	 * @param a
	 * @return
	 */
	<A> Where<A> find(A a);

	/**
	 * Counts child entities without loading objects into memory.  This is preferable to 
	 * using Collection.size(), which would load the full collection into memory.
	 * <pre>
	 * <code>
	 *   Customer customer = new Customer();
	 *   customer = graph.find(customer).where(customer.id).is(123).result();
	 *   long numOrders = graph.count(customer.orders);
	 * </code>
	 * </pre>
	 * 
	 * @param values a collection value from a jo4neo annotated field.
	 * @return
	 */
	long count(Collection<? extends Object> values);

	/**
	 * Returns a collection of entities added since <code>d</code>.
	 * Type <code>t</code> must be annotated with {@link Timeline}
	 * 
	 * @see Timeline
	 * 
	 */
	<T> Collection<T> getAddedSince(Class<T> t, Date d);

	/**
	 * Returns a collection of entities added bewteen dates from and to.
	 * Type <code>t</code> must be annotated with {@link Timeline}.
	 * 
	 * @see Timeline
	 */
	<T> Collection<T> getAddedBetween(Class<T> t, Date from,
			Date to);

	
	/**
	 * Returns up to <code>max</code> most recently added instances of type <code>t</code>
	 * 
	 * @param max limit the number of instances returned
	 * @see neo#recency()
	 */
	<T> Collection<T> getMostRecent(Class<T> t, int max);
	
	
	<T> T getSingle(Class<T> t, String indexname, Object value);
	
	<T> Collection<T> get(Class<T> t, String indexname, Object value);
	
	<T> Collection<T> fullTextQuery(Class<T> t, String indexname, Object value);

}
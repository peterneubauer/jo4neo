package jo4neo.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import jo4neo.ObjectGraph;
import jo4neo.neo;
import jo4neo.fluent.Where;
import jo4neo.util.Lazy;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

/**
 * 
 * 
 *
 */
class ObjectGraphImpl implements ObjectGraph {

	IndexedNeo ineo;

	public ObjectGraphImpl(GraphDatabaseService neo) {
		ineo = new IndexedNeo(neo);
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#beginTx()
	 */
	public Transaction beginTx() {
		return ineo.beginTx();
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#persist(A)
	 */
	public <A> void persist(A... o) {
		new PersistOperation<A>(ineo).save(o);
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#get(java.lang.Object)
	 */
	public Node get(Object o) {
		return ineo.asNode(o);
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#delete(java.lang.Object)
	 */
	public void delete(Object... o) {
		new DeleteOpertation(ineo).delete(o);
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#get(java.lang.Class, long)
	 */
	public <T> T get(Class<T> t, long key) {
		return new LoadOperation<T>(t, ineo).load(key);
	}
	
	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#get(org.neo4j.graphdb.Node)
	 */
	public Object get(Node node) {
		return new LoadOperation<Object>(Object.class, ineo).load(node);
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#close()
	 */
	public void close() {
		ineo.close();
	}

	public <T> Collection<T> get(Class<T> t, String indexname, Object value) {
		ArrayList<T> list = new ArrayList<T>();
		for (Node n : ineo.getIndexService().get(indexname, value))
			list.add(get(t, n.getId()));
		return list;
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#find(A)
	 */
	public <A> Where<A> find(A a) {
		return new FieldValueMap<A>(a, this);
	}
	
	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#count(java.util.Collection)
	 */
	public long count(Collection<? extends Object> values) {
		if (values instanceof Lazy)
			return ((Lazy) values).getCount();
		return 0;
	}
	
	public <T> T getSingle(Class<T> t, String indexname, Object value) {
		return new LoadOperation<T>(t, ineo).load(indexname, value);
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#get(java.lang.Class)
	 */
	public <T> Collection<T> get(Class<T> t) {
		return new LoadOperation<T>(t, ineo).loadAll();
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#getAddedSince(java.lang.Class, java.util.Date)
	 */
	public <T> Collection<T> getAddedSince(Class<T> t, Date d) {
		return new LoadOperation<T>(t, ineo).since(d.getTime());
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#getAddedBetween(java.lang.Class, java.util.Date, java.util.Date)
	 */
	public <T> Collection<T> getAddedBetween(Class<T> t, Date from, Date to) {
		return new LoadOperation<T>(t, ineo).within(from.getTime(), to
				.getTime());
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#getMostRecent(java.lang.Class, int)
	 */
	public <T> Collection<T> getMostRecent(Class<T> t, int max) {
		if ( supportsRecency(t))
			return new LoadOperation<T>(t, ineo).latest(max);
		else 
			throw new RuntimeException("Recency unsupported for " + t + ": must be annotated as @neo(recency=true)");
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#get(java.net.URI)
	 */
	public Node get(URI uri) {
		return ineo.getURINode(uri);
	}

	/* (non-Javadoc)
	 * @see jo4neo.ObjectGraph#get(java.lang.Class, java.lang.Iterable)
	 */
	public <T> Collection<T> get(Class<T> type, Iterable<Node> nodes) {
		return new LoadOperation<T>(type, ineo).loadAndFilter(nodes);
	}

	private boolean supportsRecency(Class<?> c) {
		return c.isAnnotationPresent(neo.class) && c.getAnnotation(neo.class).recency();
	}

	/**
	 * returns a collection of objects that match the query string (full-text).
	 * @param t the type of the returned objects
	 * @param indexname the key
	 * @param value the query string, (part of) the value
	 * @return a collection with all hits
	 */
	public <T> Collection<T> fullTextQuery(Class<T> t, String indexname,
			Object value) {
		ArrayList<T> list = new ArrayList<T>();
		Index<Node> index = ineo.getFullTextIndexService();
		for (Node n : index.query(indexname, value))
			list.add(get(t, n.getId()));
		return list;
	}

}

/**
 * jo4neo is a java object binding library for neo4j Copyright (C) 2009 Taylor
 * Cowan
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

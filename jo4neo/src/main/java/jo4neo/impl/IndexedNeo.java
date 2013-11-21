package jo4neo.impl;

import static jo4neo.Relationships.JO4NEO_HAS_TYPE;
import static jo4neo.impl.TypeWrapperFactory.$;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import jo4neo.Nodeid;
import jo4neo.util.RelationFactory;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.event.KernelEventHandler;
import org.neo4j.graphdb.event.TransactionEventHandler;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.index.lucene.LuceneTimeline;
import org.neo4j.index.lucene.TimelineIndex;

class IndexedNeo implements GraphDatabaseService {

	private GraphDatabaseService neo;
	private Index<Node> index;
	private Index<Node> ftindex;
	private RelationFactory relFactory;
	private boolean isClosed = false;

	private Map<Class<?>, TimelineIndex<Node>> timelines;

	public IndexedNeo(GraphDatabaseService neo) {
		this.neo = neo;
		IndexManager im = neo.index();
		index = im.forNodes("default");

		ftindex = im.forNodes("default-fulltext", MapUtil.stringMap(
				IndexManager.PROVIDER, "lucene", "type", "fulltext"));
		;
		relFactory = new RelationFactoryImpl();
		timelines = new HashMap<Class<?>, TimelineIndex<Node>>();
	}

	public synchronized void close() {
		isClosed = true;
	}

	public Index<Node> getIndexService() {
		return index;
	}

	public Index<Node> getFullTextIndexService() {
		return ftindex;
	}

	public Transaction beginTx() {
		return neo.beginTx();
	}

	public Iterable<Node> getAllNodes() {
		return neo.getAllNodes();
	}

	public Node getNodeById(long arg0) {
		return neo.getNodeById(arg0);
	}

	public Node getNodeById(Nodeid id) {
		return neo.getNodeById(id.id());
	}

	public Node getReferenceNode() {
		return neo.getReferenceNode();
	}

	public Relationship getRelationshipById(long arg0) {
		return neo.getRelationshipById(arg0);
	}

	public Iterable<RelationshipType> getRelationshipTypes() {
		return neo.getRelationshipTypes();
	}

	public void shutdown() {
		close();
		neo.shutdown();
	}

	public RelationFactory getRelationFactory() {
		return relFactory;
	}

	public Node createNode() {
		return neo.createNode();
	}

	public Node asNode(Object o) {
		return getNodeById($(o).id(o));
	}

	public Node getMetaNode(Class<?> type) {
		Node metanode;
		RelationshipType relType = DynamicRelationshipType.withName(type
				.getName());
		Node root = neo.getReferenceNode();
		Iterable<Relationship> r = root.getRelationships(relType);
		if (r.iterator().hasNext())
			metanode = r.iterator().next().getEndNode();
		else {
			Transaction tx = neo.beginTx();
			try {
				metanode = neo.createNode();
				metanode.setProperty(Nodeid.class.getName(), type.getName());
				root.createRelationshipTo(metanode, relType);
				tx.success();
			} finally {
				tx.finish();
			}
		}
		return metanode;
	}

	public TimelineIndex<Node> getTimeLine(Class<?> c) {

		if (timelines.containsKey(c))
			return timelines.get(c);
		// TODO
		// Node metaNode = getMetaNode(c);
		TimelineIndex<Node> t = new LuceneTimeline<Node>(neo, neo.index()
				.forNodes(c.getName()));
		timelines.put(c, t);
		return t;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public IndexManager index() {
		return neo.index();
	}

	public KernelEventHandler registerKernelEventHandler(
			KernelEventHandler handler) {
		return neo.registerKernelEventHandler(handler);
	}

	public <T> TransactionEventHandler<T> registerTransactionEventHandler(
			TransactionEventHandler<T> handler) {
		return neo.registerTransactionEventHandler(handler);
	}

	public KernelEventHandler unregisterKernelEventHandler(
			KernelEventHandler handler) {
		return neo.unregisterKernelEventHandler(handler);
	}

	public <T> TransactionEventHandler<T> unregisterTransactionEventHandler(
			TransactionEventHandler<T> handler) {
		return neo.unregisterTransactionEventHandler(handler);
	}

	public Node getURINode(URI uri) {
		Transaction t = neo.beginTx();
		try {
			Node n = getIndexService().get(URI.class.getName(), uri.toString()).getSingle();
			if (n == null) {
				n = createNode();
				getIndexService().add(n, URI.class.getName(), uri.toString());
				n.setProperty("uri", uri.toString());
				n.setProperty(Nodeid.class.getName(), URI.class.getName());
				// find metanode for type t
				Node metanode = getMetaNode(URI.class);
				n.createRelationshipTo(metanode, JO4NEO_HAS_TYPE);
			}
			t.success();
			return n;
		} finally {
			t.finish();
		}
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

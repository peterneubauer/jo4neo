package jo4neo.impl;

import java.lang.annotation.Annotation;
import jo4neo.Nodeid;
import jo4neo.Timeline;
import jo4neo.neo;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import static jo4neo.Relationships.*;


public class DefaultNodeid implements InjectedNodeid {
	
	long id;
	Class<?> type;
	
	public DefaultNodeid() {}
	
	public DefaultNodeid(long id, Class<?> c) {
		this.id = id;
		type = c;
	}
	
	public DefaultNodeid(Class<?> c) {
		this.id = Long.MIN_VALUE;
		type = c;
	}
	
	/* (non-Javadoc)
	 * @see jo4neo.Nodeid#id()
	 */
	public long id() {
		return id;
	}

	/* (non-Javadoc)
	 * @see jo4neo.Nodeid#hostingType()
	 */
	public String hostingType() {
		return type.getName();
	}
	
	/* (non-Javadoc)
	 * @see jo4neo.Nodeid#type()
	 */
	public Class<?> type() {
		return type;
	}

	/* (non-Javadoc)
	 * @see jo4neo.Nodeid#valid()
	 */
	public boolean valid() {
		return id != Long.MIN_VALUE;
	}
	

	
	public boolean equals(Object o) {
		if (o instanceof DefaultNodeid)
			return id == ((DefaultNodeid)o).id;
		return false;
	}

	/* (non-Javadoc)
	 * @see jo4neo.Nodeid#mirror(jo4neo.IndexedNeo)
	 */
	public Node mirror(IndexedNeo neo) {
		return (valid()) ? neo.getNodeById(id): newNode(neo);
	}


	/**
	 * Creates a new node within the context of a given javaclass.  First the node
	 * is annotated with the classname.  The neo4j node's id, as a long, is remembered
	 * so that the containing java object knows to where and from where its values will be persisted.
	 * Next the node is related to a "metanode" which represents metainformation about the javaclass
	 * within the neo graph.  This allows jo4neo to find all instances of a given type easily.
	 * Finally, if the javaclass is annotated with the Timeline annotation, the new node
	 * is stored within a timeline rooted at the javaclasses metanode. 
	 * @param neo
	 * @return
	 */
	private Node newNode(IndexedNeo neo) {
		Node newNode = neo.createNode();
		newNode.setProperty(Nodeid.class.getName(), type.getName());
		id = newNode.getId();
		//find metanode for type t
		Node metanode = neo.getMetaNode(type);		
		newNode.createRelationshipTo(metanode, JO4NEO_HAS_TYPE);	
		if (type.isAnnotationPresent((Class<? extends Annotation>) Timeline.class))
			neo.getTimeLine(type).add(newNode, System.currentTimeMillis());
		
		//delete "latest" relation
		if (type.isAnnotationPresent(neo.class) && type.getAnnotation(neo.class).recency())
			recencyStack(newNode, metanode);

		return newNode;	
	}

	private void recencyStack(Node newNode, Node metanode) {
		Node latest=null;
		for(Relationship r : metanode.getRelationships(JO4NEO_NEXT_MOST_RECENT, Direction.OUTGOING)) {
			latest = r.getEndNode();
			r.delete();
		}
		if (latest!=null)
			newNode.createRelationshipTo(latest, JO4NEO_NEXT_MOST_RECENT);
		metanode.createRelationshipTo(newNode, JO4NEO_NEXT_MOST_RECENT);
	}

}

/**
 * jo4neo is a java object binding library for neo4j
 * Copyright (C) 2009  Taylor Cowan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
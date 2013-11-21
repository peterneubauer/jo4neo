package jo4neo.impl;

import static jo4neo.impl.TypeWrapperFactory.$;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jo4neo.util.Lazy;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;



class PersistOperation<T> {

	IndexedNeo neo;
	Map<Long, Object> visited;

	public PersistOperation(IndexedNeo neo) {
		this.neo = neo;
		visited = new HashMap<Long, Object>();
	}

	public void save(Object... o) {
		Transaction t = neo.beginTx();
		try {
			for (Object bean : o) 
				save(asNode(bean), bean);			
			t.success();
		} finally {
			t.finish();
		}
	}

	private void save(Node node, Object o) {
		/*
		 * cycle detection
		 * object graphs may contain cycles, which would cause
		 * infinite recursion without this check
		 */
		if (visited.containsKey(node.getId()))
			return;
		visited.put(node.getId(), node);
		for (FieldContext field : $(o).getFields(o))
			save(node, field);
	}

	private void save(Node node, FieldContext field) {
		if (field.isInverse() || field.isTraverser())
			initializeIfNull(field);
		else if (field.isSimpleType())
			saveAndIndex(node, field);
		else if (field.isPluralPrimitive())
			saveAndIndex(node, field);
		else if (field.isSingular())
			relate(node, field);
		else if (field.isPlural())
			relations(node, field);
	}

	private void initializeIfNull(FieldContext field) {
		if (field.isPluralComplex())
			field.setProperty(ListFactory.get(field, new LoadOperation<T>(neo)));
	}

	private void saveAndIndex(Node node, FieldContext field) {
		field.applyTo(node);
		if (field.value() != null && field.isIndexed()) {
			index().remove(node, field.getIndexName(),
					field.value());
			index().add(node, field.getIndexName(),
					field.value());
		} else if (field.value() != null && field.isFullText() ) {
			Index<Node> is = neo.getFullTextIndexService();
			is.remove(node, field.getIndexName(), field.value());
			is.add(node, field.getIndexName(), field.value());
		}
	}

	private Index<Node> index() {
		return neo.getIndexService();
	}

	private void relations(Node node, FieldContext field) {
		Collection<?> values = field.values();
		RelationshipType reltype = field.toRelationship(neo.getRelationFactory());
		
		//initialize null collections to a lazy loader
		if (values == null) {
			field.setProperty(ListFactory.get(field, new LoadOperation<T>(neo)));
			return;
		}
		
		/*
		 *  Ignore unmodified collections.
		 */
		if (values instanceof Lazy) {
			if (!((LazyList) values).modified())
				return;
			values = ((LazyList)values).newdata();
		}
		
		for (Object value : values) {
			Node n2 = asNode(value);
			if (! related(node, n2, reltype))
				node.createRelationshipTo(n2, reltype);
			save(n2, value);
		}
		
		//initialize collections to a lazy loader
		if (! (values instanceof Lazy))
			field.setProperty(ListFactory.get(field, new LoadOperation<T>(neo)));

		
	}

	private boolean related(Node a, Node b, RelationshipType type) {
		 for ( Relationship rel : a.getRelationships( type, OUTGOING ) ) {
	          if ( rel.getOtherNode( a ).equals( b ) )
	              return true;	          
	      }
		 return false;
	}

	private Node asNode(Object value) {
		return $(value).id(value).mirror(neo);		
	}

	private void relate(Node node, FieldContext field) {
		RelationshipType reltype = field.toRelationship(neo.getRelationFactory());
		deleteAll(node, reltype);		
		if (field.value() == null)
			return;
		Object value = field.value();
		boolean isNew = ! $(value).id(value).valid();
		Node n2 = asNode(value);
		node.createRelationshipTo(n2, reltype);
		if (isNew)
			save(n2, field.value());
	}

	private void deleteAll(Node node, RelationshipType reltype) {
		for (Relationship r : node.getRelationships(reltype, Direction.OUTGOING))
			r.delete();
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
package jo4neo.impl;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;



import jo4neo.util.Lazy;

public class LazyList implements Lazy {

	private transient FieldContext field;
	private transient SoftReference<LoadCollectionOps> loader;
	private transient Collection<Object> newdata;

	private Collection<Object> data;
	private boolean modified = false;
	
	public LazyList(FieldContext f, LoadCollectionOps loader) {
		field = f;
		this.loader = new SoftReference<LoadCollectionOps>(loader);
	}
	
	public long getCount() {
		return graph().count(field, Direction.OUTGOING);
	}
	
	private LoadCollectionOps graph() {
		LoadCollectionOps graph = loader.get();
		if (graph == null || graph.isClosed())
			throw new UnsupportedOperationException("Neo graph is closed");
		return graph;
	}

	private Collection<Object> data() {
		if (data == null)
			data = graph().load(field);
		return data;
	}
	
	protected Collection<Object> newdata() {
		if ( newdata == null)
			newdata = new ArrayList<Object>();
		return newdata;
	}
	
	protected Collection<Object> consumeUpdates() {
		Collection<Object> updates = newdata();
		newdata = null;
		return updates;
	}

	public boolean add(Object e) {
		modified = true;
		if (data().add(e)) {
			newdata().add(e);
			return true;
		}
		return false;
	}

	public boolean addAll(Collection<?> c) {
		modified = true;
		return data().addAll(c);
	}


	public void clear() {
		modified = true;
		data().clear();
	}

	public boolean contains(Object o) {
		return data().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return data().containsAll(c);
	}

	public boolean equals(Object o) {
		return data().equals(o);
	}

	public int hashCode() {
		return data().hashCode();
	}

	public boolean isEmpty() {
		return data().isEmpty();
	}

	public Iterator<Object> iterator() {
		return data().iterator();
	}

	public boolean remove(Object o) {
		modified = true;
		if (data().remove(o))
			graph().removeRelationship(field, o);
		return data().remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		modified = true;
		return data().removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		modified = true;
		return data().retainAll(c);
	}
	
	public int size() {
		return data().size();
	}

	public Object[] toArray() {
		return data().toArray();
	}

	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a) {
		return data().toArray(a);
	}

	public boolean isConnected() {
		return data != null;
	}

	public boolean modified() {
		return modified;
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
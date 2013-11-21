package jo4neo.impl;

import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;



import jo4neo.util.Lazy;

/**
 * Represents implied relationships.
 * members cannot be added or removed as this
 * is contingent upon relationships declared from another
 * entity.
 *
 */
@SuppressWarnings("unchecked")
class InverseList implements Lazy {

	private transient FieldContext field;
	private transient LoadOperation<?> loader;
	private Collection<Object> data;
	
	public InverseList(FieldContext f, LoadOperation<?> neo) {
		field = f;
		this.loader = neo;
	}

	public long getCount() {
		return loader.count(field, Direction.INCOMING);
	}
	
	private Collection<Object> data() {
		if ( data == null)
			data = loader.loadInverse(field);
		return data;
	}
	
	public boolean add(Object e) {
		return false;
	}

	public boolean addAll(Collection<?> c) {
		return false;
	}


	public void clear() {

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
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		return false;
	}
	
	public int size() {
		return data().size();
	}

	public Object[] toArray() {
		return data().toArray();
	}

	public Object[] toArray(Object[] a) {
		return data().toArray(a);
	}

	public boolean isConnected() {
		return data != null;
	}

	public boolean modified() {
		return false;
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
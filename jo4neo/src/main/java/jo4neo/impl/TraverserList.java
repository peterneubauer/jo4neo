package jo4neo.impl;

import java.util.Collection;
import java.util.Iterator;



import jo4neo.util.Lazy;

/**
 *
 */
@SuppressWarnings("unchecked")
class TraverserList implements Lazy {

	private transient FieldContext field;
	private transient LoadOperation<?> loader;
	private Collection<?> data;
	
	public TraverserList(FieldContext f, LoadOperation<?> neo) {
		field = f;
		this.loader = neo;
	}
	
	public long getCount() {
		throw new UnsupportedOperationException();
	}

	private Collection<?> data() {
		if ( data == null)
			data = loader.loadTraverser(field);
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
		return (Iterator<Object>) data().iterator();
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
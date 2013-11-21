package jo4neo.impl;

import java.util.Collection;
import java.util.Iterator;

import jo4neo.ObjectGraph;
import jo4neo.fluent.Result;

public class FullTextResultImpl<T> implements Result<T> {

	Class<T> c;
	String indexName;
	Object o;
	ObjectGraph pm;

	public FullTextResultImpl(ObjectGraph pm, Class<T> c, String indexName, Object o) {
		this.o = o;
		this.indexName = indexName;
		this.c = c;
		this.pm = pm;
	}

	public T result() {
		Iterator<T> it = results().iterator();
		return (it.hasNext()) ? it.next() : null;
	}

	public Collection<T> results() {
		return pm.fullTextQuery(c,indexName,o);
	}

}

package example.model;

import jo4neo.Nodeid;
import example.ContextListener;

public class NeoBean<T> {

	transient Nodeid neo;

	public long getId() {
		return neo.id();
	}
	
	public T save() { 
		ContextListener.pm.persist(this);
		return (T)this;
	}
	
}

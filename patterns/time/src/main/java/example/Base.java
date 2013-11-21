package example;

import jo4neo.Nodeid;

public class Base {
	private transient Nodeid id;
	
    public long nodeid() {
    	return id.id();
    }
	
}

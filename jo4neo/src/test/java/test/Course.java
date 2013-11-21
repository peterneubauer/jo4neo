package test;

import jo4neo.Nodeid;
import jo4neo.neo;

public class Course {
	
	Nodeid neo;
	@neo String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

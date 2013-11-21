package test;

import java.util.ArrayList;
import java.util.Collection;

import jo4neo.Nodeid;
import jo4neo.neo;


public class Tag {
	Nodeid neo;
	
	public static final String NAME_INDEX = "test.Tag.name_INDEX";
	
	@neo(index=true)
	String name;
	
	@neo
	Collection<Taggable> items = new ArrayList<Taggable>();
	
	public Collection<Taggable> getItems() {
		return items;
	}
	public void setItems(Collection<Taggable> items) {
		this.items = items;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

package test;

import java.util.ArrayList;
import java.util.Collection;

import jo4neo.Nodeid;
import jo4neo.neo;


public class Student {
	Nodeid neo;
	
	@neo 
	Collection<Course> courses = new ArrayList<Course>();

	@neo 
	String name;
	
	public Collection<Course> getCourses() {
		return courses;
	}
	
	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}

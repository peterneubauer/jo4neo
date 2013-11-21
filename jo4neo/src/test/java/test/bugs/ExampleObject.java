/**
 * 
 */
package test.bugs;

import java.util.ArrayList;
import java.util.Collection;

import jo4neo.Nodeid;
import jo4neo.neo;

public class ExampleObject  {
	
	transient Nodeid neo;
	
	public ExampleObject() {}
	
	public ExampleObject(String stringOne, String stringTwo, int number) {
		super();
		this.stringOne = stringOne;
		this.stringTwo = stringTwo;
		this.number = number;
		/*strings = new ArrayList<String>();
		strings.add(stringOne);
		strings.add(stringTwo);*/
		objects = new ArrayList<ExampleObject>();
		
	}
	
	@neo(index=true) public String stringOne;
	@neo(index=true) public String stringTwo;
	@neo(index=true) public int number;
	@neo public Collection<ExampleObject> objects;
	
}
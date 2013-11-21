package example;

import java.util.Collection;

import jo4neo.neo;

public class Day extends Base {
	
	@neo(inverse="hasDay") public Collection<Hour> hours;
	@neo("hasMonth") public Month parent;
	@neo public int value;
	
	public Day() {}
	
	public Day(int dayNum, Month month) {
		value = dayNum;
		parent = month;
	}

}

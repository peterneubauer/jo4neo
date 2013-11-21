package example;

import java.util.Collection;

import jo4neo.neo;

public class Month extends Base {
	@neo(inverse="hasMonth") public Collection<Day> days;
	@neo("hasYear") public Year parent;
	@neo public int value;
	
	public Month() {}
	
	public Month(int month) {
		value = month;
	}
	
	public Day getDay(int i) {
		for (Day d : days)
			if (d.value == i)
				return d;
		return null;
	}



}

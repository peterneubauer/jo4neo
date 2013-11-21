package example;

import java.util.Calendar;
import java.util.Collection;

import jo4neo.neo;

public class Year extends Base {
	@neo(inverse="hasYear") public Collection<Month> months;
	@neo(index=true) public int value;
	
	public Year() {}
	
	public Year(Calendar cal) {
		value = cal.get(Calendar.YEAR);
	}
	
	public Month getMonth(int i) {
		for (Month m : months)
			if (m.value == i)
				return m;
		return null;
	}
}

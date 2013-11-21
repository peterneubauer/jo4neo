package example;

import java.util.Collection;

import jo4neo.neo;

public class Hour extends Base {
	@neo public int value;
	@neo("hasDay") public Day parent;
	@neo(inverse="hour") public Collection<Event> events;
}

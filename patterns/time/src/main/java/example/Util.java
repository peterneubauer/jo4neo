package example;

import java.util.Calendar;

import jo4neo.ObjectGraph;

import org.neo4j.graphdb.Transaction;

public class Util {

	ObjectGraph graph;

	public Util(ObjectGraph graph) {
		this.graph = graph;
	}

	public Day findDay(Calendar cal) {
		int yearNum = cal.get(Calendar.YEAR);
		int monthNum = cal.get(Calendar.MONTH);
		int dayNum = cal.get(Calendar.DAY_OF_MONTH);
		Day day = null;
		Transaction t = graph.beginTx();
		try {
			Year y = new Year();
			y = graph.find(y).where(y.value).is(yearNum).result();
			if (y == null)
				y = newYear(cal);
			Month month = y.getMonth(monthNum);
			day = month.getDay(dayNum);
			if (day == null)
				day = newDay(dayNum, month);
			t.success();
			return day;
		} finally {
			t.finish();
		}
	}

	private Year newYear(Calendar cal) {
		Year y = new Year(cal);
		for (int i = 0; i < 12; i++) {
			Month month = new Month(i);
			month.parent = y;
			graph.persist(month);
		}
		return y;
	}
	
	private Day newDay(int dayNum, Month month) {
		Day day = new Day(dayNum, month);
		for (int i = 0; i < 24; i++) {
			Hour h = new Hour();
			h.value = i;
			h.parent = day;
			graph.persist(h);
		}
		return day;
	}
}

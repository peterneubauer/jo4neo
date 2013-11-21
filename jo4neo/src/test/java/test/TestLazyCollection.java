package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.TreeSet;

import jo4neo.Nodeid;
import jo4neo.impl.DefaultNodeid;
import jo4neo.impl.NeoComparator;
import jo4neo.impl.TypeWrapper;
import jo4neo.impl.TypeWrapperFactory;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;


public class TestLazyCollection extends BaseTest {


	
	@Test
	public void basic() {
		TreeSet<Object> set = new TreeSet<Object>(new NeoComparator());

		Person p1 = new Person();
		Person p2 = new Person();
		Person p3 = new Person();

		TypeWrapper tw = TypeWrapperFactory.$(p3);
		Nodeid neo = new DefaultNodeid(12, Person.class);
		tw.setId(p3, neo);

		set.add(p1);
		assertTrue(set.add(p2));
		assertEquals(set.size(), 2);

		assertTrue(set.add(p3));
		assertEquals(set.size(), 3);
	}
	
	@Test
	public void initOnSave() {
		Transaction t = graph.beginTx();
		try {
			State ca = new State();
			ca.setCode("CA");
			ca.setName("California");
			graph.persist(ca);
			assertNotNull(ca.cities);
			assertEquals(0, ca.cities.size());	
			t.success();
		} finally {
			t.finish();
		}
	}

	@Test
	public void threaded() throws InterruptedException {
		Transaction t = graph.beginTx();
		try {
			State ny = new State();
			ny.setCode("NY");
			ny.setName("New York");
			graph.persist(ny);
		
			final City nyc = new City();
			nyc.setName("new york city");
			nyc.setState(ny);
			graph.persist(nyc);
			t.success();
		} finally {
			t.finish();
		}

		Runnable doit = new Runnable() {
			public void run() {
				Transaction t = graph.beginTx();
				try {
					State state = new State();
					State s = graph.find(state).where(state.code).is("NY").result();				
					City c = new City();
					c.setName("unknown");
					s.getCities().add(c);			
					graph.persist(s);
					t.success();				
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					t.finish();
				}
			}
		};
		
		for (int i=0; i<10; i++) {
			Thread thread = new Thread(doit);
			thread.start();
			thread.join();
		}

		t = graph.beginTx();
		try {
			State state = new State();
			State ny = new State();
			ny = graph.find(state).where(state.code).is("NY").result();
			assertEquals(10, ny.getCities().size());
			t.success();
		} finally {
			t.finish();
		}

	}

}
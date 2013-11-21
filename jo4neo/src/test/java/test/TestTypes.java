package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;


public class TestTypes extends BaseTest {
	
	@Test
	public void index() {
		Transaction t = graph.beginTx();
		try {
			
			for (int i=0; i<100; i++) {
				FunkyWinkerBean o = new FunkyWinkerBean();
				o.setJ(i%10);
				graph.persist(o);
			}
			t.success();
		} finally {
			t.finish();
		}
		
		
		t = graph.beginTx();
		try {
			
			FunkyWinkerBean bean = new FunkyWinkerBean();
			Collection<FunkyWinkerBean> results = graph.find(bean).where(bean.j).is(9).results();
			assertEquals(10, results.size());
			
			results = graph.find(bean).where(bean.j).is(3).results();
			assertEquals(10, results.size());

		} finally {
			t.finish();
		}
	}
	@Test
	public void basic() {
		Transaction t = graph.beginTx();
		try {
			
			TypeBean bean = new TypeBean();
			//bean.setId(27);
			bean.setAges( new int[] {1,2,3,4,10});
			bean.setDate(new Date());
			bean.setIntItem(15);
			bean.setLongItem(10001);
			bean.setNames( new String[] {"a", "b", "c", "d" });
			bean.setTags(Arrays.asList("a", "b", "c", "d"));
			bean.setWaar(true);
			bean.setOnwaar(false);
			bean.setOokwaar(true);
			
			graph.persist(bean);
			
			TypeBean check = graph.get(TypeBean.class, bean.neo.id());
			assertEquals(15, check.getIntItem());
			assertArrayEquals(new String[] {"a", "b", "c", "d" }, check.getNames());
			assertArrayEquals(new int[] {1,2,3,4,10}, check.getAges());		
			assertNotNull(check.getTags());
			assertEquals(4, check.getTags().size());
			assertTrue(check.getWaar());
			assertFalse(check.getOnwaar());
			assertTrue(check.getOokwaar());
			
			t.success();
		
		} finally {
			t.finish();
		}
	}

}

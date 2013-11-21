package test;

import static org.junit.Assert.assertEquals;
import jo4neo.fluent.Where;
import jo4neo.impl.FieldValueMap;

import org.junit.Test;


public class TestFieldValueMap {
	
	@Test
	public void basic() {
		
		Person p = new Person();
		p.setAge(27);
		p.setFirstName("taylor");
		p.setLastName("cowan");

		
		FieldValueMap<?> map = new FieldValueMap<Object>(p, null);
		
		assertEquals(map.getField(p.age).getFieldname(), "age");
		assertEquals(map.getField(p.address).getFieldname(), "address");
		assertEquals(map.getField(p.friend).getFieldname(), "friend");
		assertEquals(map.getField(p.firstName).getFieldname(), "firstName");
		assertEquals(map.getField(p.lastName).getFieldname(), "lastName");
		
		FunkyWinkerBean f = new FunkyWinkerBean(); 
		f.setI(1);
		f.setJ(1);
		f.setX(3);
		
		map = new FieldValueMap<Object>(f, null);
		assertEquals(map.getField(f.i).getFieldname(), "i");
		assertEquals(map.getField(f.j).getFieldname(), "j");
		assertEquals(map.getField(f.x).getFieldname(), "x");
		assertEquals(map.getField(f.good).getFieldname(), "good");
		assertEquals(map.getField(f.bad).getFieldname(), "good"); // bad was overwritten by good		
	}
}

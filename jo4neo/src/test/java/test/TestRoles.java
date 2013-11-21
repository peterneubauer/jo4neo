package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import jo4neo.ObjectGraph;

import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;


public class TestRoles extends BaseTest {

	@Test
	public void inverse() {
		ObjectGraph p = graph;
		Transaction t = p.beginTx();
		long c_id = 0;
		long d_id = 0;
		try {
			
			Collection<Role> existingRoles = p.get(Role.class);
			for (Role existing: existingRoles) {
				p.delete(existing);
			}
			
			Role a = new Role("a");
			Role b = new Role("b", a);
			Role c = new Role("c", b);
			Role d = new Role("d", c);
			p.persist(d);
			t.success();
			c_id = c.id.id();
			d_id = d.id.id();
		} finally {
			t.finish();
		}
		
		Node n = neo.getNodeById(d_id);
		for (Relationship rel : n.getRelationships()) {
			if (n.getProperty("name").toString().equals("parent")) {
				assertEquals(
						c_id, 
						rel.getStartNode().getId()
				);
			}			
		}
		Role role = new Role();
		role = p.find(role).where(role.name).is("d").result();
		int counter = 0;
		for(Role r : p.get(Role.class)) {
			counter++;
			assertTrue(
				r.name.equals("a")
				|| r.name.equals("b")
				|| r.name.equals("c")
				|| r.name.equals("d")
			);
		}
		assertEquals(4, counter);
		
	}
	@Test
	public void roleEqual() {
		Role r = new Role();
		Role s = new Role();
		assertFalse(r.equals(null));
		assertFalse(r.equals("hello"));
		assertTrue(r.equals(s));
		
		r.name = "foo";
		s.name = "bar";
		assertFalse(r.equals(s));
		s.name = "foo";
		assertTrue(r.equals(s));
	}

	@Test
	public void basic() {
		ObjectGraph p = graph;
		Transaction t = p.beginTx();
		try {
			
			for(Role r : p.get(Role.class)) {
				p.delete(r);
			}
			Role roleuser = new Role("roleuser");
			Role roleadmin = new Role("roleadmin", roleuser);
			Role roledeveloper = new Role("roledeveloper", roleuser);
			Role rolesuperuser = new Role("rolesuperuser", roleadmin);
			
			User u = new User();
			u.id = "user1";
			u.directRoles.add(roleuser);
			u.directRoles.add(roleuser);
			u.directRoles.add(roleuser);
			
			User a = new User();
			a.id = "user2";
			a.directRoles.add(rolesuperuser);
	
			p.persist(roleuser, roleadmin, roledeveloper, rolesuperuser, u, a);			
			t.success();
		} finally {
			t.finish();
		}
		
		t = p.beginTx();
		try {
			User user = new User();
			user = p.find(user).where(user.id).is("user1").result();
			assertEquals(1, user.directRoles.size());
			Role r = new Role();
			
			r = p.find(r).where(r.name).is("rolesuperuser").result();
			assertEquals(1, r.users.size());
			while (r != null) {
				if (r.name.equals("a")) {
					assertTrue(r.parent == null);
				} else if (r.name.equals("b")) {
					assertTrue(r.parent.name == "a");
				} else if (r.name.equals("c")) {
					assertTrue(r.parent.name == "b");
				} else if (r.name.equals("d")) {
					assertTrue(r.parent.name == "c");
				}
				r = r.parent;
			}
			
			

			assertFalse(user.hasRole(r));
			
			r = user.directRoles.iterator().next();
			assertEquals("roleuser", r.name);
			
			
			r = p.find(r).where(r.name).is("roleuser").result();
			assertEquals(1, r.users.size());

			
			user = p.find(user).where(user.id).is("user2").result();
			assertTrue(user.hasRole(r));
			
			r = p.find(r).where(r.name).is("roleadmin").result();
			assertTrue(user.hasRole(r));
			
			r = p.find(r).where(r.name).is("rolesuperuser").result();
			assertTrue(user.hasRole(r));
			assertEquals(4, p.getMostRecent(Role.class, 4).size());
			assertEquals(3, p.getMostRecent(Role.class, 3).size());
			assertEquals(2, p.getMostRecent(Role.class, 2).size());
			assertEquals(1, p.getMostRecent(Role.class, 1).size());
			assertEquals(4, p.getMostRecent(Role.class, 20).size());			

			t.success();
		} finally {
			t.finish();
		}		
		
	}
}

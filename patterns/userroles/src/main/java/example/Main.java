package example;

import jo4neo.ObjectGraph;
import jo4neo.ObjectGraphFactory;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Main {

	/**
	 * 
	 * @param graph
	 */
	static void setup(ObjectGraph graph) {
		for (Role r : graph.get(Role.class))
			graph.delete(r);
		
		for (User u : graph.get(User.class))
			graph.delete(u);
		
		Role basicuser = new Role("basicuser");
		Role agent = new Role("agent", basicuser);
		Role hotelspecialist = new Role("hotelspecialist", agent);
		Role premierhotelagent = new Role("premierhotelagent", hotelspecialist);
		Role admin = new Role("admin", basicuser);
		graph.persist(admin, premierhotelagent);
		
		User mark = new User();
		mark.firstName = "Mark";
		mark.lastName = "Lavery";
		mark.id = "markl";
		mark.directRoles.add(premierhotelagent);
		
		User newuser = new User();
		newuser.firstName = "anon";
		newuser.lastName = "anon";
		newuser.id = "anon";
		newuser.directRoles.add(basicuser);
		
		for(int i=0; i<10; i++) {
			User adminuser = new User();
			adminuser.firstName = "admin";
			adminuser.lastName = "admin";
			adminuser.id = "admin" + i;
			adminuser.directRoles.add(admin);
			graph.persist(adminuser);
		}
		
		graph.persist(mark, newuser);
	}

	public static void main(String[] args) {

		GraphDatabaseService neo = new EmbeddedGraphDatabase("neo_store2");
		neo.enableRemoteShell();
		ObjectGraph graph = ObjectGraphFactory.instance().get(neo);
		try {
			setup(graph);
			Role role = new Role();
			role = graph.find(role).where(role.name).is("premierhotelagent")
					.result();
			
			Role inspect = role;
			while (inspect != null) {
				System.out.println("." + inspect.name);
				inspect = inspect.parent;
			}
			
			User u = new User();
			u = graph.find(u).where(u.id).is("markl").result();
			for (Role r : graph.get(Role.class)) {
				System.out.println(u.id + "is member of " + r.name + ": " + u.hasRole(r));
			}
			
			u = graph.find(u).where(u.id).is("anon").result();
			for (Role r : graph.get(Role.class)) {
				System.out.println(u.id + " is member of " + r.name + ": " + u.hasRole(r));
			}
			
			role = graph.find(role).where(role.name).is("basicuser").result();
			for (User member :role.getMembers()) {
				System.out.println(".." + member.id);
			}
			
			for(User user : role.members) {
				System.out.println(user.id);
			}
			
			role = graph.find(role).where(role.name).is("admin").result();
			for (User member :role.getMembers()) {
				System.out.println(role.members.contains(member));
			}

		} finally {
			graph.close();
			neo.shutdown();
		}
	}
}

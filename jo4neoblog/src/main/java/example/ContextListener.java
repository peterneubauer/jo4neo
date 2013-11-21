package example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jo4neo.ObjectGraph;
import jo4neo.ObjectGraphFactory;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;



public class ContextListener implements ServletContextListener {

	public static ObjectGraph pm;
	private static GraphDatabaseService neo;
	
	public void contextDestroyed(ServletContextEvent ev) {
		pm.close();
		neo.shutdown();	
	}

	public void contextInitialized(ServletContextEvent ev) {
		neo = new EmbeddedGraphDatabase("neo_base"); 
		pm = ObjectGraphFactory.instance().get(neo);		
		new InitialPosts(pm);
	}


}

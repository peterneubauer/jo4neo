package jo4neo.spi;

import jo4neo.ObjectGraph;

import org.neo4j.graphdb.GraphDatabaseService;

public interface ObjectGraphProvider  {	
	public ObjectGraph create(GraphDatabaseService neo);
}

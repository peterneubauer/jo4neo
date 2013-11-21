package jo4neo.impl;

import jo4neo.ObjectGraph;

import org.neo4j.graphdb.GraphDatabaseService;

public class ObjectGraphProvider implements jo4neo.spi.ObjectGraphProvider {

	public ObjectGraph create(GraphDatabaseService neo) {
		return new ObjectGraphImpl(neo);
	}

}

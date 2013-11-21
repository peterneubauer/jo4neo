package test;

import org.neo4j.graphdb.RelationshipType;

public class PropertyBase implements RelationshipType {

	public String name() {
		return "base";
	}

}

package test;

import jo4neo.util.RelationFactory;

import org.neo4j.graphdb.RelationshipType;


public class PeopleRelFactory implements RelationFactory {

	public RelationshipType relationshipType(String s) {
		return PeopleRelationships.valueOf(s);
	}

}

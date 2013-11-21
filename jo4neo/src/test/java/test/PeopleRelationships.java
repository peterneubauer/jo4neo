package test;

import org.neo4j.graphdb.RelationshipType;

public enum PeopleRelationships implements RelationshipType {
	FRIEND,
	ENEMY,
	BROTHER,
	SISTER,
	FATHER, 
	MOTHER
}

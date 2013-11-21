package example;

import org.neo4j.graphdb.RelationshipType;

public enum UserRoleRelationships implements RelationshipType {
	role,
	parent
}

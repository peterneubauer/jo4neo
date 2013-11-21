package jo4neo.impl;

import java.lang.reflect.Field;

import jo4neo.TraverserProvider;
import jo4neo.util.RelationFactory;

import org.neo4j.graphdb.RelationshipType;


interface AnnotationHelper {
	public TraverserProvider getTraverserProvider(Field field);
	boolean isInverse(Field field);
	boolean isIndexed(Field field);
	boolean isTraverser(Field field);
	boolean isEmbedded(Field field);
	RelationshipType toRelationship(RelationFactory f, Field field);
	FieldContext[] getFields(Field[] fields, Object o);
	public boolean isFullText(Field field);
}

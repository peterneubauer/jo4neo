package jo4neo.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;

import jo4neo.DefaultTraverserProvider;
import jo4neo.TraverserProvider;
import jo4neo.embed;
import jo4neo.neo;
import jo4neo.util.RelationFactory;

import org.neo4j.graphdb.RelationshipType;



class Jo4neoAnnotations implements AnnotationHelper {

	public TraverserProvider getTraverserProvider(Field field) {
		Class<? extends TraverserProvider> c = field.getAnnotation(neo.class).traverser();
		try {
			return c.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Type lacks default constructor:" +  c.getName(), e);
		}
	}

	public boolean isIndexed(Field field) {
		return (field.isAnnotationPresent(neo.class) && field.getAnnotation(
				neo.class).index());
	}

	public boolean isFullText(Field field) {
		return (field.isAnnotationPresent(neo.class) && field.getAnnotation(
				neo.class).fulltext());
	}

	public boolean isInverse(Field field) {
		if (field.isAnnotationPresent(neo.class)) {
			neo n = field.getAnnotation(neo.class);
			return !n.inverse().equals(neo.DEFAULT);
		}
		return false;		
	}

	public RelationshipType toRelationship(RelationFactory f, Field field) {
		String n = field.getName();
		if (field.isAnnotationPresent(neo.class)) {
			neo annot = field.getAnnotation(neo.class);
			if (!neo.DEFAULT.equals(annot.value()))
				n = annot.value();
			else if (!neo.DEFAULT.equals(annot.inverse()))
				n = annot.inverse();
		}
		return f.relationshipType(n);
	}
	
	public boolean isTraverser(Field field) {
		if (field.isAnnotationPresent(neo.class)) {
			neo n = field.getAnnotation(neo.class);
			return !n.traverser().equals(DefaultTraverserProvider.class);
		}
		return false;		
	}
	
	public boolean isEmbedded(Field field) {
		return field.isAnnotationPresent(embed.class);
	}
	
	public FieldContext[] getFields(Field[] fields, Object o) {
		ArrayList<FieldContext> values = new ArrayList<FieldContext>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(neo.class))
				values.add(new FieldContext(o, field, this));
			else if (field.isAnnotationPresent(embed.class))
				values.add(new EmbeddedContext(o, field, this));
		}
		return values.toArray(new FieldContext[0]);
	}
}

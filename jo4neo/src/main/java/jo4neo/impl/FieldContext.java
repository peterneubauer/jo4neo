package jo4neo.impl;

import static jo4neo.util.PrimitiveWrapper.isPrimitive;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import jo4neo.TraverserProvider;
import jo4neo.util.NullType;
import jo4neo.util.PrimitiveWrapper;
import jo4neo.util.RelationFactory;
import jo4neo.util.Utils;



import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

public class FieldContext {

	private static final String INDEX = "_INDEX";
	protected Field field;
	AnnotationHelper helper;
	public Object subject;
	

	public FieldContext(Object o, Field field, AnnotationHelper helper) {
		this.field = field;
		subject = o;
		this.helper = helper;
	}

	public boolean isInverse() {
		return helper.isInverse(field);
	}

	public boolean isSimpleType() {
		return isPrimitive(field.getType()) || isEmbedded() || arrayPrimitive();
	}

	public boolean isIndexed() {
		return helper.isIndexed(field);
	}
	
	public boolean isFullText() {
		return helper.isFullText(field);
	}

	private boolean arrayPrimitive() {
		return field.getType().isArray()
				&& PrimitiveWrapper.isPrimitive(field.getType()
						.getComponentType());
	}
	
	public Iterable<Relationship> relationships(Node n, RelationFactory f, Direction direction) {
		return n.getRelationships(toRelationship(f), direction);
	}


	public Object value() {
		Object result = null;
		try {
			field.setAccessible(true);
			result = field.get(subject);
		} catch (Exception e) {
			Utils.runtime(e);
		}
		if (result == null)
			return result;
		if (result instanceof Date)
			return ((Date) result).getTime();
		else if (isPlural())
			return ((Collection<?>) result).toArray();
		else
			return result;
	}

	Object rawValue() {
		try {
			field.setAccessible(true);
			return field.get(subject);
		} catch (Exception e) {
			Utils.runtime(e);
		}
		return null;
	}

	public Object initWithNewObject() {
		try {
			field.setAccessible(true);
			Object o = Utils.newObject(field.getType());
			field.set(subject, o);
			return o;
		} catch (Exception e) {
			Utils.runtime(e);
		}
		return null;
	}

	public void setProperty(Object v) {
		try {
			field.setAccessible(true);
			if (field.getType() == Date.class)
				v = new Date((Long) v);
			else if (isPluralPrimitive())
				v = Arrays.asList((Object[]) v);
			field.set(subject, v);
		} catch (Exception e) {
			Utils.runtime(e);
		}
	}

	public void applyTo(Node n) {
		if (value() != null)
			n.setProperty(name(), value());
	}

	@SuppressWarnings("unchecked")
	public Collection<Object> values() {
		try {
			field.setAccessible(true);
			return (Collection<Object>) field.get(subject);
		} catch (Exception e) {
			Utils.runtime(e);
		}
		return null;
	}

	public String name() {
		return field.getName();
	}

	public RelationshipType toRelationship(RelationFactory f) {
		return helper.toRelationship(f, field);
	}

	public boolean isSingular() {
		return !field.getType().isAssignableFrom(Collection.class);
	}

	public boolean isEmbedded() {
		return helper.isEmbedded(field);
	}

	public Class<?> type() {
		return field.getType();
	}

	public boolean isPlural() {
		return field.getType().isAssignableFrom(Collection.class);
	}

	public boolean isPluralPrimitive() {
		return isPlural() && isPrimitive(type2());
	}
	
	public boolean isPluralComplex() {
		return isPlural() && !isPrimitive(type2());
	}

	public Class<?> type2() {
		return getGenericType((ParameterizedType) field.getGenericType());
	}

	private Class<?> getGenericType(ParameterizedType type) {
		return (type == null) ? NullType.class : (Class<?>) type
				.getActualTypeArguments()[0];
	}

	public void applyFrom(Node n) {
		if (n.hasProperty(name()))
			setProperty(n.getProperty(name()));
	}

	public String getIndexName() {
		return subject.getClass().getName() + '.' + field.getName() + INDEX;
	}

	public String getFieldname() {
		return field.getName();
	}
	
	public TraverserProvider getTraverserProvider() {
		return helper.getTraverserProvider(field);
	}

	public boolean isTraverser() {
		return helper.isTraverser(field);	
	}

}

/**
 * jo4neo is a java object binding library for neo4j
 * Copyright (C) 2009  Taylor Cowan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
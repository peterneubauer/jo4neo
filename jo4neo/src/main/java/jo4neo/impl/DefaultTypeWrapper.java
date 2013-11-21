package jo4neo.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;



import jo4neo.Nodeid;
import jo4neo.PersistenceException;
import jo4neo.util.Utils;

import static jo4neo.util.Resources.*;


class DefaultTypeWrapper extends TypeWrapper {
	Field[] fields;
	Field idfield;
	Class<?> me;
	AnnotationHelper helper;

	public DefaultTypeWrapper(Class<?> c, AnnotationHelper helper) {
		me = c;
		this.helper = helper;
		fields = getDeclaredFields(c);
		for (Field f : fields)
			if (f.getType().equals(Nodeid.class))
				idfield = f;
		if (idfield==null && !c.isAssignableFrom(Collection.class))
			throw new PersistenceException(msg(MISSING_ID, c.getName()));
	}

	public InjectedNodeid id(Object o) {
		InjectedNodeid n = null;
		try {
			if (!idfield.isAccessible())
				idfield.setAccessible(true);
			n = (InjectedNodeid)idfield.get(o);
			if (n== null)
				n = new DefaultNodeid(o.getClass());
			idfield.set(o, n);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error retrieving id field value.", e);
		}
		return n;

	}

	
	
	public FieldContext[] getFields(Object o) {
		return helper.getFields(fields, o);
	}
	
	

	@Override
	public Object newInstance(Object o) {
		try {
			return me.newInstance();
		} catch (Exception e) {
			Utils.runtime("Your Java Object must provide jo4neo with a default constructor.", e);
		}
		return null;
	}

	@Override
	public void setId(Object bean, Nodeid n) {		
		try {
			if (!idfield.isAccessible())
				idfield.setAccessible(true);			
			idfield.set(bean, n);
		} catch (Exception e) {
			Utils.runtime(e);
		}
	}

	@Override
	public Class<?> getWrappedType() {
		return this.me;
	}
	
	public String name() {
		return this.me.getName();
	}
	
	public Field[] getDeclaredFields(Class<?> c) {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (Field field : c.getDeclaredFields())
			fields.add(field);
		Class<?> cls = c;
		while (cls.getSuperclass() != Object.class && cls.getSuperclass() != null) {
			cls = cls.getSuperclass();
			for (Field field : cls.getDeclaredFields())
				fields.add(field);
		}
		return fields.toArray(new Field[0]);
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
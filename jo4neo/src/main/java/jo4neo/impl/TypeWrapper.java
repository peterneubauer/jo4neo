package jo4neo.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.logging.Logger;

import jo4neo.Nodeid;




public abstract class TypeWrapper {
	public static Logger logger = Logger.getLogger("com.thewebsemantic");
	
	protected static BeanInfo beanInfo(Class<?> c) {
		try {
			return Introspector.getBeanInfo(c);
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public boolean assignableTo(Class<?> c) {
		return c.isAssignableFrom(getWrappedType());
	}

	public abstract Object newInstance(Object o);	
	public abstract FieldContext[] getFields(Object o);
	public abstract InjectedNodeid id(Object o);

	public abstract void setId(Object bean, Nodeid n);
	public abstract Class<?> getWrappedType();
	public abstract String name();

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
package jo4neo.impl;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashMap;

import jo4neo.util.Utils;

public class TypeWrapperFactory {

	public static HashMap<Class<?>, TypeWrapper> cache = new HashMap<Class<?>, TypeWrapper>();
	static Jo4neoAnnotations helper = new Jo4neoAnnotations();
	
	static {
		cache.put(URI.class, new URITypeWrapper());
	}

	private static TypeWrapper newwrapper(Class<?> c) {
		TypeWrapper t = new DefaultTypeWrapper(c, helper);
		cache.put(c, t);
		return t;
	}

	public static synchronized TypeWrapper wrap(Class<?> c) {
		return (cache.containsKey(c)) ? cache.get(c) : TypeWrapperFactory
				.newwrapper(c);
	}

	public static TypeWrapper $(Object o) {
		return wrap(o.getClass());
	}

	public static TypeWrapper wrap(Field o) {
		return wrap(o.getType());
	}

	public static TypeWrapper wrap(String s) {
		try {
			return wrap(Class.forName(s));
		} catch (ClassNotFoundException e) {
			Utils.runtime(e);
		}
		return null;
	}


}

/**
 * jo4neo is a java object binding library for neo4j Copyright (C) 2009 Taylor
 * Cowan
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

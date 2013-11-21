package jo4neo.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class PrimitiveWrapper {
	private static final Set<Class<?>> WRAPPERS = new HashSet<Class<?>>();

	static {
		WRAPPERS.add(Byte.class);
		WRAPPERS.add(Short.class);
		WRAPPERS.add(Character.class);
		WRAPPERS.add(Integer.class);
		WRAPPERS.add(Long.class);
		WRAPPERS.add(Float.class);
		WRAPPERS.add(Double.class);
		WRAPPERS.add(Boolean.class);
		WRAPPERS.add(String.class);
		WRAPPERS.add(Date.class);

	}

	public static boolean isPrimitive(Class<?> c) {
		return c.isPrimitive() || WRAPPERS.contains(c) || WRAPPERS.contains(c.getSuperclass());
	}

	public static boolean isPrimitive(Object o) {
		return isPrimitive(o.getClass());
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
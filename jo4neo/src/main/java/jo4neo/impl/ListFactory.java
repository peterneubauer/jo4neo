package jo4neo.impl;

import jo4neo.util.Lazy;

class ListFactory {
	public static Lazy get(FieldContext field, LoadOperation<?> load) {
		if (field.isInverse())
			return new InverseList(field, load);
		else if (field.isTraverser())
			return new TraverserList(field, load);
		else
			return new LazyList(field, load);
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
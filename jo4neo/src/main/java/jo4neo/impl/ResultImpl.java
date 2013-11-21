package jo4neo.impl;

import java.util.Collection;

import jo4neo.ObjectGraph;
import jo4neo.fluent.Result;




class ResultImpl<T> implements Result<T> {

	Class<T> c;
	String indexName;
	Object o;
	ObjectGraph pm;
	
	public ResultImpl(ObjectGraph pm, Class<T> c, String indexName, Object o) {
		this.o = o;
		this.indexName = indexName;
		this.c = c;
		this.pm = pm;
	}

	public T result() {
		return pm.getSingle(c,indexName,o);
	}

	public Collection<T> results() {
		return pm.get(c,indexName,o);
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
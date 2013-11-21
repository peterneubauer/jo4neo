package jo4neo.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;



import jo4neo.util.Utils;


class EmbeddedContext extends FieldContext {

	public EmbeddedContext(Object o, Field field, AnnotationHelper helper) {
		super(o, field, helper);
	}
	
	public Object value() {
		Object result = null;
		try {
			field.setAccessible(true);
			result = field.get(subject);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(result);
			result = bos.toByteArray();			
		} catch (Exception e) {
			Utils.runtime(e);
		}
		return result;
	}

	public void setProperty(Object v) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream((byte[])v);
			ObjectInputStream os = new ObjectInputStream(bis);
			Object o = os.readObject();
			field.setAccessible(true);
			field.set(subject, o);
		} catch (Exception e) {
			Utils.runtime(e);
		}
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
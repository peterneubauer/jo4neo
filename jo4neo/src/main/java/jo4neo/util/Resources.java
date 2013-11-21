package jo4neo.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Resources {
	
	ResourceBundle bundle;
	private static Resources myself = new Resources();
	public static String MISSING_ID = "MISSING_ID";
	public static String MISSING_TIMELINE_ANNOTATION = "MISSING_TIMELINE_ANNOTATION";
	
	private Resources() {
		bundle = ResourceBundle.getBundle("jo4neo.message");
	}
	
	public String message(String key) {
		return bundle.getString(key);
	}
	
	public static Resources getInstance() {
		return myself;
	}
	
	public static String msg(String key) {
		return myself.message(key);
	}
	
	public static String msg(String key, Object o) {
		return MessageFormat.format(msg(key), o);
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
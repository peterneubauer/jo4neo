package jo4neo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a persistent field.  Fields annotated with embed
 * will be serialized to bytes using the Java's
 * standard serialization mechanism.  Fields annotated
 * with embed should be marked with the Serializable interface.
 * 
 * In the neo4j graph the bytes are stored as a node property.
 * 
 * @author Taylor Cowan
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) 
public @interface embed {

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
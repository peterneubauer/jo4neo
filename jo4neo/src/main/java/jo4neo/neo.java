package jo4neo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 
 * Defines a persistent field, including primitive types or arrays of primitives,
 * complex types, and collections of complex types.  (Arrays of complex types are not 
 * supported).
 * 
 * @author Taylor Cowan
 *
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
public @interface neo {
	public static final String DEFAULT = "default";	
	
	/**
	 * If the value is specified, the node property or relationship
	 * will be named accordingly.  Defaults to the qualified field name.
	 * 
	 * @return
	 */
	String value() default DEFAULT;
	
	/**
	 * If true the field will be indexed by jo4neo.
	 * It is the client's responsibility to ensure uniqueness, as jo4neo
	 * allows multiple entities to be indexed against a single value.  Defaults
	 * to false.
	 * @return
	 */
	boolean index() default false;
	
	/**
	 * Name of a relationship for which this field acts as inverse.
	 * All incoming edges with the same name will be treated as if they
	 * were outgoing.
	 * 
	 * @return
	 */
	String inverse() default DEFAULT;
	
	/**
	 * Declares a {@link TraverserProvider} from which the field (of type Collection)
	 * will be generated.
	 * 
	 * @return
	 */
	Class<? extends TraverserProvider> traverser() default jo4neo.DefaultTraverserProvider.class;
	
	/**
	 * If true jo4neo manages a list of instances in most recent order.
	 * 
	 * @see ObjectGraph#getMostRecent(Class, int)
	 */
	boolean recency() default false;

	boolean fulltext() default false;
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
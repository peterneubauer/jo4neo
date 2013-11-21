package jo4neo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When applied to a java class declaration, this annotation tells
 * jo4neo to let the class participate in Timeline indexing.
 * 
 * {@link ObjectGraph#getAddedBetween(Class, java.util.Date, java.util.Date)} and 
 * {@link ObjectGraph#getAddedSince(Class, java.util.Date)} are only available for types
 * annotated with {@literal}@Timeline.
 * 
 * @author Taylor Cowan
 * @see ObjectGraph#getAddedBetween(Class, java.util.Date, java.util.Date)
 * @see ObjectGraph#getAddedSince(Class, java.util.Date)
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
public @interface Timeline {

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
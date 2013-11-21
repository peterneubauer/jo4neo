package jo4neo.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class Utils {

	private static volatile long counter;

	@SuppressWarnings("unchecked")
	public static <T> T newObject(Class<T> c) {
		if (Number.class.isAssignableFrom(c)) {
			try {
				return (T) c.getConstructor(String.class).newInstance(counter++);
			} catch (Exception e) {
				runtime(e);
			}
		} else if (c == String.class) {
			return (T) ("" + counter++);
		} else if (c == Boolean.class) {
			return (T) new Boolean(false);
		} else if (c == BigDecimal.class) {
			return (T) new BigDecimal(counter++);
		} else if (c == BigInteger.class) {
			return (T) new BigInteger("" + counter++);
		} else if (c == java.sql.Date.class) {
			return (T) new java.sql.Date(counter++);
		} else if (c == java.sql.Time.class) {
			return (T) new java.sql.Time(counter++);
		} else if (c == java.sql.Timestamp.class) {
			return (T) new java.sql.Timestamp(counter++);
		} else if (c == java.util.Date.class) {
			return (T) new java.util.Date(counter++);
		} else if (c == Collection.class) {
			return (T) new ArrayList<T>();
		} else if (c == Integer.TYPE) {
			return (T) new Integer((int) counter++);
		} else if (c == Long.TYPE) {
			return (T) new Long(counter++);
		} else if (c == Short.TYPE) {
			return (T) new Short((short) counter++);
		} else if (c == Float.TYPE) {
			return (T) new Float(counter++);
		} else if (c == Double.TYPE) {
			return (T) new Double(counter++);
		} else if (c == Boolean.TYPE) {
			return (T) new Boolean(false);
		} else if (c == URI.class) {
			return (T) URI.create("http://foo/" + counter++);
		} else if (c.isArray())
			return (T) Array.newInstance(c.getComponentType(), 0);
		try {
			return c.newInstance();
		} catch (Exception e) {
			System.out.println(c.isArray());
			throw new RuntimeException("Type lacks default constructor:"
					+ c.getName(), e);
		}
	}

	public static void runtime(Exception e) {
		throw new RuntimeException(e);
	}

	public static void runtime(String msg, Exception e) {
		throw new RuntimeException(msg, e);
	}

}
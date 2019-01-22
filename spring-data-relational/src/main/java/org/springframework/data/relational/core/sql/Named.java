package org.springframework.data.relational.core.sql;

/**
 * Named element exposing a {@link #getName() name}.
 *
 * @author Mark Paluch
 */
public interface Named {

	/**
	 * @return the name of the underlying element.
	 */
	String getName();
}

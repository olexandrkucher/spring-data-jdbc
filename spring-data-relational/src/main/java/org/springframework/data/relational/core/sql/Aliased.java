package org.springframework.data.relational.core.sql;

/**
 * Aliased element exposing an {@link #getAlias() alias}.
 *
 * @author Mark Paluch
 */
public interface Aliased {

	/**
	 * @return the alias name.
	 */
	String getAlias();
}

package org.springframework.data.relational.core.sql;

import org.springframework.util.Assert;

/**
 * Interface for implementations that wish to be visited by a {@link Visitor}.
 *
 * @author Mark Paluch
 * @see Visitor
 */
public interface Visitable {

	/**
	 * Accept a {@link Visitor} visiting this {@link Segment} and its nested {@link Segment}s if applicable.
	 *
	 * @param visitor the visitor to notify, must not be {@literal null}.
	 */
	default void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		visitor.leave(this);
	}
}

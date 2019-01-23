package org.springframework.data.relational.core.sql;

import org.springframework.util.Assert;

/**
 * Grouped {@link Condition} wrapping one or more {@link Condition}s into a parentheses group.
 *
 * @author Mark Paluch
 * @see Condition#group()
 */
public class ConditionGroup implements Condition {

	private final Condition nested;

	ConditionGroup(Condition nested) {
		this.nested = nested;
	}

	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		nested.visit(visitor);
		visitor.leave(this);
	}

	/**
	 * @return the nested (grouped) {@link Condition}.
	 */
	public Condition getNested() {
		return nested;
	}

	@Override
	public String toString() {
		return "(" + nested.toString() + ")";
	}
}


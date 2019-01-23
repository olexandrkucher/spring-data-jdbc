package org.springframework.data.relational.core.sql;

/**
 * AST {@link Segment} for a condition.
 *
 * @author Mark Paluch
 * @see Conditions
 */
public interface Condition extends Segment {

	/**
	 * Combine another {@link Condition} using {@code AND}.
	 *
	 * @param other the other {@link Condition}.
	 * @return the combined {@link Condition}.
	 */
	default Condition and(Condition other) {
		return new AndCondition(this, other);
	}

	/**
	 * Combine another {@link Condition} using {@code OR}.
	 *
	 * @param other the other {@link Condition}.
	 * @return the combined {@link Condition}.
	 */
	default Condition or(Condition other) {
		return new OrCondition(this, other);
	}

	/**
	 * Encapsulate this {@link Condition} in a group of parentheses.
	 *
	 * @return the grouped {@link Condition}.
	 */
	default Condition group() {
		return new ConditionGroup(this);
	}
}

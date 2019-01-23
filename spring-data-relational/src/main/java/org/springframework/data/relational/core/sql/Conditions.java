package org.springframework.data.relational.core.sql;

/**
 * Factory for common {@link Condition}s.
 *
 * @author Mark Paluch
 * @see SQL
 * @see Expressions
 * @see Functions
 */
public abstract class Conditions {

	/**
	 * @return a new {@link Equals} condition.
	 */
	public static Equals equals(Expression left, Expression right) {
		return Equals.create(left, right);
	}

	/**
	 * Creates a plain {@code sql} {@link Condition}.
	 *
	 * @param sql the SQL, must not be {@literal null} or empty.
	 * @return a SQL {@link Expression}.
	 */
	public static Condition just(String sql) {
		return new ConstantCondition(sql);
	}

	// Utility constructor.
	private Conditions() {
	}

	static class ConstantCondition extends AbstractSegment implements Condition {

		private final String condition;

		ConstantCondition(String condition) {
			this.condition = condition;
		}

		@Override
		public String toString() {
			return condition;
		}
	}
}





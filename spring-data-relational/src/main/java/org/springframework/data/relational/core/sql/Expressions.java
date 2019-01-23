package org.springframework.data.relational.core.sql;

/**
 * Factory for common {@link Expression}s.
 *
 * @author Mark Paluch
 * @see SQL
 * @see Conditions
 * @see Functions
 */
public abstract class Expressions {

	private static Expression ASTERISK = new ConstantExpression("*");

	/**
	 * @return a new asterisk {@code *} expression.
	 */
	public static Expression asterisk() {
		return ASTERISK;
	}

	/**
	 * Creates a plain {@code sql} {@link Expression}.
	 *
	 * @param sql the SQL, must not be {@literal null} or empty.
	 * @return a SQL {@link Expression}.
	 */
	public static Expression just(String sql) {
		return new ConstantExpression(sql);
	}

	/**
	 * @return a new {@link Table}.scoped asterisk {@code <table>.*} expression.
	 */
	public static Expression asterisk(Table table) {
		return table.asterisk();
	}

	// Utility constructor.
	private Expressions() {
	}

	static class ConstantExpression extends AbstractSegment implements Expression {

		private final String expression;

		ConstantExpression(String expression) {
			this.expression = expression;
		}

		@Override
		public String toString() {
			return expression;
		}
	}
}





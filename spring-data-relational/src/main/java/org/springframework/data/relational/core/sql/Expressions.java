package org.springframework.data.relational.core.sql;

/**
 * Constant {@link Expression}s.
 *
 * @author Mark Paluch
 */
public class Expressions {

	public static Expression ASTERISK = new ConstantExpression("*");

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





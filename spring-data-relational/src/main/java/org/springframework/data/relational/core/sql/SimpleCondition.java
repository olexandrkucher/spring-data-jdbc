package org.springframework.data.relational.core.sql;

/**
 * Simple condition consisting of {@link Expression}, {@code comparator} and {@code predicate}.
 *
 * @author Mark Paluch
 */
public class SimpleCondition extends AbstractSegment implements Condition {

	private final Expression expression;

	private final String comparator;

	private final String predicate;


	SimpleCondition(Expression expression, String comparator, String predicate) {
		this.expression = expression;
		this.comparator = comparator;
		this.predicate = predicate;
	}

	public static SimpleCondition create(String column, String comparator, String predicate) {
		return new SimpleCondition(new SelectColumn(column, null), comparator, predicate);
	}

	@Override
	public void visit(Visitor visitor) {
		visitor.enter(this);
		expression.visit(visitor);
		visitor.leave(this);
	}

	@Override
	public String toString() {
		return expression.toString() + " " + comparator + " " + predicate;
	}
}

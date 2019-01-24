package org.springframework.data.relational.core.sql;

import org.springframework.util.Assert;

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

	/**
	 * Creates a simple {@link Condition} given {@code column}, {@code comparator} and {@code predicate}.
	 *
	 * @param column
	 * @param comparator
	 * @param predicate
	 * @return
	 */
	public static SimpleCondition create(String column, String comparator, String predicate) {
		return new SimpleCondition(new Column(column, null), comparator, predicate);
	}

	public String getComparator() {
		return comparator;
	}

	public String getPredicate() {
		return predicate;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.relational.core.sql.Visitable#visit(org.springframework.data.relational.core.sql.Visitor)
	 */
	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		expression.visit(visitor);
		visitor.leave(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return expression.toString() + " " + comparator + " " + predicate;
	}
}

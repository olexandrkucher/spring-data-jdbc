package org.springframework.data.relational.core.sql;

/**
 * Simple condition consisting of {@link Expression}, {@code comparator} and {@code predicate}.
 *
 * @author Mark Paluch
 */
public class Equals extends AbstractSegment implements Condition {

	private final Expression left;
	private final Expression right;


	public Equals(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public static Equals create(Expression left, Expression right) {
		return new Equals(left, right);
	}

	@Override
	public void visit(Visitor visitor) {
		visitor.enter(this);
		left.visit(visitor);
		right.visit(visitor);
		visitor.leave(this);
	}

	@Override
	public String toString() {
		return left.toString() + " = " + right.toString();
	}
}

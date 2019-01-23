package org.springframework.data.relational.core.sql;

import org.springframework.util.Assert;

/**
 * Equals to {@link Condition} comparing two {@link Expression}s.
 * <p/>
 * Results in a rendered condition: {@code <left> = <right>}.
 *
 * @author Mark Paluch
 */
public class Equals extends AbstractSegment implements Condition {

	private final Expression left;
	private final Expression right;

	Equals(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Creates a new {@link Equals} {@link Condition} given two {@link Expression}s.
	 *
	 * @param left the left {@link Expression}.
	 * @param right the right {@link Expression}.
	 * @return the {@link Equals} condition.
	 */
	public static Equals create(Expression left, Expression right) {

		Assert.notNull(left, "Left expression must not be null!");
		Assert.notNull(right, "Right expression must not be null!");

		return new Equals(left, right);
	}

	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		left.visit(visitor);
		right.visit(visitor);
		visitor.leave(this);
	}

	/**
	 * @return the left {@link Expression}.
	 */
	public Expression getLeft() {
		return left;
	}

	/**
	 * @return the right {@link Expression}.
	 */
	public Expression getRight() {
		return right;
	}

	@Override
	public String toString() {
		return left.toString() + " = " + right.toString();
	}
}

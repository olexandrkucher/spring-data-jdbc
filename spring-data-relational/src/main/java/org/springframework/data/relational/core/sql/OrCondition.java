package org.springframework.data.relational.core.sql;

/**
 * @author Mark Paluch
 */
public class OrCondition implements Condition {

	private final Condition left;
	private final Condition right;

	OrCondition(Condition left, Condition right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void visit(Visitor visitor) {

		visitor.enter(this);
		left.visit(visitor);
		right.visit(visitor);
		visitor.leave(this);
	}

	public Condition getLeft() {
		return left;
	}

	public Condition getRight() {
		return right;
	}

	@Override
	public String toString() {
		return left.toString() + " OR " + right.toString();
	}
}


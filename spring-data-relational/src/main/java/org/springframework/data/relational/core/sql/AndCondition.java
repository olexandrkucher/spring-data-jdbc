package org.springframework.data.relational.core.sql;

/**
 * @author Mark Paluch
 */
public class AndCondition implements Condition {

	private final Condition left;
	private final Condition right;

	AndCondition(Condition left, Condition right) {
		this.left = left;
		this.right = right;
	}


	@Override
	public void visit(Visitor visitor) {

		visitor.leave(this);
		visitor.enter(this);
		left.visit(visitor);
		right.visit(visitor);
	}

	public Condition getLeft() {
		return left;
	}

	public Condition getRight() {
		return right;
	}

	@Override
	public String toString() {
		return left.toString() + " AND " + right.toString();
	}
}


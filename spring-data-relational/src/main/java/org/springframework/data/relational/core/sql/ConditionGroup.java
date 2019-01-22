package org.springframework.data.relational.core.sql;

/**
 * @author Mark Paluch
 */
public class ConditionGroup implements Condition {

	private final Condition nested;

	ConditionGroup(Condition nested) {
		this.nested = nested;
	}

	@Override
	public void visit(Visitor visitor) {

		visitor.enter(this);
		nested.visit(visitor);
		visitor.leave(this);
	}

	public Condition getNested() {
		return nested;
	}

	@Override
	public String toString() {
		return "(" + nested.toString() + ")";
	}
}


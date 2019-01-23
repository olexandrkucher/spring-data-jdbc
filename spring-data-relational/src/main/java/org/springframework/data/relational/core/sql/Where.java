package org.springframework.data.relational.core.sql;

import org.springframework.util.Assert;

/**
 * {@code Where} clause.
 *
 * @author Mark Paluch
 */
public class Where extends AbstractSegment implements Segment {

	private final Condition condition;

	Where(Condition condition) {
		this.condition = condition;
	}

	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);

		condition.visit(visitor);

		visitor.leave(this);
	}

	@Override
	public String toString() {
		return "WHERE " + condition.toString();
	}
}

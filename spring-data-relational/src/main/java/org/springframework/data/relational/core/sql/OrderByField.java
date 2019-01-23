package org.springframework.data.relational.core.sql;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author Mark Paluch
 */
public class OrderByField extends AbstractSegment implements Segment {

	private final Expression expression;
	private final @Nullable Sort.Direction direction;
	private final Sort.NullHandling nullHandling;

	OrderByField(Expression expression, Direction direction, NullHandling nullHandling) {

		Assert.notNull(expression, "Order by expression must not be null");
		Assert.notNull(nullHandling, "NullHandling by expression must not be null");

		this.expression = expression;
		this.direction = direction;
		this.nullHandling = nullHandling;
	}

	public static OrderByField from(Column column) {
		return new OrderByField(column, null, NullHandling.NATIVE);
	}

	public static OrderByField create(String name) {
		return new OrderByField(Column.create(name), null, NullHandling.NATIVE);
	}

	public static OrderByField index(int index) {
		return new OrderByField(new IndexedOrderByField(index), null, NullHandling.NATIVE);
	}

	public OrderByField asc() {
		return new OrderByField(expression, Direction.ASC, NullHandling.NATIVE);
	}

	public OrderByField desc() {
		return new OrderByField(expression, Direction.DESC, NullHandling.NATIVE);
	}

	public OrderByField withNullHandling(NullHandling nullHandling) {
		return new OrderByField(expression, direction, nullHandling);
	}

	@Override
	public void visit(Visitor visitor) {

		visitor.enter(this);
		expression.visit(visitor);
		visitor.leave(this);
	}

	public Expression getExpression() {
		return expression;
	}

	@Nullable
	public Direction getDirection() {
		return direction;
	}

	public NullHandling getNullHandling() {
		return nullHandling;
	}

	@Override
	public String toString() {
		return direction != null ? expression.toString() + " " + direction : expression.toString();
	}

	static class IndexedOrderByField extends AbstractSegment implements Expression {

		private final int index;

		IndexedOrderByField(int index) {
			this.index = index;
		}

		@Override
		public String toString() {
			return Integer.toString(index);
		}
	}
}

package org.springframework.data.relational.core.sql;

/**
 * {@link Segment} to select all columns from a {@link Table} as in {@literal SELECT foo.*}.
 *
 * @author Mark Paluch
 */
public class SelectAllFromTable extends AbstractSegment implements Expression {

	private final Table table;

	SelectAllFromTable(Table table) {
		this.table = table;
	}

	public static SelectAllFromTable create(Table table) {
		return new SelectAllFromTable(table);
	}

	@Override
	public void visit(Visitor visitor) {

		visitor.enter(this);
		table.visit(visitor);
		visitor.leave(this);
	}

	@Override
	public String toString() {
		return table.toString() + ".*";
	}
}

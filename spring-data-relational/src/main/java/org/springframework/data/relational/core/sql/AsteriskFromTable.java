package org.springframework.data.relational.core.sql;

import org.springframework.util.Assert;

/**
 * {@link Segment} to select all columns from a {@link Table}.
 * <p/>
 * * Renders to: {@code <table>.*} as in {@code SELECT <table>.* FROM …}.
 *
 * @author Mark Paluch
 * @see Table#asterisk()
 */
public class AsteriskFromTable extends AbstractSegment implements Expression {

	private final Table table;

	AsteriskFromTable(Table table) {
		this.table = table;
	}

	public static AsteriskFromTable create(Table table) {
		return new AsteriskFromTable(table);
	}

	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		table.visit(visitor);
		visitor.leave(this);
	}

	/**
	 * @return the associated {@link Table}.
	 */
	public Table getTable() {
		return table;
	}

	@Override
	public String toString() {

		if (table instanceof Aliased) {
			return ((Aliased) table).getAlias() + ".*";
		}

		return table.toString() + ".*";
	}
}

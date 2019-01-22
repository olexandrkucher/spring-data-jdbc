package org.springframework.data.relational.core.sql;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Column name within a {@code SELECT … FROM} clause.
 *
 * @author Mark Paluch
 */
public class SelectColumn extends AbstractSegment implements Expression, Named {

	private final String name;
	private final Table table;

	SelectColumn(String name, Table table) {

		Assert.notNull(name, "Name must not be null");

		this.name = name;
		this.table = table;
	}

	public static SelectColumn create(String name) {

		Assert.hasText(name, "Name must not be empty or null");

		return new SelectColumn(name, null);
	}

	public static SelectColumn aliased(String name, String alias) {

		Assert.hasText(name, "Name must not be empty or null");
		Assert.hasText(alias, "Alias must not be empty or null");

		return new AliasedSelectColumn(name, null, alias);
	}

	public static SelectColumn create(String name, Table table) {

		Assert.hasText(name, "Name must not be empty or null");
		Assert.notNull(table, "Table must not be null");

		return new SelectColumn(name, table);
	}

	public static SelectColumn aliased(String name, Table table, String alias) {

		Assert.hasText(name, "Name must not be empty or null");
		Assert.notNull(table, "Table must not be null");
		Assert.hasText(alias, "Alias must not be empty or null");

		return new AliasedSelectColumn(name, table, alias);
	}

	public SelectColumn as(String alias) {

		Assert.hasText(alias, "Alias must not be empty or null");

		return new AliasedSelectColumn(name, table, alias);
	}

	public SelectColumn from(Table table) {

		Assert.notNull(table, "Table must not be null");

		return new SelectColumn(name, table);
	}

	@Override
	public void visit(Visitor visitor) {

		visitor.enter(this);
		table.visit(visitor);
		visitor.leave(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Nullable
	public Table getTable() {
		return table;
	}

	@Override
	public String toString() {
		return name;
	}

	static class AliasedSelectColumn extends SelectColumn implements Aliased {

		private final String alias;

		private AliasedSelectColumn(String name, Table table, String alias) {
			super(name, table);
			this.alias = alias;
		}

		@Override
		public String getAlias() {
			return alias;
		}

		public SelectColumn from(Table table) {

			Assert.notNull(table, "Table must not be null");

			return new AliasedSelectColumn(getName(), table, getAlias());
		}

		@Override
		public String toString() {
			return getName() + " AS " + getAlias();
		}
	}
}

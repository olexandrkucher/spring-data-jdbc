package org.springframework.data.relational.core.sql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Represents a table reference within an SQL statement. Typically used to denote {@code FROM} or {@code JOIN} or to prefix a {@link SelectColumn}.
 *
 * @author Mark Paluch
 */
public class Table extends AbstractSegment implements Segment, Named {

	private final String name;

	Table(String name) {
		this.name = name;
	}

	public static Table create(String name) {

		Assert.hasText(name, "Name must not be null or empty!");

		return new Table(name);
	}

	public static Table aliased(String name, String alias) {

		Assert.hasText(name, "Name must not be null or empty!");
		Assert.hasText(name, "Name must not be null or empty!");

		return new AliasedTable(name, alias);
	}

	public Table as(String alias) {

		Assert.hasText(alias, "Alias must not be null or empty!");

		return new AliasedTable(name, alias);
	}

	public SelectColumn column(String name) {

		Assert.hasText(name, "Name must not be null or empty!");

		return new SelectColumn(name, this);
	}

	public List<SelectColumn> columns(String... names) {

		Assert.notNull(names, "Names must not be null");

		List<SelectColumn> columns = new ArrayList<>();
		for (String name : names) {
			columns.add(column(name));
		}

		return columns;
	}

	public SelectAllFromTable all() {
		return new SelectAllFromTable(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	static class AliasedTable extends Table implements Aliased {

		private final String alias;

		AliasedTable(String name, String alias) {
			super(name);

			Assert.hasText(alias, "Alias must not be null or empty!");

			this.alias = alias;
		}

		@Override
		public String getAlias() {
			return alias;
		}

		@Override
		public String toString() {
			return getName() + " AS " + getAlias();
		}
	}
}

package org.springframework.data.relational.core.sql;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

/**
 * Unit tests for {@link SelectValidator}.
 *
 * @author Mark Paluch
 */
public class SelectValidatorUnitTests {

	@Test
	public void shouldReportMissingTableViaSelectlist() {

		Column column = SQL.table("table").column("foo");

		assertThatThrownBy(() -> {
			SQL.select(column).from(SQL.table("bar")).build();
		}).isInstanceOf(IllegalStateException.class).hasMessageContaining("Required table [table] by a SELECT column not imported by FROM [bar] or JOIN []");
	}

	@Test
	public void shouldReportMissingTableViaSelectlistCount() {

		Column column = SQL.table("table").column("foo");

		assertThatThrownBy(() -> {
			SQL.select(Functions.count(column)).from(SQL.table("bar")).build();
		}).isInstanceOf(IllegalStateException.class).hasMessageContaining("Required table [table] by a SELECT column not imported by FROM [bar] or JOIN []");
	}

	@Test
	public void shouldReportMissingTableViaSelectlistDistinct() {

		Column column = SQL.table("table").column("foo");

		assertThatThrownBy(() -> {
			SQL.select(Functions.distinct(column)).from(SQL.table("bar")).build();
		}).isInstanceOf(IllegalStateException.class).hasMessageContaining("Required table [table] by a SELECT column not imported by FROM [bar] or JOIN []");
	}

	@Test
	public void shouldReportMissingTableViaOrderBy() {

		Column column = SQL.table("table").column("foo");

		assertThatThrownBy(() -> {
			SQL.select(SQL.column("foo")) //
					.from(SQL.table("bar")).orderBy(column) //
					.build();
		}).isInstanceOf(IllegalStateException.class).hasMessageContaining("Required table [table] by a ORDER BY column not imported by FROM [bar] or JOIN []");
	}

	@Test
	public void shouldReportMissingTableViaWhere() {

		Column column = SQL.table("table").column("foo");

		assertThatThrownBy(() -> {
			SQL.select(SQL.column("foo")).from(SQL.table("bar")) //
					.where(new SimpleCondition(column, "=", "foo")) //
					.build();
		}).isInstanceOf(IllegalStateException.class).hasMessageContaining("Required table [table] by a WHERE predicate not imported by FROM [bar] or JOIN []");
	}
}

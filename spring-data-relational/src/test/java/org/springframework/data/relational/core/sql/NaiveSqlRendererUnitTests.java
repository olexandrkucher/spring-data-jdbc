package org.springframework.data.relational.core.sql;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

/**
 * Unit tests for {@link NaiveSqlRenderer}.
 *
 * @author Mark Paluch
 */
public class NaiveSqlRendererUnitTests {

	@Test
	public void shouldRenderSingleColumn() {

		Select select = Select.builder().select("foo").from("bar").build();

		assertThat(NaiveSqlRenderer.render(select)).isEqualTo("SELECT foo FROM bar");
	}

	@Test
	public void shouldRenderAliasedColumnAndFrom() {

		Table table = Table.create("bar").as("my_bar");

		Select select = Select.builder().select(table.column("foo").as("my_foo")).from(table).build();

		assertThat(NaiveSqlRenderer.render(select)).isEqualTo("SELECT my_bar.foo AS my_foo FROM bar AS my_bar");
	}

	@Test
	public void shouldRenderMultipleColumnsFromTables() {

		Table table1 = Table.create("table1");
		Table table2 = Table.create("table2");

		Select select = Select.builder().select(table1.column("col1")).select(table2.column("col2")).from(table1).from(table2).build();

		assertThat(NaiveSqlRenderer.render(select)).isEqualTo("SELECT table1.col1, table2.col2 FROM table1, table2");
	}

	@Test
	public void shouldRenderDistinct() {

		Select select = Select.builder().select(Functions.distinct("foo", "bar")).from("bar").build();

		assertThat(NaiveSqlRenderer.render(select)).isEqualTo("SELECT DISTINCT foo, bar FROM bar");
	}

	@Test
	public void shouldRenderCountFunction() {

		Select select = Select.builder().select(Functions.count("foo"), Column.create("bar")).from("bar").build();

		assertThat(NaiveSqlRenderer.render(select)).isEqualTo("SELECT COUNT(foo), bar FROM bar");
	}
}

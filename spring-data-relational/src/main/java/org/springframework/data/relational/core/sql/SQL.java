package org.springframework.data.relational.core.sql;

import org.springframework.data.relational.core.sql.BindMarker.NamedBindMarker;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectAndFrom;
import org.springframework.util.Assert;

/**
 * Utility to create SQL {@link Segment}s. Typically used as entry point to the Query Builder AST.
 * Objects and dependent objects created by the Query AST are immutable except for builders.
 * <p/>The Query Builder API is intended for framework usage to produce SQL required for framework operations.
 *
 * @author Mark Paluch
 * @see Expressions
 * @see Conditions
 * @see Functions
 */
public abstract class SQL {

	/**
	 * Creates a new {@link SelectBuilder} by specifying a {@code SELECT} column.
	 *
	 * @param expression the select list expression.
	 * @return the {@link SelectBuilder} containing {@link Expression}.
	 * @see SelectBuilder#select(Expression)
	 */
	public static SelectAndFrom newSelect(Expression expression) {
		return Select.builder().select(expression);
	}

	/**
	 * Creates a new {@link SelectBuilder} by specifying one or more {@code SELECT} columns.
	 *
	 * @param expressions the select list expressions.
	 * @return the {@link SelectBuilder} containing {@link Expression}s.
	 * @see SelectBuilder#select(Expression...)
	 */
	public static SelectAndFrom newSelect(Expression... expressions) {
		return Select.builder().select(expressions);
	}

	/**
	 * Creates a new {@link SelectBuilder}.
	 *
	 * @return the new {@link SelectBuilder}.
	 * @see SelectBuilder
	 */
	public static SelectBuilder select() {
		return Select.builder();
	}

	/**
	 * Creates a new {@link SelectTop SELECT TOP count} segment.
	 *
	 * @param count the TOP count.
	 * @return the new {@link SelectTop} segment.
	 */
	public static SelectTop top(int count) {
		return SelectTop.create(count);
	}

	/**
	 * Creates a new {@link Column}.
	 *
	 * @param name column name, must not be {@literal null} or empty.
	 * @return the column with {@code name}.
	 */
	public static Column column(String name) {
		return Column.create(name);
	}

	/**
	 * Creates a new {@link Column} associated with a source {@link Table}.
	 *
	 * @param name column name, must not be {@literal null} or empty.
	 * @param table table name, must not be {@literal null}.
	 * @return the column with {@code name} associated with {@link Table}.
	 */
	public static Column column(String name, Table table) {
		return Column.create(name, table);
	}

	/**
	 * Creates a new {@link Table}.
	 *
	 * @param name table name, must not be {@literal null} or empty.
	 * @return the column with {@code name}.
	 */
	public static Table table(String name) {
		return Table.create(name);
	}

	/**
	 * Creates a new parameter bind marker.
	 *
	 * @return a new {@link BindMarker}.
	 */
	public static BindMarker bindMarker() {
		return new BindMarker();
	}

	/**
	 * Creates a new parameter bind marker associated with a {@code name} hint.
	 *
	 * @param name name hint, must not be {@literal null} or empty.
	 * @return a new {@link BindMarker}.
	 */
	public static BindMarker bindMarker(String name) {

		Assert.hasText(name, "Name must not be null or empty!");

		return new NamedBindMarker(name);
	}

	// Utility constructor.
	private SQL() {
	}
}

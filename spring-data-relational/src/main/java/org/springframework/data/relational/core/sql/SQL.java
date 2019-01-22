package org.springframework.data.relational.core.sql;

import org.springframework.util.Assert;

/**
 * Utility to create SQL {@link Segment}s.
 *
 * @author Mark Paluch
 */
public class SQL {

	public static SelectTop top(int count) {
		return SelectTop.create(count);
	}

	public static SelectColumn column(String name) {
		return SelectColumn.create(name);
	}

	public static SelectColumn column(String name, Table table) {
		return SelectColumn.create(name, table);
	}

	public static Table table(String name) {
		return Table.create(name);
	}

	public static BindMarker bindMarker() {
		return new BindMarker(null);
	}

	public static BindMarker bindMarker(String name) {

		Assert.hasText(name, "Name must not be empty or null!");

		return new BindMarker(name);
	}
}

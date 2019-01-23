package org.springframework.data.relational.core.sql;

/**
 * @author Mark Paluch
 */
public class SimpleSegment extends AbstractSegment {

	private final String sql;

	public SimpleSegment(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	@Override
	public String toString() {
		return getSql();
	}
}
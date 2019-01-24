package org.springframework.data.relational.core.sql;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * {@code FROM} clause.
 *
 * @author Mark Paluch
 */
public class From extends AbstractSegment implements Segment {

	private final List<Table> tables;

	From(Table... tables) {
		this(Arrays.asList(tables));
	}

	From(List<Table> tables) {
		this.tables = tables;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.relational.core.sql.Visitable#visit(org.springframework.data.relational.core.sql.Visitor)
	 */
	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);

		tables.forEach(it -> it.visit(visitor));

		visitor.leave(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FROM " + StringUtils.collectionToDelimitedString(tables, ", ");
	}
}

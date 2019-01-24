package org.springframework.data.relational.core.sql;

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * {@code DISTINCT} {@link Expression}.
 * <p/>
 * * Renders to: {@code DISTINCT column1, column2, columnN}.
 *
 * @author Mark Paluch
 */
public class Distinct extends AbstractSegment implements Expression {

	private List<Column> columns;

	Distinct(List<Column> columns) {
		this.columns = columns;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.relational.core.sql.Visitable#visit(org.springframework.data.relational.core.sql.Visitor)
	 */
	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		columns.forEach(it -> it.visit(visitor));
		visitor.leave(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DISTINCT " + StringUtils.collectionToDelimitedString(columns, ", ");
	}
}

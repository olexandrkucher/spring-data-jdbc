package org.springframework.data.relational.core.sql;

import java.util.OptionalLong;

/**
 * AST for a {@code SELECT} statement.
 * Visiting order:
 * <ol>
 * <li>Self</li>
 * <li>{@link SelectTop top clause}</li>
 * <li>{@link Column SELECT columns} </li>
 * <li>{@link Table FROM tables} clause</li>
 * <li>{@link Join JOINs}</li>
 * <li>{@link Condition WHERE} condition</li>
 * <li>{@link OrderByField ORDER BY fields}</li>
 * </ol>
 *
 * @author Mark Paluch
 * @see SelectBuilder
 * @see SQL
 */
public interface Select extends Visitable {

	/**
	 * Optional limit. Used for limit/offset paging.
	 *
	 * @return
	 */
	OptionalLong getLimit();

	/**
	 * Optional offset. Used for limit/offset paging.
	 *
	 * @return
	 */
	OptionalLong getOffset();
}

package org.springframework.data.relational.core.sql;

import java.util.Collection;

import org.springframework.data.domain.PageRequest;

/**
 * Entry point to construct a {@link Select} statement.
 *
 * @author Mark Paluch
 */
public interface SelectBuilder {

	SelectBuilder top(int topCount);

	SelectAndFrom select(String sql);

	SelectAndFrom select(Expression expression);

	SelectAndFrom select(Expression... expressions);

	SelectAndFrom select(Collection<? extends Expression> expression);

	interface SelectAndFrom {

		SelectFrom select(String table);

		SelectFrom select(Expression expression);

		SelectFrom select(Expression... expressions);

		SelectFrom select(Collection<? extends Expression> expression);

		SelectFromAndJoin from(Table table);

		SelectFromAndJoin from(Table... tables);

		SelectFromAndJoin from(Collection<? extends Table> tables);
	}

	interface SelectFrom {

		SelectFromAndJoin from(Table table);

		SelectFromAndJoin from(Table... tables);

		SelectFromAndJoin from(Collection<? extends Table> tables);
	}

	interface SelectFromAndJoin extends SelectFrom, BuildSelect, SelectJoin, SelectWhere {

		SelectFromAndJoin from(Table table);

		SelectFromAndJoin from(Table... tables);

		SelectFromAndJoin from(Collection<? extends Table> tables);

		SelectFromAndJoin limitOffset(PageRequest pageable);

		SelectFromAndJoin limitOffset(long limit, long offset);
	}

	interface SelectFromAndJoinCondition extends BuildSelect, SelectJoin, SelectWhere, SelectOnCondition {

		SelectFromAndJoin limitOffset(PageRequest pageable);

		SelectFromAndJoin limitOffset(long limit, long offset);
	}

	interface SelectOrdered extends BuildSelect {

		SelectOrdered orderBy(String field);

		SelectOrdered orderBy(int... indexes);

		SelectOrdered orderBy(OrderByField... orderByFields);

		SelectOrdered orderBy(Collection<? extends OrderByField> orderByFields);

		SelectOrdered orderBy(SelectColumn... selectColumns);
	}

	interface SelectWhere extends SelectOrdered, BuildSelect {

		SelectWhereAndOr where(Condition condition);
	}

	interface SelectWhereAndOr extends SelectOrdered, BuildSelect {

		SelectWhereAndOr and(Condition condition);

		SelectWhereAndOr or(Condition condition);
	}

	interface SelectFromAndJoinAnd extends SelectOnCondition, SelectFromAndJoinCondition {

	}

	interface SelectJoin extends BuildSelect {

		SelectOn join(String table);

		SelectOn join(Table table);
	}

	interface SelectOn {

		SelectOnConditionComparison on(String field);

		SelectOnConditionComparison on(Expression field);
	}

	interface SelectOnCondition extends SelectJoin, BuildSelect {

		SelectOnConditionComparison and(String field);

		SelectOnConditionComparison and(Expression field);
	}

	interface SelectOnConditionComparison {

		SelectFromAndJoinCondition equals(String field);

		SelectFromAndJoinCondition equals(Expression field);
	}

	interface BuildSelect {

		Select build();
	}
}

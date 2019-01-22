package org.springframework.data.relational.core.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.sql.Join.JoinType;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectAndFrom;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectWhereAndOr;

/**
 * Default {@link SelectBuilder} implementation.
 *
 * @author Mark Paluch
 */
class DefaultSelectBuilder implements SelectBuilder, SelectAndFrom, SelectFromAndJoin, SelectWhereAndOr {

	private SelectTop top;
	private List<Expression> selectList = new ArrayList<>();
	private List<Table> from = new ArrayList<>();
	private long limit = -1;
	private long offset = -1;
	private List<Join> joins = new ArrayList<>();
	private List<OrderByField> orderBy = new ArrayList<>();
	private Condition where;

	@Override
	public SelectBuilder top(int topCount) {

		top = SelectTop.create(topCount);
		return this;
	}

	@Override
	public DefaultSelectBuilder select(String sql) {
		return select(SQL.column(sql));
	}

	@Override
	public DefaultSelectBuilder select(Expression expression) {
		selectList.add(expression);
		return this;
	}

	@Override
	public DefaultSelectBuilder select(Expression... expressions) {
		selectList.addAll(Arrays.asList(expressions));
		return this;
	}

	@Override
	public DefaultSelectBuilder select(Collection<? extends Expression> expression) {
		selectList.addAll(expression);
		return this;
	}

	@Override
	public SelectFromAndJoin from(Table table) {
		from.add(table);
		return this;
	}

	@Override
	public SelectFromAndJoin from(Table... tables) {
		from.addAll(Arrays.asList(tables));
		return this;
	}

	@Override
	public SelectFromAndJoin from(Collection<? extends Table> tables) {
		from.addAll(tables);
		return this;
	}

	@Override
	public SelectFromAndJoin limitOffset(PageRequest pageable) {
		this.limit = pageable.getOffset() + pageable.getPageSize();
		return this;
	}

	@Override
	public SelectFromAndJoin limitOffset(long limit, long offset) {
		this.limit = limit;
		this.offset = offset;
		return this;
	}

	@Override
	public SelectOrdered orderBy(String field) {
		return orderBy(OrderByField.create(field));
	}

	@Override
	public SelectOrdered orderBy(int... indexes) {

		for (int index : indexes) {
			this.orderBy.add(OrderByField.index(index));
		}
		return this;
	}

	@Override
	public SelectOrdered orderBy(OrderByField... orderByFields) {

		this.orderBy.addAll(Arrays.asList(orderByFields));

		return this;
	}

	@Override
	public SelectOrdered orderBy(Collection<? extends OrderByField> orderByFields) {

		this.orderBy.addAll(orderByFields);

		return this;
	}

	@Override
	public SelectOrdered orderBy(SelectColumn... selectColumns) {

		for (SelectColumn selectColumn : selectColumns) {
			this.orderBy.add(OrderByField.from(selectColumn));
		}

		return this;
	}

	@Override
	public SelectWhereAndOr where(Condition condition) {

		where = condition;
		return this;
	}

	@Override
	public SelectWhereAndOr and(Condition condition) {

		where = where.and(condition);
		return this;
	}

	@Override
	public SelectWhereAndOr or(Condition condition) {

		where = where.or(condition);
		return this;
	}

	@Override
	public SelectOn join(String table) {
		return join(SQL.table(table));
	}

	@Override
	public SelectOn join(Table table) {
		return new JoinBuilder(table, this);
	}


	public DefaultSelectBuilder join(Join join) {
		this.joins.add(join);

		return this;
	}

	@Override
	public Select build() {
		return null;
	}

	static class JoinBuilder implements SelectOn, SelectOnConditionComparison, SelectFromAndJoinCondition {

		private final Table table;
		private final DefaultSelectBuilder selectBuilder;
		private Expression from;
		private Expression to;
		private Condition condition;

		JoinBuilder(Table table, DefaultSelectBuilder selectBuilder) {
			this.table = table;
			this.selectBuilder = selectBuilder;
		}

		@Override
		public SelectOnConditionComparison on(String field) {
			return on(SQL.column(field));
		}

		@Override
		public SelectOnConditionComparison on(Expression field) {

			this.from = field;
			return null;
		}

		@Override
		public JoinBuilder equals(String field) {
			return equals(SQL.column(field));
		}

		@Override
		public JoinBuilder equals(Expression field) {
			this.to = field;
			return null;
		}

		@Override
		public SelectOnConditionComparison and(String field) {
			return and(SQL.column(field));
		}

		@Override
		public SelectOnConditionComparison and(Expression field) {

			finishCondition();
			this.from = field;
			return this;
		}

		private void finishCondition() {
			Equals equals = Equals.create(from, to);

			if (condition == null) {
				condition = equals;
			} else {
				condition.and(equals);
			}
		}

		private Join finishJoin() {

			finishCondition();
			return new Join(table, JoinType.JOIN, condition);
		}

		@Override
		public SelectOrdered orderBy(String field) {

			selectBuilder.join(finishJoin());
			return selectBuilder.orderBy(field);
		}

		@Override
		public SelectOrdered orderBy(int... indexes) {
			selectBuilder.join(finishJoin());
			return selectBuilder.orderBy(indexes);
		}

		@Override
		public SelectOrdered orderBy(OrderByField... orderByFields) {
			selectBuilder.join(finishJoin());
			return selectBuilder.orderBy(orderByFields);
		}

		@Override
		public SelectOrdered orderBy(Collection<? extends OrderByField> orderByFields) {
			selectBuilder.join(finishJoin());
			return selectBuilder.orderBy(orderByFields);
		}

		@Override
		public SelectOrdered orderBy(SelectColumn... selectColumns) {
			selectBuilder.join(finishJoin());
			return selectBuilder.orderBy(selectColumns);
		}

		@Override
		public SelectWhereAndOr where(Condition condition) {
			selectBuilder.join(finishJoin());
			return selectBuilder.where(condition);
		}

		@Override
		public SelectOn join(String table) {
			selectBuilder.join(finishJoin());
			return selectBuilder.join(table);
		}

		@Override
		public SelectOn join(Table table) {
			selectBuilder.join(finishJoin());
			return selectBuilder.join(table);
		}

		@Override
		public SelectFromAndJoin limitOffset(PageRequest pageable) {
			selectBuilder.join(finishJoin());
			return selectBuilder.limitOffset(pageable);
		}

		@Override
		public SelectFromAndJoin limitOffset(long limit, long offset) {
			selectBuilder.join(finishJoin());
			return selectBuilder.limitOffset(limit, offset);
		}

		@Override
		public Select build() {
			selectBuilder.join(finishJoin());
			return selectBuilder.build();
		}
	}
}

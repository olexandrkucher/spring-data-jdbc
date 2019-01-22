package org.springframework.data.relational.core.sql;

/**
 * {@link Segment} for a {@code JOIN} declaration.
 *
 * @author Mark Paluch
 */
public class Join implements Segment {

	private final Table joinTable;
	private final JoinType type;
	private final Condition on;

	public Join(Table joinTable, JoinType type, Condition on) {
		this.joinTable = joinTable;
		this.type = type;
		this.on = on;
	}

	@Override
	public void visit(Visitor visitor) {

		visitor.enter(this);

		visitor.enter(joinTable);
		visitor.enter(on);

		visitor.leave(this);
	}

	public Table getJoinTable() {
		return joinTable;
	}

	public JoinType getType() {
		return type;
	}

	public Condition getOn() {
		return on;
	}

	public enum JoinType {

		/**
		 * {@code INNER JOIN} for two tables.
		 */

		JOIN("JOIN"),

		/**
		 * {@code CROSS JOIN} for two tables.
		 */

		CROSS_JOIN("CROSS JOIN"),

		/**
		 * {@code LEFT OUTER JOIN} two tables.
		 */

		LEFT_OUTER_JOIN("LEFT OUTER JOIN"),

		/**
		 * {@code RIGHT OUTER JOIN} two tables.
		 */

		RIGHT_OUTER_JOIN("RIGHT OUTER JOIN"),

		/**
		 * {@code FULL OUTER JOIN} two tables.
		 */

		FULL_OUTER_JOIN("FULL OUTER JOIN");


		private final String sql;

		JoinType(String sql) {
			this.sql = sql;
		}

		public String getSql() {
			return sql;
		}
	}
}

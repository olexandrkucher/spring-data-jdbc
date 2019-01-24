package org.springframework.data.relational.core.sql;

/**
 * {@link Segment} for a {@code JOIN} declaration.
 * <p/>
 * Renders to: {@code JOIN <table> ON <condition>}.
 *
 * @author Mark Paluch
 */
public class Join implements Segment {

	private final JoinType type;
	private final Table joinTable;
	private final Condition on;

	Join(JoinType type, Table joinTable, Condition on) {
		this.joinTable = joinTable;
		this.type = type;
		this.on = on;
	}

	@Override
	public void visit(Visitor visitor) {

		visitor.enter(this);

		joinTable.visit(visitor);
		on.visit(visitor);

		visitor.leave(this);
	}

	/**
	 * @return join type.
	 */
	public JoinType getType() {
		return type;
	}

	/**
	 * @return the joined {@link Table}.
	 */
	public Table getJoinTable() {
		return joinTable;
	}

	/**
	 * @return join condition (the ON or USING part).
	 */
	public Condition getOn() {
		return on;
	}

	@Override
	public String toString() {
		return type + " " + joinTable + " ON " + on;
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

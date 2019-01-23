package org.springframework.data.relational.core.sql;

import java.util.Stack;
import java.util.function.Consumer;

import org.springframework.util.Assert;

/**
 * Naive SQL renderer that does not consider dialect specifics..
 *
 * @author Mark Paluch
 */
public class NaiveSqlRenderer {

	private final Select select;

	private NaiveSqlRenderer(Select select) {

		Assert.notNull(select, "Select must not be null!");

		this.select = select;
	}

	/**
	 * Creates a new {@link NaiveSqlRenderer}.
	 *
	 * @param select must not be {@literal null}.
	 * @return
	 */
	public static NaiveSqlRenderer create(Select select) {
		return new NaiveSqlRenderer(select);
	}

	/**
	 * Renders a {@link Select} statement into its SQL representation.
	 *
	 * @param select must not be {@literal null}.
	 * @return
	 */
	public static String render(Select select) {
		return create(select).render();
	}

	public String render() {

		RenderVisitor visitor = new RenderVisitor();
		select.visit(visitor);

		return visitor.builder.toString();
	}

	/**
	 * {@link Visitor} to render the SQL.
	 */
	static class RenderVisitor implements Visitor {

		StringBuilder builder = new StringBuilder();

		private boolean hasDistinct = false;
		private boolean nextRequiresComma = false;
		private Stack<Visitable> segments = new Stack<>();

		@Override
		public void enter(Visitable segment) {

			if (segment instanceof Select) {
				builder.append("SELECT ");
			}

			if (segment instanceof From) {

				nextRequiresComma = false;

				if (requiresSpace()) {
					builder.append(' ');
				}

				builder.append("FROM ");
			}

			if (segment instanceof Distinct && !hasDistinct) {
				builder.append("DISTINCT ");
				hasDistinct = true;
			}

			if (segment instanceof SimpleFunction) {

				nextRequiresComma = false;
				builder.append(((SimpleFunction) segment).getFunctionName()).append("(");
			}

			if (segment instanceof Table && segments.peek() instanceof From) {

				if (nextRequiresComma) {
					builder.append(", ");
				}

				Table table = (Table) segment;

				builder.append(table.getName());

				ifAliased(segment, aliased -> builder.append(" AS ").append(aliased.getAlias()));

				nextRequiresComma = true;
			}

			segments.add(segment);
		}

		@Override
		public void leave(Visitable segment) {

			segments.pop();
			Visitable parent = segments.isEmpty() ? null : segments.peek();

			if (segment instanceof Table && parent instanceof Column) {

				if (nextRequiresComma) {
					builder.append(", ");
					nextRequiresComma = false;
				}
				builder.append(((Table) segment).getReferenceName()).append('.');
			}

			if (segment instanceof Column && (parent instanceof Select || parent instanceof Distinct || parent instanceof SimpleFunction)) {

				if (nextRequiresComma) {
					builder.append(", ");
				}

				Column column = (Column) segment;

				builder.append(column.getName());

				if (!(parent instanceof SimpleFunction)) {
					ifAliased(segment, aliased -> builder.append(" AS ").append(aliased.getAlias()));
				}

				nextRequiresComma = true;
			}

			if (segment instanceof SimpleFunction) {

				builder.append(")");
				ifAliased(segment, aliased -> builder.append(" AS ").append(aliased.getAlias()));

				nextRequiresComma = true;
			}
		}

		private boolean requiresSpace() {
			return builder.length() != 0 && builder.charAt(builder.length() - 1) != ' ';
		}

		private void ifAliased(Object segment, Consumer<Aliased> aliasedConsumer) {

			if (segment instanceof Aliased) {
				aliasedConsumer.accept((Aliased) segment);
			}
		}
	}
}

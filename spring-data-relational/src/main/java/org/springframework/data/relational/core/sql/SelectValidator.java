package org.springframework.data.relational.core.sql;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mark Paluch
 */
class SelectValidator implements Visitor {

	private Set<Table> requiredBySelect = new HashSet<>();
	private Set<Table> requiredByWhere = new HashSet<>();
	private Set<Table> requiredByOrderBy = new HashSet<>();

	private Set<Table> from = new HashSet<>();
	private Set<Table> join = new HashSet<>();

	private Visitable parent;

	public static void validate(Select select) {
		new SelectValidator().doValidate(select);
	}

	private void doValidate(Select select) {

		select.visit(this);

		for (Table table : requiredBySelect) {
			if (!join.contains(table) && !from.contains(table)) {
				throw new IllegalStateException(String.format("Required table [%s] by a SELECT column not imported by FROM %s or JOIN %s", table, from, join));
			}
		}

		for (Table table : requiredByWhere) {
			if (!join.contains(table) && !from.contains(table)) {
				throw new IllegalStateException(String.format("Required table [%s] by a WHERE predicate not imported by FROM %s or JOIN %s", table, from, join));
			}
		}

		for (Table table : requiredByOrderBy) {
			if (!join.contains(table) && !from.contains(table)) {
				throw new IllegalStateException(String.format("Required table [%s] by a ORDER BY column not imported by FROM %s or JOIN %s", table, from, join));
			}
		}
	}

	@Override
	public void enter(Visitable segment) {

		if (segment instanceof AsteriskFromTable && parent instanceof Select) {

			Table table = ((AsteriskFromTable) segment).getTable();
			requiredBySelect.add(table);
		}

		if (segment instanceof Column && (parent instanceof Select || parent instanceof SimpleFunction || parent instanceof Distinct)) {

			Table table = ((Column) segment).getTable();

			if (table != null) {
				requiredBySelect.add(table);
			}
		}

		if (segment instanceof Table && parent instanceof From) {
			from.add((Table) segment);
		}

		if (segment instanceof Column && parent instanceof OrderByField) {

			Table table = ((Column) segment).getTable();

			if (table != null) {
				requiredByOrderBy.add(table);
			}
		}

		if (segment instanceof Table && parent instanceof Join) {
			join.add((Table) segment);
		}

		if (segment instanceof Where) {

			segment.visit(item -> {

				if (item instanceof Table) {
					requiredByWhere.add((Table) item);
				}
			});
		}

		if (segment instanceof Join || segment instanceof OrderByField || segment instanceof From || segment instanceof Select || segment instanceof Where || segment instanceof SimpleFunction || segment instanceof Distinct) {
			parent = segment;
		}
	}

	@Override
	public void leave(Visitable segment) {
	}
}

package org.springframework.data.relational.core.sql;

/**
 * AST {@link Segment} visitor. Visitor methods get called by segments on entering a {@link Segment}, their child {@link Segment}s and on leaving the {@link Segment}.
 *
 * @author Mark Paluch
 */
public interface Visitor {

	/**
	 * Enter a {@link Segment}.
	 *
	 * @param segment the segment to visit.
	 */
	void enter(Visitable segment);

	/**
	 * Leave a {@link Segment}.
	 *
	 * @param segment the visited segment.
	 */
	void leave(Visitable segment);
}

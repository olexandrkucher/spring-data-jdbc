package org.springframework.data.relational.core.sql;

/**
 * Supertype of all Abstract Syntax Tree (AST) segments. Segments are typically immutable and mutator methods return new instances instead of changing the called instance.
 *
 * @author Mark Paluch
 */
public interface Segment extends Visitable {

	/**
	 * Check whether this {@link Segment} is equal to another {@link Segment}.
	 * <p/>
	 * Equality is typically given if the {@link #toString()} representation matches.
	 *
	 * @param other the reference object with which to compare.
	 * @return {@literal true} if this object is the same as the {@code other}
	 * argument; {@literal false} otherwise.
	 */
	@Override
	boolean equals(Object other);

	/**
	 * Generate a hash code from this{@link Segment}.
	 * <p/>
	 * Hashcode typically derives from the {@link #toString()} representation so two {@link Segment}s yield the same  {@link #hashCode()}  if their {@link #toString()}  representation matches.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	int hashCode();

	/**
	 * Return a SQL string representation of this {@link Segment}.
	 * <p/>
	 * The representation is intended for debugging purposes and an approximation to the generated SQL. While it might work in the context of a specific dialect, you should not that the {@link #toString()} representation works across multiple databases.
	 *
	 * @return a SQL string representation of this {@link Segment}.
	 */
	@Override
	String toString();
}

package org.springframework.data.relational.core.sql;

/**
 * Abstract implementation to support {@link Segment} implementations.
 *
 * @author Mark Paluch
 */
abstract class AbstractSegment implements Segment {

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Segment && toString().equals(obj.toString());
	}
}

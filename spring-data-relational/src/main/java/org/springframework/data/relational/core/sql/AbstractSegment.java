package org.springframework.data.relational.core.sql;

/**
 * Abstract implementation to support {@link Segment} implementations.
 *
 * @author Mark Paluch
 */
abstract class AbstractSegment implements Segment {

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Segment && toString().equals(obj.toString());
	}
}

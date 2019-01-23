package org.springframework.data.relational.core.sql;

/**
 * {@code TOP} clause for {@code SELECT TOP …}.
 *
 * @author Mark Paluch
 */
public class SelectTop extends AbstractSegment implements Segment {

	private final int count;

	private SelectTop(int count) {
		this.count = count;
	}

	public static SelectTop create(int count) {
		return new SelectTop(count);
	}

	/**
	 * @return the count.
	 */
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "TOP " + count;
	}
}

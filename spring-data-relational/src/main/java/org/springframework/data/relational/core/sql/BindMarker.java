package org.springframework.data.relational.core.sql;

import org.springframework.lang.Nullable;

/**
 * Bind marker/parameter placeholder used to construct prepared statements with parameter substitution.
 *
 * @author Mark Paluch
 */
public class BindMarker implements Segment {

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "?";
	}

	static class NamedBindMarker extends BindMarker implements Named {

		private final String name;

		NamedBindMarker(String name) {
			this.name = name;
		}

		/*
		 * (non-Javadoc)
		 * @see org.springframework.data.relational.core.sql.Named#getName()
		 */
		@Nullable
		@Override
		public String getName() {
			return name;
		}

		/*
		 * (non-Javadoc)
		 * @see org.springframework.data.relational.core.sql.BindMarker#toString()
		 */
		@Override
		public String toString() {
			return "?[" + name + "]";
		}
	}
}

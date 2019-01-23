package org.springframework.data.relational.core.sql;

import org.springframework.lang.Nullable;

/**
 * Bind marker/parameter placeholder used to construct prepared statements with parameter substitution.
 *
 * @author Mark Paluch
 */
public class BindMarker implements Segment {

	@Override
	public String toString() {
		return "?";
	}

	static class NamedBindMarker extends BindMarker implements Named {

		private final String name;

		NamedBindMarker(String name) {
			this.name = name;
		}

		@Nullable
		@Override
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return "?[" + name + "]";
		}
	}
}

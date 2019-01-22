package org.springframework.data.relational.core.sql;

/**
 * Bind marker/parameter placeholder used to construct prepared statements with parameter substitution.
 *
 * @author Mark Paluch
 */
public class BindMarker implements Segment, Named {

	private final String name;

	BindMarker(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {

		if (name != null) {
			return "?[" + name + "]";
		}

		return "?";
	}
}

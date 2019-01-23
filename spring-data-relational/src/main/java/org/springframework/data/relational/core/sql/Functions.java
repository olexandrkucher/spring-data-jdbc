package org.springframework.data.relational.core.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.util.Assert;

/**
 * Factory for common {@link Expression function expressions}.
 *
 * @author Mark Paluch
 * @see SQL
 * @see Expressions
 * @see Functions
 */
public class Functions {

	/**
	 * Creates a new {@link Distinct} function.
	 *
	 * @param columns columns to apply distinction, must not be {@literal null}.
	 * @return the new {@link Distinct} for {@code columns}.
	 */
	public static Distinct distinct(Column... columns) {

		Assert.notNull(columns, "Columns must not be null!");

		return new Distinct(Arrays.asList(columns));
	}

	/**
	 * Creates a new {@link Distinct} function.
	 *
	 * @param columns columns to apply distinction, must not be {@literal null}.
	 * @return the new {@link Distinct} for {@code columns}.
	 */
	public static Distinct distinct(Collection<? extends Column> columns) {

		Assert.notNull(columns, "Columns must not be null!");

		return new Distinct(new ArrayList<>(columns));
	}

	/**
	 * Creates a new {@code COUNT} function.
	 *
	 * @param columns columns to apply count, must not be {@literal null}.
	 * @return the new {@link SimpleFunction count function} for {@code columns}.
	 */
	public static SimpleFunction count(Column... columns) {

		Assert.notNull(columns, "Columns must not be null!");

		return new SimpleFunction("COUNT", Arrays.asList(columns));
	}

	/**
	 * Creates a new {@code COUNT} function.
	 *
	 * @param columns columns to apply count, must not be {@literal null}.
	 * @return the new {@link SimpleFunction count function} for {@code columns}.
	 */
	public static SimpleFunction count(Collection<? extends Expression> columns) {

		Assert.notNull(columns, "Columns must not be null!");

		return new SimpleFunction("COUNT", new ArrayList<>(columns));
	}

	// Utility constructor.
	private Functions() {
	}
}

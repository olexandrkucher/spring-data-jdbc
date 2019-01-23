package org.springframework.data.relational.core.sql;

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Simple function accepting one or more {@link Expression}s.
 *
 * @author Mark Paluch
 */
public class SimpleFunction extends AbstractSegment implements Expression {

	private String functionName;
	private List<Expression> expressions;

	SimpleFunction(String functionName, List<Expression> expressions) {
		this.functionName = functionName;
		this.expressions = expressions;
	}

	/**
	 * Expose this function result under a column {@code alias}.
	 *
	 * @param alias column alias name, must not {@literal null} or empty.
	 * @return the aliased {@link SimpleFunction}.
	 */
	public SimpleFunction as(String alias) {

		Assert.hasText(alias, "Alias must not be null or empty");

		return new AliasedFunction(functionName, expressions, alias);
	}

	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		expressions.forEach(it -> it.visit(visitor));
		visitor.leave(this);
	}

	/**
	 * @return the function name.
	 */
	public String getFunctionName() {
		return functionName;
	}

	@Override
	public String toString() {
		return functionName + "(" + StringUtils.collectionToDelimitedString(expressions, ", ") + ")";
	}

	/**
	 * {@link Aliased} {@link SimpleFunction} implementation.
	 */
	static class AliasedFunction extends SimpleFunction implements Aliased {

		private final String alias;

		AliasedFunction(String functionName, List<Expression> expressions, String alias) {
			super(functionName, expressions);
			this.alias = alias;
		}

		@Override
		public String getAlias() {
			return alias;
		}
	}
}

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

	@Override
	public void visit(Visitor visitor) {

		Assert.notNull(visitor, "Visitor must not be null!");

		visitor.enter(this);
		expressions.forEach(it -> it.visit(visitor));
		visitor.leave(this);
	}

	public String getFunctionName() {
		return functionName;
	}

	@Override
	public String toString() {
		return functionName + "(" + StringUtils.collectionToDelimitedString(expressions, ", ") + ")";
	}
}

package cnf;

import java.io.IOException;

import unification.Expression;

public class DoubleImplication extends Expression {
	
	public DoubleImplication(Expression left, Expression right) throws IOException {
		super();
		super.myExpression.add(left);
		super.myExpression.add(right);
	}

	@Override
	public String toString() {
		return "(" + super.myExpression.get(0).toString() + "⇔" + super.myExpression.get(1).toString() + ")";
	}
}

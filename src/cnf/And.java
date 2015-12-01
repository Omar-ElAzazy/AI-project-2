package cnf;

import java.io.IOException;

import unification.Expression;

public class And extends Expression{
	public And(Expression left, Expression right) throws IOException {
		super();
		super.myExpression.add(left);
		super.myExpression.add(right);
	}

	@Override
	public String toString() {
		String res =  "(" + super.myExpression.get(0).toString() + ") ^ (" + super.myExpression.get(1).toString() + ")";
		if (negated == true) {
			res = "!" + res;
		}
		return res;
	}
}

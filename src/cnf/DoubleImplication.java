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
		String res = "(" + super.myExpression.get(0).toString() + ") ⇔ (" + super.myExpression.get(1).toString() + ")";
		if (negated == true) {
			res = "!" + res;
		}
		return res;
	}
	
	@Override
	public Expression deepCopy() throws IOException{
		DoubleImplication copy = new DoubleImplication(myExpression.get(0).deepCopy(), myExpression.get(1).deepCopy());
		copy.negated = negated;
		return copy;
	}
}

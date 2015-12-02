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
		String res = "";
		if(CNF.flattened){
			res = super.myExpression.get(0).toString() + "\n^ " + super.myExpression.get(1).toString();
		}
		else{
			res =  "(" + super.myExpression.get(0).toString() + ") ^ (" + super.myExpression.get(1).toString() + ")";
			if (negated == true) {
				res = "!" + res;
			}
		}
		return res;
	}
	
	@Override
	public Expression deepCopy() throws IOException{
		And copy = new And(myExpression.get(0).deepCopy(), myExpression.get(1).deepCopy());
		copy.negated = negated;
		return copy;
	}
}

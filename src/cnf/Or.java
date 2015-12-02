package cnf;

import java.io.IOException;

import unification.Expression;
import unification.Variable;

public class Or extends Expression{
	public Or(Expression left, Expression right) throws IOException {
		super();
		super.myExpression.add(left);
		super.myExpression.add(right);
	}

	@Override
	public String toString() {
		String res = "";
		if(CNF.flattened){
			res = super.myExpression.get(0).toString() + " v " + super.myExpression.get(1).toString();
		}
		else{
			res = "(" + super.myExpression.get(0).toString() + ") v (" + super.myExpression.get(1).toString() + ")";
			if (negated == true) {
				res = "!" + res;
			}
		}
		return res;
	}
	
	@Override
	public Expression deepCopy() throws IOException{
		Or copy = new Or(myExpression.get(0).deepCopy(), myExpression.get(1).deepCopy());
		copy.negated = negated;
		return copy;
	}
}

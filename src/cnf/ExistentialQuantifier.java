package cnf;

import java.io.IOException;

import unification.Expression;
import unification.Variable;

public class ExistentialQuantifier extends Expression {
	Variable variable;
	public static boolean removed = false;
	
	public ExistentialQuantifier(Variable variable, Expression expression){
		super();
		this.variable = variable;
		super.myExpression.add(expression);
	}
	
	public String toString(){
		String res = "";
		if (removed == true) {
			res = super.myExpression.get(0).toString();
		}
		else {
			res = "∃" + variable.toString() + " (" + super.myExpression.get(0).toString() + ")";
		}
		if (negated == true) {
			res = "!(" + res + ")";
		}
		return res;
	}
	
	@Override
	public Expression deepCopy() throws IOException{
		ExistentialQuantifier copy = new ExistentialQuantifier((Variable)variable.deepCopy(), myExpression.get(0).deepCopy());
		copy.negated = negated;
		return copy;
	}
}

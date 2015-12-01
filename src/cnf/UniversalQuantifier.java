package cnf;

import unification.Expression;
import unification.Variable;

public class UniversalQuantifier extends Expression{
	Variable variable;
	boolean discarded = false;
	
	public UniversalQuantifier(Variable variable, Expression expression){
		super();
		this.variable = variable;
		super.myExpression.add(expression);
	}
	
	public String toString(){
		if (discarded == true) {
			return super.myExpression.get(0).toString();
		}
		else {
			return "for_all" + variable.toString() + " " + super.myExpression.get(0).toString();
		}
	}
}

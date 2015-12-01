package cnf;

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
		return "(there_exists" + variable.toString() + " " + super.myExpression.get(0).toString() + ")";
	}
}

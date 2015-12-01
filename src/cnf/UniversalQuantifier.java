package cnf;

import java.util.ArrayList;
import java.util.HashMap;

import unification.Expression;
import unification.Variable;

public class UniversalQuantifier extends Expression{
	Variable variable;
	public UniversalQuantifier(Variable variable, Expression expression){
		super();
		this.variable = variable;
		super.myExpression.add(expression);
	}
	
	public String toString(){
		return "for_all" + variable.toString() + " " + super.myExpression.get(0).toString();
	}
}

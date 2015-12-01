package cnf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import unification.Constant;
import unification.Expression;
import unification.Function;
import unification.Variable;

public class CNF {

	public static Expression normalize(Expression E) throws IOException {
		E = eliminateDoubleImplication(E);
		E = eliminateImplication(E);
		E = pushNegationInwards(E);
		E = Standardize(E);
		E = Skolemize(E);
		E = discardUniversalQuantifiers(E);
		E = translateIntoCNF(E);
		E = flatten(E);
		return E;
	}

	private static Expression flatten(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression translateIntoCNF(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression discardUniversalQuantifiers(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression Skolemize(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getNewVariableName(ArrayList<String> variableNames){
		int id = 0;
		while(variableNames.contains("v" + id)){
			id ++;
		}
		return "v" + id;
	}
	
	private static ArrayList<String> getAllVariableNames(Expression e){
		if(e instanceof And 
				|| e instanceof DoubleImplication 
				|| e instanceof Implication
				|| e instanceof Or
				|| e instanceof Function){
			ArrayList<String> vars = new ArrayList<String>();
			for(Expression sub_e : e.myExpression){
				vars.addAll(getAllVariableNames(sub_e));
			}
			return vars;
		}
		else if(e instanceof ExistentialQuantifier){
			ArrayList<String> vars = new ArrayList<String>();
			vars.add(((ExistentialQuantifier)e).variable.name);
			vars.addAll(getAllVariableNames(e.myExpression.get(0)));
			return vars;
		}
		else if(e instanceof UniversalQuantifier){
			ArrayList<String> vars = new ArrayList<String>();
			vars.add(((UniversalQuantifier)e).variable.name);
			vars.addAll(getAllVariableNames(e.myExpression.get(0)));
			return vars;
		}
		else if(e instanceof Variable){
			ArrayList<String> vars = new ArrayList<String>();
			vars.add(((Variable)e).name);
			return vars;
		}
		return null;
	}
	
	private static Expression Standardize(Expression e) {
		ArrayList<String> variableNames = getAllVariableNames(e);
		
		return null;
	}

	private static Expression pushNegationInwards(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression eliminateImplication(Expression e) throws IOException {
		e.myExpression.get(0).isNegated = true;
		return new Or(e.myExpression.get(0), e.myExpression.get(1));
	}

	private static Expression eliminateDoubleImplication(Expression e) throws IOException {
		return new And(new Implication(e.myExpression.get(0), e.myExpression.get(1)),
				new Implication(e.myExpression.get(1), e.myExpression.get(0)));
	}
}

package cnf;

import unification.Expression;

public class CNF {

	public static Expression normalize(Expression E){
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

	private static Expression Standardize(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression pushNegationInwards(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression eliminateImplication(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression eliminateDoubleImplication(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}
}

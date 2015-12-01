package cnf;

import java.io.IOException;

import unification.Expression;

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

	private static Expression Standardize(Expression e) {
		// TODO Auto-generated method stub
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

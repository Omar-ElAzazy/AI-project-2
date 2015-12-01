package cnf;

import java.io.IOException;

import unification.Constant;
import unification.Expression;
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

	private static Expression Standardize(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Expression pushNegationInwards(Expression e) throws IOException {
		if (e instanceof Variable || e instanceof Constant) {
			e.myExpression.get(0).isNegated = !e.myExpression.get(0).isNegated;
			return e;
		} else if (e instanceof And) {
			return new Or(pushNegationInwards(e.myExpression.get(0)),
					pushNegationInwards(e.myExpression.get(1)));
		} else if (e instanceof Or) {
			return new And(pushNegationInwards(e.myExpression.get(0)),
					pushNegationInwards(e.myExpression.get(1)));
		} else if (e instanceof UniversalQuantifier) {
			return new ExistentialQuantifier(((UniversalQuantifier) e).variable,
					pushNegationInwards(e.myExpression.get(0)));
		} else if (e instanceof ExistentialQuantifier) {
			return new UniversalQuantifier(((ExistentialQuantifier) e).variable,
					pushNegationInwards(e.myExpression.get(0)));
		}
		return null;
	}

	private static Expression eliminateImplication(Expression e) throws IOException {
		if (e instanceof Variable || e instanceof Constant) {
			return e;
		}
		else if (e instanceof Implication) {
			e.myExpression.get(0).isNegated = true;
			return new Or(e.myExpression.get(0), e.myExpression.get(1));
		}
		else if (e instanceof And) {
			Expression newLeft = eliminateImplication(e.myExpression.get(0));
			Expression newRight = eliminateImplication(e.myExpression.get(1));
			return new And(newLeft, newRight);
		}
		else if (e instanceof Or) {
			Expression newLeft = eliminateImplication(e.myExpression.get(0));
			Expression newRight = eliminateImplication(e.myExpression.get(1));
			return new Or(newLeft, newRight);
		}
		else if (e instanceof UniversalQuantifier) {
			Expression newExpression = eliminateImplication(e.myExpression.get(0));
			return new UniversalQuantifier(((UniversalQuantifier) e).variable, newExpression);
		}
		else if (e instanceof ExistentialQuantifier) {
			Expression newExpression = eliminateImplication(e.myExpression.get(0));
			return new ExistentialQuantifier(((ExistentialQuantifier) e).variable, newExpression);
		}
		return null;
	}

	private static Expression eliminateDoubleImplication(Expression e) throws IOException {
		if (e instanceof Variable || e instanceof Constant) {
			return e;
		}
		else if (e instanceof DoubleImplication) {
			return new And(new Implication(eliminateDoubleImplication(e.myExpression.get(0)), eliminateDoubleImplication(e.myExpression.get(1))),
					new Implication(eliminateDoubleImplication(e.myExpression.get(1)), eliminateDoubleImplication(e.myExpression.get(0))));
		}
		else if (e instanceof And) {
			Expression newLeft = eliminateDoubleImplication(e.myExpression.get(0));
			Expression newRight = eliminateDoubleImplication(e.myExpression.get(1));
			return new And(newLeft, newRight);
		}
		else if (e instanceof Or) {
			Expression newLeft = eliminateDoubleImplication(e.myExpression.get(0));
			Expression newRight = eliminateDoubleImplication(e.myExpression.get(1));
			return new Or(newLeft, newRight);
		}
		else if (e instanceof Implication) {
			Expression newLeft = eliminateDoubleImplication(e.myExpression.get(0));
			Expression newRight = eliminateDoubleImplication(e.myExpression.get(1));
			return new Implication(newLeft, newRight);
		}
		else if (e instanceof UniversalQuantifier) {
			Expression newExpression = eliminateDoubleImplication(e.myExpression.get(0));
			return new UniversalQuantifier(((UniversalQuantifier) e).variable, newExpression);
		}
		else if (e instanceof ExistentialQuantifier) {
			Expression newExpression = eliminateDoubleImplication(e.myExpression.get(0));
			return new ExistentialQuantifier(((ExistentialQuantifier) e).variable, newExpression);
		}
		return null;
	}
}

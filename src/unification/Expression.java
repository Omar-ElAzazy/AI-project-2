package unification;
import java.util.ArrayList;

public abstract class Expression implements Listifiable {
	public ArrayList<Expression> myExpression;
	public boolean negated = false;

	public Expression() {
		myExpression = new ArrayList<Expression>();
	}

	public void add(Expression e) {
		myExpression.add(e);
	}

	public ArrayList<Expression> listify() {
		return myExpression;
	}

	// public boolean isVariable() {
	// return myExpression.size() == 1
	// && myExpression.get(0) instanceof Variable;
	// }
	//
	// public boolean isConstant() {
	// return myExpression.size() == 1
	// && myExpression.get(0) instanceof Constant;
	// }

	public boolean equals(Expression e2) {
		if (this == e2) {
			// returns when both are same constant or same variable. Any
			// constant or variable is initialized only once and used multiple
			// times
			return true;
		}

		if (this instanceof Function && e2 instanceof Function) {
			ArrayList<Expression> e1List = ((Function) this).listify();
			ArrayList<Expression> e2List = ((Function) e2).listify();
			if (e1List.size() != e2List.size()) {
				return false;
			}
			for (int i = 0; i < e1List.size(); i++) {
				if (!e1List.get(i).equals(e2List.get(i))) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	// public void substituteOld(HashMap<Variable, Expression> unificationSet) {
	// // TODO test for the condition when two values(Expressions) substitute
	// // one key(Variable)
	// // TODO make sure that subst changes e along the way
	// for (int i = 0; i < myExpression.size(); i++) {
	// if (myExpression.get(i) instanceof Constant) {
	// continue;
	// }
	// if (myExpression.get(i) instanceof Variable) {
	// Expression boundTo = unificationSet.get((Variable) myExpression
	// .get(i));
	// // System.out.println("boundTo is " + boundTo);
	// if (boundTo != null) {
	// myExpression.set(i, boundTo);
	// }
	// } else {
	// // Expression is a function
	// myExpression.get(i).substituteOld(unificationSet);
	// }
	// }
	// }

	public boolean contains(Variable var) {
		// System.out.println("Ana d5lt 3shan expression " + this.toString()
		// + " and variable " + var.toString());
		for (Expression e : myExpression) {
			if (e instanceof Constant) {
				continue;
			}
			if (e instanceof Variable) {
				return (Variable) e == var;
			} else {
				// e is a function
				if (e.contains(var)) {
					return true;
				}

			}
		}
		return false;
	}

	// @Override
	// public String toString() {
	// if (this instanceof Variable) {
	// return ((Variable) this).toString();
	// }
	// if (this instanceof Constant) {
	// return ((Constant) this).toString();
	// }
	// // this is a function
	// System.out.println("Ely ana fakro function bs hyz3lni aho "
	// + myExpression.toString());
	// if (myExpression.size() == 1 && myExpression.get(0) instanceof Function)
	// {
	// myExpression = myExpression.get(0).listify();
	// }
	// String res = ((Constant) myExpression.get(0)).toString();
	// res += "(";
	// for (int i = 1; i < myExpression.size(); i++) {
	// res += myExpression.get(i).toString();
	// res += ",";
	// }
	// res = res.substring(0, res.length() - 1) + ")";
	// System.out.println("l2 dah peace");
	// return res;
	// }

	@Override
	public String toString() {
		String res = "";
		for (Expression e : myExpression) {
			if (e instanceof Variable) {
				res += ((Variable) e).toString();
			} else if (e instanceof Constant) {
				res += ((Constant) e).toString();
			} else if (e instanceof Function) {
				res += ((Function) e).toString();
			}
		}
		return res;
	}
}

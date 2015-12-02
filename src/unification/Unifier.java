package unification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Unifier {

	String unificationsStr;

	public Unifier() {
		unificationsStr = "";
	}

	public HashMap<Variable, Expression> unify(Expression e1, Expression e2, boolean traceMode)
			throws IOException {
		return unify(e1.listify(), e2.listify(),
				new HashMap<Variable, Expression>(), traceMode);
	}

	private HashMap<Variable, Expression> unify(ArrayList<Expression> e1,
			ArrayList<Expression> e2,
			HashMap<Variable, Expression> unificationSet, boolean traceMode) throws IOException {
		if (e1.size() == 1 && e1.get(0) instanceof Function) {
			e1 = e1.get(0).listify();
		}
		if (e2.size() == 1 && e2.get(0) instanceof Function) {
			e2 = e2.get(0).listify();
		}
		
		if(traceMode){
			System.out.println("e1 = " + e1.toString());
			System.out.println("e2 = " + e2.toString());
			if (unificationSet != null) {
				System.out.println("meow = " + unificationSet.toString());
			} else {
				System.out.println("meow = null");
			}
		}
		if (unificationSet == null) {
			return null;
		}
		if (equals(e1, e2)) {
			return unificationSet;
		}
		if (Helper.isVariable(e1)) {
			return unifyVar(Helper.parseVariable(e1), e2, unificationSet, traceMode);
		}
		if (Helper.isVariable(e2)) {
			return unifyVar(Helper.parseVariable(e2), e1, unificationSet, traceMode);
		}
		if (Helper.isConstant(e1) || Helper.isConstant(e2)) {
			return null;
		}
		// ArrayList<Expression> e1List = ((Function) e1).listify();
		// ArrayList<Expression> e2List = ((Function) e2).listify();
		if (e1.size() != e2.size()) {
			return null;
		}
		Expression removed1 = e1.remove(0);
		Expression removed2 = e2.remove(0);
		HashMap<Variable, Expression> newUS = unify(removed1.listify(),
				removed2.listify(), unificationSet, traceMode);
		return unify(e1, e2, newUS, traceMode);
	}

	private boolean equals(ArrayList<Expression> e1, ArrayList<Expression> e2) {
		if (e1.size() != e2.size()) {
			return false;
		}
		for (int i = 0; i < e1.size(); i++) {
			if (!e1.get(i).equals(e2.get(i))) {
				return false;
			}
		}
		return true;
	}

	private HashMap<Variable, Expression> unifyVar(Variable var,
			ArrayList<Expression> expList,
			HashMap<Variable, Expression> unificationSet, boolean traceMode) throws IOException {
		if(traceMode){
			System.out.println("Entered unifyVar with variable " + var.toString()
					+ " and expression list " + expList.toString());
		}
		Expression boundTo = unificationSet.get(var);
		if (boundTo != null) {
			if (!(boundTo instanceof Variable && var == (Variable) boundTo)) {
				// ArrayList<Expression> tmp = new ArrayList<Expression>();
				// tmp.add(boundTo);
				// return unify(tmp, expList, unificationSet);
				return unify(boundTo.listify(), expList, unificationSet, traceMode);
			}
		}
		// substituteOld(unificationSet, expList);
		Expression substituted = substitute(unificationSet, expList);
		expList = (substituted).listify();
		if (contains(expList, var)) {
			return null;
		}
		Variable vv = var;
		Expression eee = Helper.parseExpression(expList);
		unificationsStr += eee + " / " + vv + "\t,\t";
		unificationSet.put(vv, eee);
		return unificationSet;

	}

	private Expression substitute(HashMap<Variable, Expression> unificationSet,
			ArrayList<Expression> expList) throws IOException {
		// list of expressions to be substituted is either Constant or Variable
		// or Function
		return substitute(unificationSet, Helper.parseExpression(expList));
	}

	private Expression substitute(HashMap<Variable, Expression> unificationSet,
			Expression expression) throws IOException {
		if (expression instanceof Constant) {
			return expression;
		}
		if (expression instanceof Variable) {
			Expression boundTo = unificationSet.get((Variable) expression);
			if (boundTo == null) {
				return expression;
			} else {
				return boundTo;
			}
		}
		// expression is a function
		ArrayList<Expression> newFunc = expression.listify();
		for (int i = 0; i < newFunc.size(); i++) {
			newFunc.set(i, substitute(unificationSet, newFunc.get(i)));
		}
		return Helper.parseFunction(newFunc);

	}

	private boolean contains(ArrayList<Expression> expList, Variable var) {
		for (Expression e : expList) {
			if (e.contains(var)) {
				return true;
			}
		}
		return false;
	}

	public String getUnifications() {
		return unificationsStr;
	}

	// private void substituteOld(HashMap<Variable, Expression> unificationSet,
	// ArrayList<Expression> expList) {
	// System.out
	// .println("ana d5lt substitute with expression "
	// + expList.toString() + " and meow "
	// + unificationSet.toString());
	// // TODO there is an unhandled case when expression is a function and
	// // there is a substitution for the function as a whole
	// for (int i = 0; i < expList.size(); i++) {
	// expList.get(i).substituteOld(unificationSet);
	// }
	// System.out.println("ana tl3t mn substitute bl expression el gdeed "
	// + expList.toString());
	// }
}

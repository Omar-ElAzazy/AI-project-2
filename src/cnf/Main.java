package cnf;

import java.io.IOException;

import unification.Constant;
import unification.Expression;
import unification.Function;
import unification.Variable;

public class Main {
	
	public static void main(String[] args) throws IOException {
		/*Expression e5 = new Function(new Constant("P"), new Variable("x"));
		e5.negated = true;
		Expression e4 = new Implication(new Function(new Constant("Q"), new Variable("x")), e5);
		Expression e3 = new UniversalQuantifier(new Variable("x"), e4);
		Expression e2 = new And(new Function(new Constant("P"), new Variable("x")), e3);
		Expression e1 = new ExistentialQuantifier(new Variable("x"), e2);*/

		//∀x[P (x) ⇔ (Q(x) ∧ ∃y[Q(y) ∧ R(y, x)])]
		
		Expression e1 = new Function(new Constant("R"), new Variable("y"), new Variable("x"));
		Expression e2 = new Function(new Constant("Q"), new Variable("y"));
		Expression e3 = new And(e2, e1);
		Expression e4 = new ExistentialQuantifier(new Variable("y"), e3);
		Expression e5 = new Function(new Constant("Q"), new Variable("x"));
		Expression e6 = new And(e5, e4);
		Expression e7 = new Function(new Constant("P"), new Variable("x"));
		Expression e8 = new DoubleImplication(e7, e6);
		Expression e9 = new UniversalQuantifier(new Variable("x"), e8);
		
		System.out.println(CNF.clauseForm(e9, true).toString());
	}
}

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
		
		Expression e6 = new Function(new Constant("R"), new Variable("y"), new Variable("x"));
		Expression e5 = new And(new Function(new Constant("Q"), new Variable("x")), e6);
		Expression e4 = new ExistentialQuantifier(new Variable("y"), e5);
		Expression e3 = new And(new Function(new Constant("Q"), new Variable("x")), e4);
		Expression e2 = new DoubleImplication(new Function(new Constant("P"), new Variable("x")), e3);
		Expression e1 = new UniversalQuantifier(new Variable("x"), e2);

		System.out.println(e1.toString());
		System.out.println(CNF.clauseForm(e1, true).toString());
	}
}

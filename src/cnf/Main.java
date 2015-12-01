package cnf;

import java.io.IOException;

import unification.Constant;
import unification.Expression;
import unification.Function;
import unification.Variable;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Expression e5 = new Function(new Constant("P"), new Variable("x"));
		e5.negated = true;
		Expression e4 = new Implication(new Function(new Constant("Q"), new Variable("x")), e5);
		Expression e3 = new UniversalQuantifier(new Variable("x"), e4);
		Expression e2 = new And(new Function(new Constant("P"), new Variable("x")), e3);
		Expression e1 = new ExistentialQuantifier(new Variable("x"), e2);
		
		Expression t1 = CNF.eliminateDoubleImplication(e1);
		Expression t2 = CNF.eliminateImplication(t1);
		Expression t3 = CNF.pushNegationInwards(t2, false);
		System.out.println(CNF.clauseForm(e1).toString());
	}
}

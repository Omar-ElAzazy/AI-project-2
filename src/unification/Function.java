package unification;
import java.io.IOException;

public class Function extends Expression {
	// ArrayList<Expression> myFunction;

	public Function(Constant functionName, Object... args) throws IOException {
		super();
		super.myExpression.add(functionName);
		for (Object l : args) {
			if (!(l instanceof Expression)) {
				throw new IOException(
						"Function parameter must be an expression");
			}
			super.myExpression.add((Expression) l);
		}
	}

	@Override
	public String toString() {
		String res = "";
		res += myExpression.get(0);
		if(myExpression.size() > 1){
			res += "(";
			for (int i = 1; i < myExpression.size(); i++) {
				res += myExpression.get(i).toString();
				res += ",";
			}
			res = res.substring(0, res.length() - 1) + ")";
		}
		if (negated == true) {
			res = "!" + res;
		}
		return res;
	}
	
	@Override
	public Expression deepCopy() throws IOException{
		Function func = new Function(new Constant(((Constant)myExpression.get(0)).name));
		for(int q = 1; q < myExpression.size(); q++){
			func.myExpression.add(myExpression.get(q).deepCopy());
		}
		func.negated = negated;
		return func;
	}
}
package unification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
		String res = "(";
		for (int i = 0; i < myExpression.size(); i++) {
			res += myExpression.get(i).toString();
			res += ",";
		}
		res = res.substring(0, res.length() - 1) + ")";
		return res;
	}
}
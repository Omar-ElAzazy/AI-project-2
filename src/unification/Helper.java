package unification;
import java.io.IOException;
import java.util.ArrayList;

public class Helper {
	public static boolean isVariable(ArrayList<Expression> e) {
		return e.size() == 1 && e.get(0) instanceof Variable;
	}

	public static Variable parseVariable(ArrayList<Expression> e)
			throws IOException {
		if (!isVariable(e)) {
			throw new IOException(
					"Parameter to parseVariable must be a variable");
		}
		return (Variable) e.get(0);
	}

	public static boolean isConstant(ArrayList<Expression> e) {
		return e.size() == 1 && e.get(0) instanceof Constant;
	}

	public static Constant parseConstant(ArrayList<Expression> e)
			throws IOException {
		if (!isConstant(e)) {
			throw new IOException(
					"Parameter to parseConstant must be a constant");
		}
		return (Constant) e.get(0);
	}

	public static boolean isFunction(ArrayList<Expression> e) {
		return e.size() > 1 && e.get(0) instanceof Constant;
	}

	public static Function parseFunction(ArrayList<Expression> e)
			throws IOException {
		if (!isFunction(e)) {
			throw new IOException(
					"Parameter to parseFunction must be a function");
		}
		Constant cons = (Constant) e.remove(0);
		return new Function(cons, e.toArray());
	}

	public static Expression parseExpression(ArrayList<Expression> newExpList)
			throws IOException {
		if (isVariable(newExpList)) {
			return parseVariable(newExpList);
		}
		if (isConstant(newExpList)) {
			return parseConstant(newExpList);
		}
		if (isFunction(newExpList)) {
			return parseFunction(newExpList);
		}
		System.out.println("Not an expression in parseExpression");
		return null;
	}

}

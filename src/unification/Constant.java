package unification;
import java.io.IOException;

public class Constant extends Expression {
	public String name;

	public Constant(String name) throws IOException {
		super();
		this.name = name;
		super.myExpression.add(this);
	}

	@Override
	public String toString() {
		String res = name;
		if (negated == true) {
			res = "!" + res;
		}
		return res;
	}
}
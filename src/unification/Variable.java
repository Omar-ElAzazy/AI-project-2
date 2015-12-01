package unification;
import java.io.IOException;

public class Variable extends Expression {
	char v;

	public Variable(char v) throws IOException {
		if (v > 'z' || v < 'a') {
			throw new IOException("A variable must be lowercase");
		}
		this.v = v;
		super.myExpression.add(this);
	}

	@Override
	public String toString() {
		return v + "";
	}

}

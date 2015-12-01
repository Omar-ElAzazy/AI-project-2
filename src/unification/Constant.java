package unification;
import java.io.IOException;

public class Constant extends Expression {
	char c;

	public Constant(char c) throws IOException {
		super();
		if (c > 'Z' || c < 'A') {
			throw new IOException("A constant must be uppercase");
		}
		this.c = c;
		super.myExpression.add(this);
	}

	@Override
	public String toString() {
		return c + "";
	}
}
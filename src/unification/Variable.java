package unification;
import java.io.IOException;

public class Variable extends Expression {
	public String name;

	public Variable(String name) throws IOException {
		this.name = name;
		if (Character.isUpperCase(this.name.charAt(0))) {
			throw new IOException("A variable must be lowercase");
		}
		super.myExpression.add(this);
	}

	@Override
	public String toString() {
		return name;
	}

}

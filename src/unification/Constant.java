package unification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Constant extends Expression {
	public String name;

	public Constant(String name) throws IOException {
		super();
		this.name = name;
		if (Character.isLowerCase(this.name.charAt(0))) {
			throw new IOException("A constant must be uppercase");
		}
		super.myExpression.add(this);
	}

	@Override
	public String toString() {
		return name;
	}
}
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
		String res = name;
		if (negated == true) {
			res = "!" + res;
		}
		return res;
	}
	
	@Override
	public Expression deepCopy() throws IOException{
		Variable copy = new Variable(new String(name));
		copy.negated = negated;
		return copy;
	}
}

package unification;
import java.io.IOException;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) throws IOException {
		Variable x = new Variable("x");
		Constant P = new Constant("P");
		Constant A = new Constant("A");
		Constant G = new Constant("G");
		Constant H = new Constant("H");
		Constant F = new Constant("F");
		Variable u = new Variable("u");
		Variable v = new Variable("v");
		Variable y = new Variable("y");
		Variable z = new Variable("z");

		Function left1 = new Function(P, x, new Function(G, x), new Function(G,
				new Function(F, A)));
		Function right1 = new Function(P, new Function(F, u), v, v);

		Function left2 = new Function(P, A, y, new Function(F, y));
		Function right2 = new Function(P, z, z, u);

		Function left3 = new Function(F, x, new Function(G, x), x);
		Function right3 = new Function(F, new Function(G, u),
				new Function(G, z), z);

		Function left4 = new Function(F, new Function(G, new Function(H, A)));
		Function right4 = new Function(F, new Function(G, new Function(H, x)));

		Unifier unifier = new Unifier();
		HashMap<Variable, Expression> result = unifier.unify(left1, right1, true);
		if (result == null) {
			System.out.println("Do not unify");
		} else {
			System.out.println("Unify with the following unification(s):\n"
					+ unifier.getUnifications());
		}

		// System.out.println(unifier.unify(new Function(P, x), P));
	}
}

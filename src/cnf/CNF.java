package cnf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import unification.Constant;
import unification.Expression;
import unification.Function;
import unification.Variable;

public class CNF {

	public static Expression clauseForm(Expression E, boolean trace) throws IOException {
		if (trace == true) System.out.println(E.toString());
		E = eliminateDoubleImplication(E); if (trace == true) System.out.println(E.toString());
		E = eliminateImplication(E); if (trace == true) System.out.println(E.toString());
		E = pushNegationInwards(E, false); if (trace == true) System.out.println(E.toString());
		E = Standardize(E); if (trace == true) System.out.println(E.toString());
		E = Skolemize(E); if (trace == true) System.out.println(E.toString());
		E = discardUniversalQuantifiers(E); if (trace == true) System.out.println(E.toString());
		E = translateIntoCNF(E); if (trace == true) System.out.println(E.toString());
		return E;
	}

	public static Expression cleanUp(Expression e) throws IOException {
		if (e instanceof Variable || e instanceof Constant
				|| e instanceof Function) {
			return e;
		} else if (e instanceof UniversalQuantifier
				|| e instanceof ExistentialQuantifier) {
			return cleanUp(e.myExpression.get(0));
		} else if (e instanceof And) {
			return new And(cleanUp(e.myExpression.get(0)),
					cleanUp(e.myExpression.get(1)));
		} else if (e instanceof Or) {
			return new Or(cleanUp(e.myExpression.get(0)),
					cleanUp(e.myExpression.get(1)));
		}
		return null;
	}

	private static Expression applyTranslation(Expression e) throws IOException{
		for(int q = 0; q < e.myExpression.size(); q++){
			if(!(e.myExpression.get(q) instanceof Constant || e.myExpression.get(q) instanceof Variable)){
				e.myExpression.set(q, applyTranslation(e.myExpression.get(q)));
			}
		}
		if(e instanceof Or){
			int indOfAnd = -1;
			if(e.myExpression.get(0) instanceof And){
				indOfAnd = 0;
			}
			else if(e.myExpression.get(1) instanceof And){
				indOfAnd = 1;
			}
			if(indOfAnd != -1){
				Expression and = e.myExpression.get(indOfAnd);
				Expression other = e.myExpression.get((indOfAnd + 1) % 2);
				
				Expression left = new Or(other, and.myExpression.get(0));
				left = applyTranslation(left);
				
				Expression right = new Or(other, and.myExpression.get(1));
				right = applyTranslation(right);
				
				return new And(left, right);
			}
		}
		return e;
	}
	
	private static Expression translateIntoCNF(Expression e) throws IOException {
		e = cleanUp(e);
		return applyTranslation(e);
	}

	public static Expression discardUniversalQuantifiers(Expression e) {
		if (e instanceof Variable || e instanceof Constant
				|| e instanceof Function) {
			return e;
		} else if (e instanceof UniversalQuantifier) {
			((UniversalQuantifier) e).discarded = true;
			discardUniversalQuantifiers(e.myExpression.get(0));
			return e;
		} else if (e instanceof And || e instanceof Or) {
			discardUniversalQuantifiers(e.myExpression.get(0));
			discardUniversalQuantifiers(e.myExpression.get(1));
			return e;
		} else if (e instanceof ExistentialQuantifier) {
			discardUniversalQuantifiers(e.myExpression.get(0));
			return e;
		}
		return null;
	}

	public static Expression applySkolemization(Expression e,
			ArrayList<Variable> scopeVariables,
			ArrayList<String> constantNames, HashMap<String, Function> mapping)
			throws IOException {
		if (e instanceof And) {
			for (Expression sub_e : e.myExpression) {
				if (sub_e instanceof Variable) {
					String variableName = ((Variable) sub_e).name;
					if (mapping.containsKey(variableName)) {
						e.myExpression.set(e.myExpression.indexOf(sub_e),
								mapping.get(variableName));
					}
				} else {
					applySkolemization(sub_e, scopeVariables, constantNames,
							mapping);
				}
			}
			return e;
		} else if (e instanceof DoubleImplication) {
			for (Expression sub_e : e.myExpression) {
				if (sub_e instanceof Variable) {
					String variableName = ((Variable) sub_e).name;
					if (mapping.containsKey(variableName)) {
						e.myExpression.set(e.myExpression.indexOf(sub_e),
								mapping.get(variableName));
					}
				} else {
					applySkolemization(sub_e, scopeVariables, constantNames,
							mapping);
				}
			}
			return e;
		} else if (e instanceof ExistentialQuantifier) {
			String variableName = ((ExistentialQuantifier) e).variable.name;
			Function func = new Function(new Constant(
					getNewConstantName(constantNames) + variableName),
					scopeVariables.toArray());
			mapping.put(variableName, func);
			for (Expression sub_e : e.myExpression) {
				if (sub_e instanceof Variable) {
					String sub_variableName = ((Variable) sub_e).name;
					if (mapping.containsKey(sub_variableName)) {
						e.myExpression.set(e.myExpression.indexOf(sub_e),
								mapping.get(sub_variableName));
					}
				} else {
					applySkolemization(sub_e, scopeVariables, constantNames,
							mapping);
				}
			}
			return e;
		} else if (e instanceof Implication) {
			for (Expression sub_e : e.myExpression) {
				if (sub_e instanceof Variable) {
					String variableName = ((Variable) sub_e).name;
					if (mapping.containsKey(variableName)) {
						e.myExpression.set(e.myExpression.indexOf(sub_e),
								mapping.get(variableName));
					}
				} else {
					applySkolemization(sub_e, scopeVariables, constantNames,
							mapping);
				}
			}
			return e;
		} else if (e instanceof Or) {
			for (Expression sub_e : e.myExpression) {
				if (sub_e instanceof Variable) {
					String variableName = ((Variable) sub_e).name;
					if (mapping.containsKey(variableName)) {
						e.myExpression.set(e.myExpression.indexOf(sub_e),
								mapping.get(variableName));
					}
				} else {
					applySkolemization(sub_e, scopeVariables, constantNames,
							mapping);
				}
			}
			return e;
		} else if (e instanceof UniversalQuantifier) {
			ArrayList<Variable> newScope = new ArrayList<Variable>();
			newScope.add(((UniversalQuantifier) e).variable);
			newScope.addAll(scopeVariables);
			for (Expression sub_e : e.myExpression) {
				if (sub_e instanceof Variable) {
					String variableName = ((Variable) sub_e).name;
					if (mapping.containsKey(variableName)) {
						e.myExpression.set(e.myExpression.indexOf(sub_e),
								mapping.get(variableName));
					}
				} else {
					applySkolemization(sub_e, scopeVariables, constantNames,
							mapping);
				}
			}
			return e;
		} else if (e instanceof Constant) {
			return e;
		} else if (e instanceof Function) {
			for (Expression sub_e : e.myExpression) {
				if (sub_e instanceof Variable) {
					String variableName = ((Variable) sub_e).name;
					if (mapping.containsKey(variableName)) {
						e.myExpression.set(e.myExpression.indexOf(sub_e),
								mapping.get(variableName));
					}
				} else {
					applySkolemization(sub_e, scopeVariables, constantNames,
							mapping);
				}
			}
			return e;
		} else if (e instanceof Variable) {
			return e;
		}
		return null;
	}

	public static Expression Skolemize(Expression e) throws IOException {
		ArrayList<String> constantNames = getAllConstantNames(e);
		Expression result = applySkolemization(e, new ArrayList<Variable>(),
				constantNames, new HashMap<String, Function>());
		ExistentialQuantifier.removed = true;
		return result;
	}

	public static String getNewVariableName(ArrayList<String> variableNames) {
		int id = 0;
		while (variableNames.contains("v" + id)) {
			id++;
		}
		variableNames.add("v" + id);
		return "v" + id;
	}

	public static String getNewConstantName(ArrayList<String> constantNames) {
		int id = 0;
		while (constantNames.contains("f" + id)) {
			id++;
		}
		constantNames.add("f" + id);
		return "f" + id;
	}

	public static ArrayList<String> getAllConstantNames(Expression e) {
		if (e instanceof And) {
			ArrayList<String> cons = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				cons.addAll(getAllConstantNames(sub_e));
			}
			return cons;
		} else if (e instanceof DoubleImplication) {
			ArrayList<String> cons = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				cons.addAll(getAllConstantNames(sub_e));
			}
			return cons;
		} else if (e instanceof ExistentialQuantifier) {
			ArrayList<String> cons = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				cons.addAll(getAllConstantNames(sub_e));
			}
			return cons;
		} else if (e instanceof Implication) {
			ArrayList<String> cons = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				cons.addAll(getAllConstantNames(sub_e));
			}
			return cons;
		} else if (e instanceof Or) {
			ArrayList<String> cons = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				cons.addAll(getAllConstantNames(sub_e));
			}
			return cons;
		} else if (e instanceof UniversalQuantifier) {
			ArrayList<String> cons = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				cons.addAll(getAllConstantNames(sub_e));
			}
			return cons;
		} else if (e instanceof Constant) {
			ArrayList<String> cons = new ArrayList<String>();
			cons.add(((Constant) e).name);
			return cons;
		} else if (e instanceof Function) {
			ArrayList<String> cons = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				cons.addAll(getAllConstantNames(sub_e));
			}
			return cons;
		} else if (e instanceof Variable) {
		}
		return new ArrayList<String>();
	}

	public static ArrayList<String> getAllVariableNames(Expression e) {
		if (e instanceof And || e instanceof DoubleImplication
				|| e instanceof Implication || e instanceof Or
				|| e instanceof Function) {
			ArrayList<String> vars = new ArrayList<String>();
			for (Expression sub_e : e.myExpression) {
				vars.addAll(getAllVariableNames(sub_e));
			}
			return vars;
		} else if (e instanceof ExistentialQuantifier) {
			ArrayList<String> vars = new ArrayList<String>();
			vars.add(((ExistentialQuantifier) e).variable.name);
			vars.addAll(getAllVariableNames(e.myExpression.get(0)));
			return vars;
		} else if (e instanceof UniversalQuantifier) {
			ArrayList<String> vars = new ArrayList<String>();
			vars.add(((UniversalQuantifier) e).variable.name);
			vars.addAll(getAllVariableNames(e.myExpression.get(0)));
			return vars;
		} else if (e instanceof Variable) {
			ArrayList<String> vars = new ArrayList<String>();
			vars.add(((Variable) e).name);
			return vars;
		}
		return new ArrayList<String>();
	}

	/*
	 * if(e instanceof And){
	 * 
	 * } else if(e instanceof DoubleImplication){
	 * 
	 * } else if(e instanceof ExistentialQuantifier){
	 * 
	 * } else if(e instanceof Implication){
	 * 
	 * } else if(e instanceof Or){
	 * 
	 * } else if(e instanceof UniversalQuantifier){
	 * 
	 * } else if(e instanceof Constant){
	 * 
	 * } else if(e instanceof Function){
	 * 
	 * } else if(e instanceof Variable){
	 * 
	 * }
	 */

	public static Expression applyStandardization(Expression e,
			HashMap<String, String> mapping, ArrayList<String> variableNames) {
		if (e instanceof And) {
			for (Expression sub_e : e.myExpression) {
				applyStandardization(sub_e, mapping, variableNames);
			}
			return e;
		} else if (e instanceof DoubleImplication) {
			for (Expression sub_e : e.myExpression) {
				applyStandardization(sub_e, mapping, variableNames);
			}
			return e;
		} else if (e instanceof ExistentialQuantifier) {
			String newName = getNewVariableName(variableNames);
			HashMap<String, String> newmapping = new HashMap<String, String>(mapping);
			newmapping.put(((ExistentialQuantifier) e).variable.name, newName);
			((ExistentialQuantifier) e).variable.name = newName;
			for (Expression sub_e : e.myExpression) {
				applyStandardization(sub_e, newmapping, variableNames);
			}
			return e;
		} else if (e instanceof Implication) {
			for (Expression sub_e : e.myExpression) {
				applyStandardization(sub_e, mapping, variableNames);
			}
			return e;
		} else if (e instanceof Or) {
			for (Expression sub_e : e.myExpression) {
				applyStandardization(sub_e, mapping, variableNames);
			}
			return e;
		} else if (e instanceof UniversalQuantifier) {
			String newName = getNewVariableName(variableNames);
			HashMap<String, String> newmapping = new HashMap<String, String>(mapping);
			newmapping.put(((UniversalQuantifier) e).variable.name, newName);
			((UniversalQuantifier) e).variable.name = newName;
			for (Expression sub_e : e.myExpression) {
				applyStandardization(sub_e, newmapping, variableNames);
			}
			return e;
		} else if (e instanceof Constant) {
			return e;
		} else if (e instanceof Function) {
			for (Expression sub_e : e.myExpression) {
				applyStandardization(sub_e, mapping, variableNames);
			}
			return e;
		} else if (e instanceof Variable) {
			Variable var = (Variable) e;
			var.name = mapping.get(var.name);
			return e;
		}
		return null;
	}

	public static Expression Standardize(Expression e) {
		ArrayList<String> variableNames = getAllVariableNames(e);
		HashMap<String, String> mapping = new HashMap<String, String>();
		return applyStandardization(e, mapping, variableNames);
	}

	public static Expression pushNegationInwards(Expression e, boolean neg)
			throws IOException {
		if (e instanceof Variable || e instanceof Constant
				|| e instanceof Function) {
			if (neg == true) {
				e.myExpression.get(0).negated = !e.myExpression.get(0).negated;
			}
			return e;
		} else if (e instanceof And) {
			if (neg != e.negated) {
				return new Or(pushNegationInwards(e.myExpression.get(0), true),
						pushNegationInwards(e.myExpression.get(1), true));
			} else {
				return new And(pushNegationInwards(e.myExpression.get(0), false),
						pushNegationInwards(e.myExpression.get(1), false));
			}
		} else if (e instanceof Or) {
			if (neg != e.negated) {
				return new And(pushNegationInwards(e.myExpression.get(0), true),
						pushNegationInwards(e.myExpression.get(1), true));
			} else {
				return new Or(pushNegationInwards(e.myExpression.get(0), false),
						pushNegationInwards(e.myExpression.get(1), false));
			}
			
		} else if (e instanceof UniversalQuantifier) {
			if (neg != e.negated) {
				return new ExistentialQuantifier(
						((UniversalQuantifier) e).variable,
						pushNegationInwards(e.myExpression.get(0), true));
			} else {
				return new UniversalQuantifier(
						((UniversalQuantifier) e).variable,
						pushNegationInwards(e.myExpression.get(0), false));
			}
		} else if (e instanceof ExistentialQuantifier) {
			if (neg != e.negated) {
				return new UniversalQuantifier(
						((ExistentialQuantifier) e).variable,
						pushNegationInwards(e.myExpression.get(0), true));
			} else {
				return new ExistentialQuantifier(
						((ExistentialQuantifier) e).variable,
						pushNegationInwards(e.myExpression.get(0), false));
			}
		}
		return null;
	}

	public static Expression eliminateImplication(Expression e)
			throws IOException {
		if (e instanceof Variable || e instanceof Constant
				|| e instanceof Function) {
			return e;
		} else if (e instanceof Implication) {
			e.myExpression.get(0).negated = true;
			return new Or(e.myExpression.get(0), e.myExpression.get(1));
		} else if (e instanceof And) {
			Expression newLeft = eliminateImplication(e.myExpression.get(0));
			Expression newRight = eliminateImplication(e.myExpression.get(1));
			return new And(newLeft, newRight);
		} else if (e instanceof Or) {
			Expression newLeft = eliminateImplication(e.myExpression.get(0));
			Expression newRight = eliminateImplication(e.myExpression.get(1));
			return new Or(newLeft, newRight);
		} else if (e instanceof UniversalQuantifier) {
			Expression newExpression = eliminateImplication(e.myExpression
					.get(0));
			return new UniversalQuantifier(((UniversalQuantifier) e).variable,
					newExpression);
		} else if (e instanceof ExistentialQuantifier) {
			Expression newExpression = eliminateImplication(e.myExpression
					.get(0));
			return new ExistentialQuantifier(
					((ExistentialQuantifier) e).variable, newExpression);
		}
		return null;
	}

	public static Expression eliminateDoubleImplication(Expression e)
			throws IOException {
		if (e instanceof Variable || e instanceof Constant
				|| e instanceof Function) {
			return e;
		} else if (e instanceof DoubleImplication) {
			return new And(new Implication(
					eliminateDoubleImplication(e.myExpression.get(0)),
					eliminateDoubleImplication(e.myExpression.get(1))),
					new Implication(eliminateDoubleImplication(e.myExpression
							.get(1)), eliminateDoubleImplication(e.myExpression
							.get(0))));
		} else if (e instanceof And) {
			Expression newLeft = eliminateDoubleImplication(e.myExpression
					.get(0));
			Expression newRight = eliminateDoubleImplication(e.myExpression
					.get(1));
			return new And(newLeft, newRight);
		} else if (e instanceof Or) {
			Expression newLeft = eliminateDoubleImplication(e.myExpression
					.get(0));
			Expression newRight = eliminateDoubleImplication(e.myExpression
					.get(1));
			return new Or(newLeft, newRight);
		} else if (e instanceof Implication) {
			Expression newLeft = eliminateDoubleImplication(e.myExpression
					.get(0));
			Expression newRight = eliminateDoubleImplication(e.myExpression
					.get(1));
			return new Implication(newLeft, newRight);
		} else if (e instanceof UniversalQuantifier) {
			Expression newExpression = eliminateDoubleImplication(e.myExpression
					.get(0));
			return new UniversalQuantifier(((UniversalQuantifier) e).variable,
					newExpression);
		} else if (e instanceof ExistentialQuantifier) {
			Expression newExpression = eliminateDoubleImplication(e.myExpression
					.get(0));
			return new ExistentialQuantifier(
					((ExistentialQuantifier) e).variable, newExpression);
		}
		return null;
	}
}

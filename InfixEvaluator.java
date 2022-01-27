package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class InfixEvaluator extends Evaluator {
		
	/**
	* These class variables denote the stacks of operators and operands (numbers)
	* that are created for the evaluation.
	*/
	private LinkedStack<String> operators = new LinkedStack<String>();
	private LinkedStack<Integer> operands = new LinkedStack<Integer>();

	public LinkedStack<String> getOperatorStack() {
		return operators;
	}

	public LinkedStack<Integer> getOperandStack() {
		return operands;
	}

	/**
	 * This method performs a single step of the evalutation to be used
	 * in the total evaluation.
	 */
	public void evaluate_step(String token) throws Exception {
		if (isOperand(token)) { //if the current c
			operands.push(Integer.parseInt(token));
		} else {
			/*
			 * TODO: What do we do if the token is an operator? If the expression is
			 * invalid, make sure you throw an exception with the correct message.
			 * 
			 * You can call precedence(token) to get the precedence value of an operator.
			 * It's already defined in the Evaluator class.
			 */
			if (token.equals("(")) {
				operators.push(token);
			} else if (((token.equals("!")) || (token.equals("*")) || (token.equals("/")) || (token.equals("+"))
					|| (token.equals("-")))
					&& ((operators.isEmpty()) || (precedence(token) > precedence(operators.top())))) {
				operators.push(token);
			} else if (token.equals(")")) {
				while ((operators != null) && (!operators.top().equals("("))) {
					process_operator();
				}
				if ((!operators.top().equals("(")) || (operators.isEmpty())) {
					throw new Exception("missing (");
				} else {
					operators.pop();
				}
			} else if ((operators.isEmpty()) || (precedence(operators.top()) < precedence(token))) {
				operators.push(token);
			} else {
				while ((operators.top() != null) && (precedence(token) <= precedence(operators.top()))) {
					process_operator();
				}
				operators.push(token);
			}
		}
	}

	/**
	 * This method evaluates an infix expression (defined by expr) and returns the
	 * evaluation result. It throws an Exception object if the infix expression is
	 * invalid.
	 */
	public Integer evaluate(String expr) throws Exception {

		for (String token : ArithParser.parse(expr)) {
			evaluate_step(token);
		}

		/* TODO: what do we do after all tokens have been processed? */
		while (!operators.isEmpty()) {
			process_operator();
		}

		// The operand stack should have exactly one operand after the evaluation is
		// done
		if (operands.size() > 1) {
			throw new Exception("too many operands");
		} else if (operands.size() < 1) {
			throw new Exception("too few operands");
		}

		return operands.pop();
	}

	public void process_operator() throws Exception {
		String operator = operators.pop();
		if (operator.equals("!")) {
			Integer firstOperand = operands.pop();
			if (firstOperand == null) {
				throw new Exception("too few operands");
			}
			firstOperand = firstOperand * -1;
			operands.push(firstOperand);
		} else if ((operator.equals("*")) || (operator.equals("/"))) {
			if (operator.equals("*")) {
				Integer firstOperand = operands.pop();
				Integer secondOperand = operands.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				}
				Integer answer = firstOperand * secondOperand;
				operands.push(answer);
			}
			if (operator.equals("/")) {
				Integer firstOperand = operands.pop();
				Integer secondOperand = operands.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				} else if (firstOperand == 0) {
					throw new Exception("division by zero");
				}
				Integer answer = secondOperand / firstOperand;
				operands.push(answer);
			}
		} else if ((operator.equals("-")) || (operator.equals("+"))) {
			if (operator.equals("-")) {
				Integer firstOperand = operands.pop();
				Integer secondOperand = operands.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				}
				Integer answer = secondOperand - firstOperand;
				operands.push(answer);
			}
			if (operator.equals("+")) {
				Integer firstOperand = operands.pop();
				Integer secondOperand = operands.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				}
				Integer answer = secondOperand + firstOperand;
				operands.push(answer);
			}
		} else if (!operator.contentEquals("(")) {
			throw new Exception("invalid operator");
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new InfixEvaluator().evaluate("5+(5+2*(5+9))/!8"));
	}

}

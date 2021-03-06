package evaluator;

import parser.ArithmeticParser;
import stack.LLStack;

public class Postfix_Expression_Solver extends Evaluator {

	private LLStack<Integer> stack = new LLStack<Integer>(); //This stack holds all the operands in the expression.
	
	public LLStack<Integer> getStack() {
		return stack;
	}

	/**
	 * This method evaluates a single step of the input expression.
	 */
	public void single_step(String token) throws Exception {
		if (isOperand(token)) { //If the current token in the parsed expression is a number, it is pushed into the stack member.
			stack.push(Integer.parseInt(token));
		} else {
			/*
			 * If the current token is an operator, its operation is performed on the next operand(s) in the stack.
			 * Each type of operator is handled in order of which is pushed first as is standard in postfix expressions.
			 */
			if (token.equals("!")) { //! is used to represent a negative sign in the input expression.
				Integer firstOperand = stack.pop();
				if (firstOperand == null) {
					throw new Exception("too few operands");
				}
				firstOperand = firstOperand * -1;
				stack.push(firstOperand);
			} else if (token.equals("*")) {
				Integer firstOperand = stack.pop();
				Integer secondOperand = stack.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				}
				Integer answer = firstOperand * secondOperand;
				stack.push(answer);
			} else if (token.equals("/")) {
				Integer firstOperand = stack.pop();
				Integer secondOperand = stack.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				} else if (firstOperand == 0) {
					throw new Exception("division by zero");
				}
				Integer answer = secondOperand / firstOperand;
				stack.push(answer);
			} else if (token.equals("+")) {
				Integer firstOperand = stack.pop();
				Integer secondOperand = stack.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				}
				Integer answer = firstOperand + secondOperand;
				stack.push(answer);
			} else if (token.equals("-")) {
				Integer firstOperand = stack.pop();
				Integer secondOperand = stack.pop();
				if ((firstOperand == null) || (secondOperand == null)) {
					throw new Exception("too few operands");
				}
				Integer answer = secondOperand - firstOperand;
				stack.push(answer);
			} else {
				throw new Exception("invalid operator");
			}
		}
	}

	/**
	 * This method uses single_step to evaluate an entire postfix-notation expression.
	 */
	public Integer evaluate(String expr) throws Exception {
		for (String token : ArithmeticParser.parseExpression(expr)) { //All individual operations in the parsed input expression are handled one at a time.
			single_step(token);
		}
		// After evaluation, there should be a single operand left, which will be the result of evaluating the entire expression.
		if (stack.size() > 1) {
			throw new Exception("too many operands");
		} else if (stack.size() < 1) {
			throw new Exception("too few operands");
		}
		return stack.pop();
	}

	// This main method can be used for testing by typing a postfix expression between the quotes.
	public static void main(String[] args) throws Exception {
		System.out.println(new Postfix_Expression_Solver().evaluate("50 25 ! / 3 +"));
	}
}

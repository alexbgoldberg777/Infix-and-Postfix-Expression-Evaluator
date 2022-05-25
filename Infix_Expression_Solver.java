package evaluator;

import parser.ArithmeticParser;
import stack.LLStack;

public class Infix_Expression_Solver extends Evaluator {
		
	/**
	* These class variables denote the stacks of operators and operands (numbers)
	* that are created for the evaluation.
	*/
	private LLStack<String> operators = new LLStack<String>();
	private LLStack<Integer> operands = new LLStack<Integer>();

	public LLStack<String> getOperatorStack() {
		return operators;
	}

	public LLStack<Integer> getOperandStack() {
		return operands;
	}

	/**
	 * This method performs a single step of the evalutation to be used
	 * in the total evaluation.
	 */
	public void single_step(String token) throws Exception {
		if (isOperand(token)) { //If the current token in the parsed expressionis a number, it immediately gets pushed to its stack
			operands.push(Integer.parseInt(token));
		} else {
			/*
			* If the current token is an operator, it is handled differently for each operator and its priority.
			 */
			if (token.equals("(")) { //Starting parentheses are immediately pushed.
				operators.push(token);
			} else if (((token.equals("!")) || (token.equals("*")) || (token.equals("/")) || (token.equals("+"))
					|| (token.equals("-")))
					&& ((operators.isEmpty()) || (precedence(token) > precedence(operators.top())))) {
				/* 
				* Operators on numbers are pushed if they are of a higher precedence than the current top
				* of the operators stack. If they are of lower precedence, the current top must be handled
				* first.
				*/
				operators.push(token);
			} else if (token.equals(")")) { //Ending parentheses signal that the current stack must be evaluated until the starting parentheses.
				while ((operators != null) && (!operators.top().equals("("))) {
					process_operator();
				}
				if ((!operators.top().equals("(")) || (operators.isEmpty())) { //This exception is triggered if there is a missing open parenthesis.
					throw new Exception("missing (");
				} else { //If the opening parenthesis is present, it is then removed.
					operators.pop();
				}
			} else { //If any operators are left that are of lower precedence than the current top, the current top must be processed first to preserve order.
				while ((operators.top() != null) && (precedence(token) <= precedence(operators.top()))) {
					process_operator();
				}
				operators.push(token);
			}
		}
	}
	
	/*
	After the order of steps is found using single_step, this method evaluates the results of each individual operation.
	*/
	public void process_operator() throws Exception {
		String operator = operators.pop();
		if (operator.equals("!")) { //! represents a negative sign in the input expression, and it has the highest precedence.
			Integer firstOperand = operands.pop();
			if (firstOperand == null) {
				throw new Exception("too few operands");
			}
			firstOperand = firstOperand * -1;
			operands.push(firstOperand);
		} else if ((operator.equals("*")) || (operator.equals("/"))) { //Next, multiplication and division are evaluated as they have the next highest precedence.
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
		} else if ((operator.equals("-")) || (operator.equals("+"))) { //Addition and subtraction are evaluated last with the lowest precedence.
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
		} else if (!operator.contentEquals("(")) { //If a starting parenthesis was never met with an ending one in an invalid expression,
			throw new Exception("invalid operator"); //an exception is thrown.
		}
	}

	/**
	 * This method evaluates the entire expression using multiple calls of single_step and process_operator.
	 */
	public Integer evaluate(String expr) throws Exception {

		for (String token : ArithmeticParser.parseExpression(expr)) { //The expression is parsed and its order is decided.
			single_step(token);
		}

		while (!operators.isEmpty()) { //After all tokens have been properly ordered, the individual operations are performed.
			process_operator();
		}

		//After the expression is fully evaluated, the last remaining operand is the final answer.
		if (operands.size() > 1) {
			throw new Exception("too many operands");
		} else if (operands.size() < 1) {
			throw new Exception("too few operands");
		}

		return operands.pop();
	}

	//This main method can be used to test and evaluate an example expression by typing an infix expression between the quotes.
	public static void main(String[] args) throws Exception {
		System.out.println(new Infix_Expression_Solver().evaluate(""));
	}

}

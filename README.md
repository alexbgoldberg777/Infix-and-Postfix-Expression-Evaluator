# Infix-and-Postfix-Expression-Evaluator
Given an expression in infix or postfix notation, this program can evaluate that expression utilizing a stack structure. Evaluator.java is the base form that is extended in InfixEvaluator.java and PostfixEvaluator.java to implement each specific expression notation. LLNode.java creates a linked list node structure, which is then used in LinkedStack.java to create a modified stack data structure that allows for unbounded size; this linked stack structure is the main method by which the evaluators use to decifer the correct order of operations by which to solve the expressions. ArithParser.java is used to parse an input expression string into individual operators and operands to be used by the evaluators. (Note that "!" must be used to denote a negative sign in an input expression.)


**Dependencies**

ArithmeticParser.java: java.util.regex.Matcher, java.util.regex.Pattern \
LLNode.java: N/A \
LinkedStack.java: LLNode.java \
Evaluator.java: N/A \
InfixEvaluator.java: ArithParser.java, LinkedStack.java, Evaluator.java \
PostfixEvaluator.java: ArithParser.java, LinkedStack.java, Evaluator.java



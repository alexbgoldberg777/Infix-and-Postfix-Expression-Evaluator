package evaluator;

/** This base class is extended by the infix and postfix evalutors. */
public abstract class Evaluator {

  /** This method checks if a given string is an operand or not.
   *  It is used to check a character in the evaluators' input string.
   */
  protected static boolean isOperand(String token) {
    try {
      Integer.parseInt(token);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /** This method returns the precedence of an operator so that the
  * operations in the input expression are solved in order. */
  protected static int precedence(String op) {
    int priority = 1;
    if (op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=") || op.equals("==") || op.equals("!=")) {
      return priority;
    }
    priority++;
    if (op.equals("+") || op.equals("-")) {
      return priority;
    }
    priority++;
    if (op.equals("*") || op.equals("/") || op.equals("%")) {
      return priority;
    }
    priority++;
    if (op.equals("!")) {
      return priority;
    }

    return 0;
  }

  public abstract Integer evaluate(String expr) throws Exception; //extended accordingly to serve each individual evaluator's purpose

}

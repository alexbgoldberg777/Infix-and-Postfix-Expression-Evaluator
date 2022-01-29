package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class is used to parse the input expressions of the evaluators to separate
 * them into their individual components.
 */
public class ArithParser {

  /**
   * Parses the input string
   * @param expr expression
   * @return parsed String.
   */
  public static String[] parse(String expr) {
    // First, the number of tokens, both operators and oprands, is scanned for.
    Pattern pattern = Pattern.compile("-?[0-9]+|[-+*/%?()]|>=|>|==|<=|!=|!");
    Matcher match = pattern.matcher(expr);
    int ntokens = 0;
    while (match.find()) {
      ntokens++;
    }
    /* Next, each individual token is stored in a string array to be used by the
    * evaluators */
    match = pattern.matcher(expr);
    String[] tokens = new String[ntokens];
    ntokens = 0;
    while (match.find()) {
      tokens[ntokens++] = match.group();
    }
    return tokens;
  }
}

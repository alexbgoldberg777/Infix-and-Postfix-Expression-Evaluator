package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * An {@code ArithParser} is a parser for arithmetic expressions.
 */
public class ArithParser {

  /**
   * Parse String.
   * @param expr expression
   * @return parsed String.
   */
  public static String[] parse(String expr) {
    /* first pass: scan for number of tokens */
    Pattern pattern = Pattern.compile("-?[0-9]+|[-+*/%?()]|>=|>|==|<=|!=|!");
    Matcher match = pattern.matcher(expr);
    int ntokens = 0;
    while (match.find()) {
      ntokens++;
    }
    // second pass: store tokens into String array
    match = pattern.matcher(expr);
    String[] tokens = new String[ntokens];
    ntokens = 0;
    while (match.find()) {
      tokens[ntokens++] = match.group();
    }
    return tokens;
  }
}

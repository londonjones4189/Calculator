
package calculator;

import java.util.ArrayList;

import static java.lang.Character.isDigit;

/**
 * Represents a simple calculator, takes in an input and computes the arithemtic
 * expresion.
 */
public class SimpleCalculator extends AbstractCalculator implements Calculator {
  private final String result;
  private final ArrayList<String> history;
  private final boolean isResult;

  /**
   * Constructs a simple calculator, to default with an empty history and empty result.
   */
  public SimpleCalculator() {
    this.result = "";
    this.history = new ArrayList<>();
    this.isResult = false;
  }

  /**
   * Constructs a simple calculator and stores information on the calculator.
   *
   * @param result   the result of the arithmetic equation as a string.
   * @param history  the history of inputs as an ArrayList.
   * @param isResult determines whether the string value was a product of an '=', input.
   */
  private SimpleCalculator(String result, ArrayList<String> history, boolean isResult) {
    this.result = result;
    this.history = new ArrayList<>(history);
    this.isResult = isResult;
  }


  /**
   * Creates a calculator object based on the input.
   *
   * @param inpt a single char.
   * @return a new calculator object.
   */
  @Override
  public Calculator input(char inpt) {
    ArrayList<String> newHistory = new ArrayList<>(this.history);
    String newResult = this.result;
    boolean newIsResult = this.isResult;
    if (!(isDigit(inpt) || isOperator(String.valueOf(inpt)) || inpt == '=' || inpt == 'C')) {
      throw new IllegalArgumentException("Not a valid input");
    } else if (newHistory.isEmpty() && isOperator(String.valueOf(inpt))) {
      throw new IllegalArgumentException("Can't have operator before a digit");
    } else if (inpt == 'C') {
      newResult = "";
      newHistory.clear();
    } else if (inpt == '=') {
      newResult = solve(newHistory);
      newHistory.clear();
      newHistory.add(newResult);
      newIsResult = true;
    } else if (isDigit(inpt)) {
      if (newIsResult) {
        newHistory.clear();
        newHistory.add(String.valueOf(inpt));
        newIsResult = false;
      } else if (newHistory.isEmpty() || isOperator(newHistory.get(newHistory.size() - 1))) {
        newHistory.add(String.valueOf(inpt));
      } else {
        String lastIndex = newHistory.get(newHistory.size() - 1);
        isOverflow(lastIndex, String.valueOf(inpt));
        newHistory.set(newHistory.size() - 1, lastIndex + inpt);
      }
    } else if (isOperator(String.valueOf(inpt))) {
      if (newIsResult) {
        newHistory.add(String.valueOf(inpt));
        newIsResult = false;
      } else if (!newHistory.isEmpty() &&
              isDigit(newHistory.get(newHistory.size() - 1).charAt(0))) {
        newResult = solve(newHistory);
        newHistory.clear();
        newHistory.add(newResult);
        newHistory.add(String.valueOf(inpt));
      } else if (newHistory.isEmpty()) {
        throw new IllegalArgumentException("Can't input operator without digit");
      } else {
        throw new IllegalArgumentException("Can't have two operators in a row");
      }
    }

    newResult = updateResultFromHistory(newHistory);
    return new SimpleCalculator(newResult, newHistory, newIsResult);
  }

  /**
   * Gets the result of a simple calculator object.
   *
   * @return the result of a simple calculator object.
   */
  public String getResult() {
    return result;
  }
}
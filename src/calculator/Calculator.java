
package calculator;

/**
 * Represents a single calculator. Returns a new
 * calculator upon input and gets the arithemtic result of calculator.
 */
public interface Calculator {
  /**
   * Creates a calculator object based on the input with
   * updated result.
   *
   * @param inpt a single char
   * @return a SimpleCalculator
   */
  Calculator input(char inpt);

  /**
   * Gets the arithmetic result of a calculator.
   *
   * @return the current result of the calculator as a String.
   */
  String getResult();
}

package calculator;

import java.util.ArrayList;

import static java.lang.Character.isDigit;

/**
 * Represents smart version of calculator, additional features
 * compared to simple calcultor. Stores information on the calculator
 * including memory, result, isResult, lastOp and lastNum for arithemtic
 * functionality .
 */
public class SmartCalculator extends AbstractCalculator implements Calculator {
  private final String result;
  private final ArrayList<String> history;
  private final boolean isResult;
  private final String lastOp;
  private final String lastNum;

  /**
   * Constructor represents the default smart calculator. This calculator is one
   * that was either cleared or has never received input.
   */
  public SmartCalculator() {
    this.result = "";
    this.history = new ArrayList<>();
    this.isResult = false;
    this.lastOp = "";
    this.lastNum = "";
  }

  /**
   * Constructor smart calculator after it has recieved input and stores.
   * information necessary for arithemtic.
   *
   * @param result   represents the result of the arithemtic.
   * @param history  represents the calculator's history.
   * @param isResult represents whether or not a value is to be considered'
   *                 a result (product of = input).
   * @param lastOp  represents the last stored operator in the calculator.
   * @param lastNum represents the last stored number  in the calculator.
   */
  private SmartCalculator(String result, ArrayList<String> history, boolean isResult,
                          String lastOp, String lastNum) {
    this.result = result;
    this.history = new ArrayList<>(history);
    this.isResult = isResult;
    this.lastOp = lastOp;
    this.lastNum = lastNum;
  }

  /**
   * Intakes a char and revaultes the result based on input.
   * Returns a new calcultor with updated fields to reflect arithemtic.
   *
   * @param inpt a single char which can be either be a digit, C, =, or an operator.
   * @return new smart calculator with updated fields.
   */
  @Override
  public Calculator input(char inpt) {
    ArrayList<String> newHistory = new ArrayList<>(this.history);
    String newResult = this.result;
    boolean newIsResult = this.isResult;
    String newLastOp = this.lastOp;
    String newLastNum = this.lastNum;
    SmartCalculator newCalculator = new SmartCalculator(newResult, newHistory, newIsResult,
            newLastOp, newLastNum);

    if (!(isDigit(inpt) || isOperator(String.valueOf(inpt)) || inpt == '=' || inpt == 'C')) {
      throw new IllegalArgumentException("Not a valid input");
    } else if (inpt == 'C') {
      newCalculator = new SmartCalculator("", new ArrayList<>(), false, "",
              "");
    } else if (inpt == '=') {
      newCalculator = caseEqual(newResult, newHistory, newIsResult, newLastOp, newLastNum);

    } else if (isDigit(inpt)) {
      newLastNum = String.valueOf(inpt); // have to do something to make nums work for double digits
      newCalculator = caseDigit(String.valueOf(inpt), newResult, newHistory, newIsResult,
              newLastOp, newLastNum);
      newLastOp = newHistory.get(newHistory.size() - 1);
    } else {
      newLastOp = String.valueOf(inpt);
      newCalculator = caseOperator(String.valueOf(inpt), newResult, newHistory, newIsResult,
              newLastOp, newLastNum);

    }

    return newCalculator;
  }

  //Intakes the fields of the most current calculator and most recent inpt, an equal sign.
  //@returns a new calculator with updated arithmetic.
  private SmartCalculator caseEqual(String result, ArrayList<String> history, boolean isResult,
                                    String lastOp, String lastNum) {
    if (lastOp.equals("")) {
      lastOp = String.valueOf('+');
    }
    if (lastNum.equals("")) {
      lastNum = String.valueOf('0');
    }
    if (history.isEmpty() || !containsDigit(history)) {
      throw new IllegalArgumentException("Can't put an equal into empty calculator");
    }
    if (isResult) {
      history.add(lastOp);
      history.add(lastNum);
    } else if (isOperator(history.get(history.size() - 1))) {
      ArrayList<String> tempHistory = new ArrayList<>(history);
      lastOp = tempHistory.get(history.size() - 1);
      tempHistory.remove(tempHistory.size() - 1);
      String tempResult = solve(tempHistory);
      tempHistory.clear();
      tempHistory.add(tempResult);
      lastNum = firstDigit(tempHistory);
      tempHistory.add(lastOp);
      tempHistory.add(lastNum);
      history = tempHistory;
      result = tempResult;
    }

    result = solve(history);
    history.clear();
    history.add(result);
    isResult = true;

    return new SmartCalculator(result, history, isResult, lastOp, lastNum);
  }


  //Intakes the fields of the most current calculator and most recent inpt, an equal sign.
  //@returns a new calculator with updated arithmetic.
  private SmartCalculator caseDigit(String x, String result, ArrayList<String> history,
                                    boolean isResult, String lastOp, String lastNum) {
    if (isResult) {
      history.clear();
      history.add(String.valueOf(x));
      isResult = false;
    } else if (history.isEmpty() || isOperator(history.get(history.size() - 1))) {
      history.add(String.valueOf(x));
    } else {
      String lastIndex = history.get(history.size() - 1);
      isOverflow(lastIndex, String.valueOf(x));
      history.set(history.size() - 1, lastIndex + x);
      lastNum = history.get(history.size() - 1);
    }
    result = updateResultFromHistory(history);
    return new SmartCalculator(result, history, isResult, lastOp, lastNum);
  }

  //Intakes the fields of the most current calculator and most recent inpt, an operator.
  //@returns a new calculator with updated arithmetic.
  private SmartCalculator caseOperator(String x, String result, ArrayList<String> history,
                                       boolean isResult, String lastOp, String lastNum) {
    if (history.isEmpty() && !x.equals("+")) {
      throw new IllegalArgumentException("Can't start with an operator other than '+'");
    }
    if (history.isEmpty() && x.equals("+")) {
      return new SmartCalculator(result, history, isResult, lastOp, lastNum);
    } else if (isResult) {
      isResult = false;
      history.add(x);
    } else if (isDigit(history.get(history.size() - 1).charAt(0))) {
      result = solve(history);
      history.clear();
      history.add(result);
      history.add(x);
    } else if (isOperator(history.get(history.size() - 1))) {
      history.set(history.size() - 1, x);
    }
    result = updateResultFromHistory(history);
    return new SmartCalculator(result, history, isResult, x, lastNum);
  }

  //Intakes the fields of the most current calculator and most recent inpt, an operator.
  //@returns a new calculator with updated arithmetic.
  private static boolean containsDigit(ArrayList<String> history) {
    for (String s : history) {
      if (isNumeric(s)) {
        return true;
      }
    }
    return false;
  }

  //Intakes the fields of the most current calculator and most recent inpt, an operator.
  //@returns a new calculator with updated arithmetic.
  private static boolean isNumeric(String str) {
    boolean b = false;
    if (str.isEmpty()) {
      b = false;
    }
    for (int i = 0; i < str.length(); i++) {
      if (isDigit(str.charAt(i))) {
        b = true;
      }
    }
    return b;
  }

  //Intakes the fields of the most current calculator and most recent inpt, an operator.
  //@returns a new calculator with updated arithmetic.
  private static String firstDigit(ArrayList<String> history) {
    for (int i = history.size() - 1; i >= 0; i--) {
      if (isNumeric(history.get(i))) {
        return history.get(i);
      }
    }
    return "";
  }

  /**
   * Gets the result of the new smartCalculotr.
   * @return result string equal to arthemtic answer.
   */
  @Override
  public String getResult() {
    return result;
  }
}
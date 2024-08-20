package calculator;

import java.util.ArrayList;

/**
 * The Abstract Calculator serves as a base class for implenting
 * smart and simple calculator.
 */
public abstract class AbstractCalculator implements Calculator {
  protected final String result;
  protected final ArrayList<String> history;
  protected final boolean isResult;

  /**
   * This constructor represents an empty or a default calculator.
   * All the fields are set to those of an empty calcuator-history empty,
   * result empty, and result false since no results have been made.
   */
  public AbstractCalculator() {
    this.result = "";
    this.history = new ArrayList<>();
    this.isResult = false;
  }

  /**
   * This constructor represents a calculator that has
   * taken in inputs. The fields are updated after arithmetic is done.
   *
   * @param result   represents the result of the user's inputs.
   * @param history  represents the history of the user's inputs.
   * @param isResult represnets whether or not a result is an result of an
   *                 arthiemtic equation.
   */
  public AbstractCalculator(String result, ArrayList<String> history, boolean isResult) {
    this.result = result;
    this.history = new ArrayList<>(history);
    this.isResult = isResult;
  }

  protected enum Operator {
    ADD {
      @Override
      int apply(int a, int b) {
        try {
          return Math.addExact(a, b);
        } catch (ArithmeticException e) {
          return 0;
        }
      }
    },
    SUBTRACT {
      @Override
      int apply(int a, int b) {
        try {
          return Math.subtractExact(a, b);
        } catch (ArithmeticException e) {
          return 0;
        }
      }
    },
    MULTIPLY {
      @Override
      int apply(int a, int b) {
        try {
          return Math.multiplyExact(a, b);
        } catch (ArithmeticException e) {
          return 0;
        }
      }
    };

    abstract int apply(int a, int b);

    static Operator whichOne(String op) {
      switch (op) {
        case "+":
          return ADD;
        case "-":
          return SUBTRACT;
        case "*":
          return MULTIPLY;
        default:
          throw new IllegalArgumentException("Invalid operator");
      }
    }
  }

  //Turns an array list into result, represents history gettign turned into history.
  protected String updateResultFromHistory(ArrayList<String> history) {
    StringBuilder builder = new StringBuilder();
    for (String st : history) {
      builder.append(st);
    }
    return builder.toString();
  }

  //Takes in an array list and solves the arthemrtic expression.
  protected String solve(ArrayList<String> history) {
    ArrayList<String> tempHistory = new ArrayList<>(history);
    int soFar = 0;
    while (tempHistory.size() > 1) {
      for (int i = 0; i < tempHistory.size(); i++) {
        if (isOperator(tempHistory.get(i))) {
          int ops1 = Integer.parseInt(tempHistory.get(i - 1));
          int ops2 = Integer.parseInt(tempHistory.get(i + 1));
          Operator op3 = Operator.whichOne(tempHistory.get(i));
          soFar = op3.apply(ops1, ops2);
          tempHistory.set(i - 1, String.valueOf(soFar));
          tempHistory.remove(i);
          tempHistory.remove(i);
          break;
        }
      }
    }
    return updateResultFromHistory(tempHistory);
  }

  //Determines if a string value is an operator.
  protected boolean isOperator(String x) {
    return "+-*".contains(x);
  }

  //Determines if a string is overflow in the calculator.
  protected void isOverflow(String a, String b) {
    String result = a + b;
    if (result.length() > 11 && result.startsWith("-")) {
      throw new IllegalArgumentException("Overflow");
    } else if (result.length() > 10) {
      throw new IllegalArgumentException("Overflow");
    }
  }

  /**
   * Gets result of the calculator, whcih represents the correct artheitmic result.
   * @return string result.
   */
  @Override
  public String getResult() {
    return result;
  }

  /**
   * Returns a new calcutlor based on the input given by user.
   * @param inpt a single char representing inpt of calculator.
   * @return new updated calculator.
   */
  public abstract Calculator input(char inpt);
}

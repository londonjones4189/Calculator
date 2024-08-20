

import static org.junit.Assert.assertEquals;

import org.junit.Test;


import calculator.AbstractCalculatorTest;
import calculator.Calculator;
import calculator.SimpleCalculator;


/**
 * Examples for SimpleCalculatorTest.
 */
public class SimpleCalculatorTest extends AbstractCalculatorTest {

  protected Calculator createCalculator() {
    return new SimpleCalculator();
  }

  /**
   * Helper method to elimate for loop from test method, chain inputs onto a calculator.
   *
   * @param calculator inputted calcultor.
   * @param input      inputted charchter that is to be repeated.
   * @param times      amount of times you want input to be repeated.
   * @return same calcultor with repeated input.
   */
  //asking me to change my tester
  public Calculator chainInput(Calculator calculator, char input, int times) {
    for (int i = 0; i < times; i++) {
      calculator = calculator.input(input);
    }
    return calculator;
  }

  @Test
  public void TestMultiEquals() {
    SimpleCalculator calc = new SimpleCalculator();
    Calculator calc14 = calc.input('9').input('*').input('9').input('=');
    assertEquals("81", calc14.getResult());//can work with negatives
    Calculator calc15 = calc14.input('=').input('=').input('=');
    assertEquals("81", calc15.getResult()); //multiple equal same thing
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestEqualAndOperator() {
    SimpleCalculator calc = new SimpleCalculator();
    calc.input('+').input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestMultiOperator() {
    SimpleCalculator calc = new SimpleCalculator();
    calc.input('1').input('+').input('+');
  }


}









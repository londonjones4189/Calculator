package calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests classes which implement the interface calculator.
 */
public abstract class AbstractCalculatorTest {

  protected abstract Calculator createCalculator();

  protected Calculator chainInput(Calculator calculator, char input, int times) {
    for (int i = 0; i < times; i++) {
      calculator = calculator.input(input);
    }
    return calculator;
  }

  @Test
  public void testInputAddition() {
    Calculator calculator = createCalculator();
    Calculator calc2 = calculator.input('0').input('+').input('1');
    assertEquals("0+1", calc2.getResult());
    Calculator calc3 = calc2.input('=');
    assertEquals("1", calc3.getResult());
    Calculator calc4 = calculator.input('4');
    assertEquals("4", calc4.getResult());
    Calculator calc5 = calc4.input('0').input('+').input('1');
    assertEquals("40+1", calc5.getResult());
    Calculator calc6 = calc5.input('+').input('1');
    assertEquals("41+1", calc6.getResult());
    Calculator calc7 = calc6.input('=');
    assertEquals("42", calc7.getResult());
    Calculator calc8 = calc7.input('+');
    assertEquals("42+", calc8.getResult());
    Calculator calc9 = calc8.input('1').input('=');
    assertEquals("43", calc9.getResult());
    Calculator calc10 = chainInput(calc9, '1', 4);
    assertEquals("1111", calc10.getResult());
    Calculator calc11 = calc10.input('+').input('0');
    assertEquals("1111+0", calc11.getResult());
    Calculator calc12 = calc11.input('=');
    assertEquals("1111", calc12.getResult());
    Calculator calc13 = calc12.input('C').input('0').input('+').input('0');
    assertEquals("0+0", calc13.getResult());
    Calculator calc14 = calc13.input('=');
    assertEquals("0", calc14.getResult());
  }


  @Test
  public void testClear() {
    Calculator calc = createCalculator();
    Calculator calc2 = calc.input('1').input('1');
    assertEquals("11", calc2.getResult());
    Calculator calc3 = calc.input('C');
    assertEquals("", calc3.getResult());

  }

  @Test(expected = IllegalArgumentException.class)
  public void TestNotCaptial() {
    Calculator calculator = createCalculator();
    calculator.input('c');
  }

  @Test
  public void testSubtractOverflow() {
    Calculator calculator = createCalculator();
    Calculator calc2 = calculator.input('0').input('-');
    Calculator calc3 = chainInput(calc2, '1', 10).input('=');
    assertEquals("-1111111111", calc3.getResult());
    // This should return "0" since subtracting would overflow
    Calculator calc4 = calc3.input('-');
    Calculator calc5 = chainInput(calc4, '1', 10).input('=');
    assertEquals("0", calc5.getResult());
  }


  @Test
  public void testMultiplyOverflow() {
    Calculator calculator = createCalculator();
    Calculator calc2 = calculator.input('0').input('+');
    Calculator calc3 = chainInput(calc2, '1', 10).input('=');
    assertEquals("1111111111", calc3.getResult());
    Calculator calc4 = calc3.input('*');
    Calculator calc5 = chainInput(calc4, '1', 10).input('=');
    assertEquals("0", calc5.getResult());
  }

  @Test
  public void testNegativetOverflow() {
    Calculator calculator = createCalculator();
    Calculator calc2 = calculator.input('0').input('-');
    Calculator calc3 = chainInput(calc2, '1', 10).input('=');
    assertEquals("-1111111111", calc3.getResult());
    Calculator calc4 = calc3.input('-');
    Calculator calc5 = chainInput(calc4, '1', 10).input('=');
    assertEquals("0", calc5.getResult());
  }

  @Test
  public void testInputPemdas() {
    Calculator calculator = createCalculator();
    Calculator calc2 = calculator.input('1').input('+').input('3').input('*').input('4')
            .input('-').input('3');
    assertEquals("16-3", calc2.getResult());
    Calculator calc3 = calc2.input('=');
    assertEquals("13", calc3.getResult());//All operations work
    Calculator calc4 = calc3.input('C').input('2').input('2').input('*').input('4')
            .input('*').input('4').input('+').input('4');
    assertEquals("352+4", calc4.getResult());
    Calculator calc5 = calc4.input('=');
    assertEquals("356", calc5.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestInputOverflow() {
    Calculator calculator = createCalculator();
    chainInput(calculator, '1', 11);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestMinusNoHistory() {
    Calculator calculator = createCalculator();
    chainInput(calculator, '-', 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestMultiNoHistory() {
    Calculator calculator = createCalculator();
    chainInput(calculator, '*', 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestAddNoHistory() {
    Calculator calculator = createCalculator();
    calculator.input('+').input('=');
  }


  @Test
  public void testInputSubtraction() {
    Calculator calculator = createCalculator();
    Calculator c2 = calculator.input('5').input('+').input('5').input('+').input('5');
    assertEquals("10+5", c2.getResult());
    Calculator calculator2 = createCalculator();
    Calculator calc2 = calculator2.input('0').input('-').input('1');
    assertEquals("0-1", calc2.getResult()); //Tests subtract from zero
    Calculator calc3 = calc2.input('=');
    assertEquals("-1", calc3.getResult()); //Tests can get negatives
    Calculator calc4 = calc3.input('-').input('1').input('2');
    assertEquals("-1-12", calc4.getResult()); //can get result with mutliple -
    Calculator calc5 = calc4.input('=');
    assertEquals("-13", calc5.getResult());//Can still get a negative
    Calculator calc6 = calc5.input('C');
    assertEquals("", calc6.getResult());// Tests that clear works
    Calculator calc7 = calc6.input('0').input('-').input('1').input('=');
    Calculator calc8 = chainInput(calc7, '2', 4);
    assertEquals("2222", calc8.getResult()); //can't add to a neagtive result
    Calculator calc9 = calc8.input('-').input('9').input('-').input('6').input('-')
            .input('9');
    assertEquals("2207-9", calc9.getResult());
    Calculator calc10 = calc9.input('=');
    assertEquals("2198", calc10.getResult()); //gets correct result with multiple -
    Calculator calc11 = calc10.input('-').input('1');
    assertEquals("2198-1", calc11.getResult());
    Calculator calc12 = calc11.input('=');
    assertEquals("2197", calc12.getResult());
    Calculator calc13 = calc12.input('-').input('0');
    assertEquals("2197-0", calc13.getResult());
    Calculator calc14 = calc13.input('=');
    assertEquals("2197", calc14.getResult());//Stays the same after zero
    Calculator calc15 = calc13.input('C').input('0').input('-').input('0');
    assertEquals("0-0", calc15.getResult());
    Calculator calc16 = calc15.input('=');
    assertEquals("0", calc16.getResult());// Still zero
    Calculator calc17 = chainInput(calc16, '1', 9).input('-');
    assertEquals("111111111-", calc17.getResult());
    Calculator calc18 = calc17.input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('1').input('1')
            .input('=');
    assertEquals("0", calc18.getResult()); //subtract to get zero
  }

  @Test
  public void testInputMultiplication() {
    Calculator calculator = createCalculator();
    Calculator calc2 = calculator.input('0').input('*').input('0');
    assertEquals("0*0", calc2.getResult()); //Tests on both zeros
    Calculator calc3 = calc2.input('=');
    assertEquals("0", calc3.getResult());
    Calculator calc4 = calc3.input('*').input('4');
    assertEquals("0*4", calc4.getResult());
    Calculator calc5 = calc4.input('=');
    assertEquals("0", calc5.getResult()); //Shows that zero remains
    Calculator calc6 = calc5.input('C').input('6').input('*').input('6');
    assertEquals("6*6", calc6.getResult());//With just single digits
    Calculator calc7 = calc6.input('*').input('7');//With multiple *
    assertEquals("36*7", calc7.getResult());
    Calculator calc8 = calc7.input('=');
    assertEquals("252", calc8.getResult());//Works with mutiple
    Calculator calc9 = calc8.input('1');
    assertEquals("1", calc9.getResult());//doesnt let you add something after
    Calculator calc10 = calc9.input('*').input('5').input('=');
    assertEquals("5", calc10.getResult()); //stays same for a one
    Calculator calc11 = calc10.input('*').input('6').input('=');
    assertEquals("30", calc11.getResult());//lets you multiple when result
    Calculator calc12 = calc11.input('*').input('3').input('3')
            .input('=');
    assertEquals("990", calc12.getResult());//works
    Calculator calc13 = calc12.input('-').input('9').input('9').input('9')
            .input('=');
    assertEquals("-9", calc13.getResult());//works
  }


}

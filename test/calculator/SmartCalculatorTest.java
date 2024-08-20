package calculator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Represents test for smartCalculator extending abstarctTests.
 */
public class SmartCalculatorTest extends AbstractCalculatorTest {
  Calculator c;


  @Override
  protected Calculator createCalculator() {
    return new SmartCalculator();
  }

  // could be setting the calculators to weird things
  // test method that inputs each character of string parameter
  @Before
  public void setup() {
    c = new SmartCalculator();
  }

  protected Calculator testInpt(String inputs) {
    for (Character ch : inputs.toCharArray()) {
      c = c.input(ch);
    }
    return c;
  }

  @Test
  public void testAddEmptyHistory() {
    c = testInpt("+10");
    assertEquals("10", c.getResult());
    c = testInpt("+5");
    assertEquals("10+5", c.getResult()); //proving the other plus isn't included
    c = testInpt("C");
    c = testInpt("+5");
    assertEquals("5", c.getResult());
    c = testInpt("C");
    c = testInpt("+");
    assertEquals("", c.getResult());
  }


  @Test
  public void testMultipleEqualsAdd() {
    //postive
    c = testInpt("3+2=");
    assertEquals("5", c.getResult());
    c = testInpt("=");
    assertEquals("7", c.getResult());
    c = testInpt("=");
    assertEquals("9", c.getResult());
    c = testInpt("=");
    assertEquals("11", c.getResult());
    //negative
    c = testInpt("C");
    c = testInpt("10-20=");
    assertEquals("-10", c.getResult());
    c = testInpt("+2=");
    assertEquals("-8", c.getResult());
    c = testInpt("=");
    assertEquals("-6", c.getResult());
  }

  @Test
  public void testPlusFirstOperhand() {
    c = testInpt("3+2="); //Before
    assertEquals("5", c.getResult());
    c = testInpt("C");
    c = testInpt("3+-2=");
    assertEquals("1", c.getResult());
    c = testInpt("C");
    c = testInpt("3+*2=");
    assertEquals("6", c.getResult());
    c = testInpt("C");
    c = testInpt("3++2=");
    assertEquals("5", c.getResult());
  }

  @Test
  public void testMinusFirstOperhand() {
    c = testInpt("3-2="); //Before
    assertEquals("1", c.getResult());
    c = testInpt("C");
    c = testInpt("3-+2=");
    assertEquals("5", c.getResult());
    c = testInpt("C");
    c = testInpt("3-*2=");
    assertEquals("6", c.getResult());
    c = testInpt("C");
    c = testInpt("3--2=");
    assertEquals("1", c.getResult());
  }

  @Test
  public void testMultisFirstOperhand() {
    c = testInpt("3*2="); //Before
    assertEquals("1", c.getResult());
    c = testInpt("C");
    c = testInpt("3-+2=");
    assertEquals("5", c.getResult());
    c = testInpt("C");
    c = testInpt("3-*2=");
    assertEquals("6", c.getResult());
    c = testInpt("C");
    c = testInpt("3--2=");
    assertEquals("1", c.getResult());
  }

  @Test
  public void testMultipleEqualsSubtract() {
    //postive
    c = testInpt("5-2=");
    assertEquals("3", c.getResult());
    c = testInpt("=");
    assertEquals("1", c.getResult());
    //negative
    c = testInpt("=");
    assertEquals("-1", c.getResult());
    c = testInpt("=");
    assertEquals("-3", c.getResult());
    c = testInpt("=");
    assertEquals("-5", c.getResult());
  }

  @Test
  public void testMultipleEqualsMultiply() {
    //postive
    c = testInpt("3*2=");
    assertEquals("6", c.getResult());
    c = testInpt("=");
    assertEquals("12", c.getResult());
    c = testInpt("=");
    assertEquals("24", c.getResult());
    c = testInpt("=");
    assertEquals("48", c.getResult());
    //negative
    c = testInpt("C");
    c = testInpt("10-20=");
    assertEquals("-10", c.getResult());
    c = testInpt("*2=");
    assertEquals("-20", c.getResult());
    c = testInpt("=");
    assertEquals("-40", c.getResult());
    c = testInpt("C");
    c = testInpt("0-1000000000=");
    assertEquals("-1000000000", c.getResult());
    c = testInpt("=");
    assertEquals("-2000000000", c.getResult());
    c = testInpt("=======");
    assertEquals("0", c.getResult()); //overflows here
  }

  @Test
  public void testAddBeforeEquals() {
    c = testInpt("3+");
    assertEquals("3+", c.getResult());
    c = testInpt("=");
    assertEquals("6", c.getResult());
    c = testInpt("=");
    assertEquals("9", c.getResult());
    c = testInpt("=");
    assertEquals("12", c.getResult());
    c = testInpt("C");
    c = testInpt("5+10+5");
    assertEquals("15+5", c.getResult());
    c = testInpt("+=");
    assertEquals("40", c.getResult());
    c = testInpt("=");
    assertEquals("60", c.getResult());
    c = testInpt("+="); //increasing last num
    assertEquals("120", c.getResult());
    c = testInpt("-=");//changign operator
    assertEquals("0", c.getResult());
    c = testInpt("*=");
    assertEquals("0", c.getResult());
    c = testInpt("+5=");
    assertEquals("5", c.getResult());
    c = testInpt("=");
    assertEquals("10", c.getResult());
    c = testInpt("C");
    c = testInpt("1000000000=");
    assertEquals("1000000000", c.getResult());
    c = testInpt("=");
    assertEquals("2000000000", c.getResult());
    c = testInpt("=");
    assertEquals("0", c.getResult()); //overflow happened
    c = testInpt("+5=");
    assertEquals("5", c.getResult());
  }

  @Test
  public void testMultiBeforeEquals() {
    c = testInpt("3*");
    assertEquals("3*", c.getResult());
    c = testInpt("=");
    assertEquals("9", c.getResult());
    c = testInpt("=");
    assertEquals("27", c.getResult());
    c = testInpt("=");
    assertEquals("81", c.getResult());
    c = testInpt("C");
    c = testInpt("5*10*5");
    assertEquals("50*5", c.getResult());
    c = testInpt("*=");
    assertEquals("62500", c.getResult());
    c = testInpt("=");
    assertEquals("15625000", c.getResult());
    c = testInpt("*="); //increasing last num
    assertEquals("0", c.getResult()); //Overflow
    c = testInpt("+5=");
    assertEquals("5", c.getResult());
    c = testInpt("=");
    assertEquals("10", c.getResult());
  }
}






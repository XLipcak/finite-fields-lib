package GaloisFieldsValidityTesting;

import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.exceptions.ElementNotInFieldException;
import muni.fi.gf2n.exceptions.MathArithmeticException;
import muni.fi.gf2n.exceptions.MathIllegalArgumentException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class GF2NTest is used to test computation with elements of Galois Field. It
 * includes testing with constants and testing with values generated randomly.
 * Results of all tests with constant values were computed by NTL library.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class GF2NTest {

    private GF2N gf;
    private Random rn;

    @Before
    public void setUp() {
        gf = new GF2N(4194307l);
        rn = new Random();
    }

    @Test
    public void testAdd() {

        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        assertEquals("Addition with constant values failed.", 201, gf.add(115, 186));
        assertEquals("Addition with constant values failed.", 1, gf.add(95, 94));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        for (int x = 0; x < 1000; x++) {
            long value1 = rn.nextInt(4194303);
            long value2 = gf.add(value1, 0l);
            assertEquals("x + 0 must be x", value1, value2);

            value2 = gf.add(value1, value1);
            assertEquals("x + x must be 0", 0l, value2);

            value1 = rn.nextInt(4194303);
            value2 = rn.nextInt(4194303);
            assertEquals("x + y must be x XOR y", (value1 ^ value2), gf.add(value1, value2));
            assertEquals("Addidion must be commutative.", gf.add(value1, value2), gf.add(value2, value1));
        }

        try {
            gf.add(123456789l, 0l);
            fail("Addition of values greater than field size should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.add(-1l, 0l);
            fail("Addition of values less than zero should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }
    }

    @Test
    public void testSubtract() {

        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        assertEquals("Subtraction with constant values failed.", 168, gf.subtract(67, 235));
        assertEquals("Subtraction with constant values failed.", 21, gf.subtract(136, 157));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        for (int x = 0; x < 1000; x++) {
            long value1 = rn.nextInt(4194303);
            long value2 = gf.subtract(value1, 0l);
            assertEquals("x - 0 must be x", value1, value2);

            value2 = gf.subtract(value1, value1);
            assertEquals("x - x must be 0", 0l, value2);

            value1 = rn.nextInt(4194303);
            value2 = rn.nextInt(4194303);
            assertEquals("x - y must be x XOR y", (value1 ^ value2), gf.subtract(value1, value2));
            assertEquals("Subtraction must be commutative.", gf.subtract(value1, value2), gf.subtract(value2, value1));
        }
        try {
            gf.subtract(123456789l, 0l);
            fail("Subtraction of values greater than field size should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.subtract(-1l, 0l);
            fail("Subtraction of values less than zero should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

    }

    @Test
    public void testMultiply() {

        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        assertEquals("Multiplication with constant values failed.", 92, gf.multiply(63, 61));
        assertEquals("Multiplication with constant values failed.", 26, gf.multiply(113, 63));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        for (int x = 0; x < 1000; x++) {
            long value1 = rn.nextInt(4194303);
            long value2 = gf.multiply(value1, 0l);
            assertEquals("x * 0 must be 0", 0l, value2);

            value2 = gf.multiply(value1, 1l);
            assertEquals("x * 1 must be x", value1, value2);
        }
        try {
            gf.multiply(1, 7654321l);
            fail("Multiplication by values greater than field size should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.multiply(1, -9l);
            fail("Multiplication by values less than zero should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }
    }

    @Test
    public void testDivide() {

        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        assertEquals("Division with constant values failed.", 219, gf.divide(38, 45));
        assertEquals("Division with constant values failed.", 154, gf.divide(141, 194));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        for (int x = 0; x < 1000; x++) {
            long value1 = rn.nextInt(4194302) + 1;
            long value2 = gf.divide(value1, 1l);
            assertEquals("x / 1 must be x", value1, value2);

            value2 = gf.divide(value1, value1);
            assertEquals("x / x must be 1", 1l, value2);
        }
        try {
            gf.divide(1, 7654321l);
            fail("Division by values greater than field size should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.divide(1, -9l);
            fail("Division by values less than zero should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.divide(1, 0l);
            fail("Division by zero should throw MathArithmeticException.");
        } catch (MathArithmeticException ex) {
            //OK
        }

    }

    @Test
    public void testInvert() {

        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        assertEquals("Inversion of constant values failed.", 121, gf.invert(112));
        assertEquals("Inversion of constant values failed.", 120, gf.invert(182));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        long value = gf.invert(1l);
        assertEquals("1^(-1) must be 1", 1l, value);

        for (int x = 0; x < 1000; x++) {
            value = rn.nextInt(4194302) + 1;
            assertEquals("(x^-1)^-1 must be x for positive x", value, gf.invert(gf.invert(value)));
        }

        try {
            gf.invert(7654321l);
            fail("Inverting values greater than field size should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.invert(-9l);
            fail("Inverting values less than zero should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.invert(0l);
            fail("Inverting zero should throw MathArithmeticException."); 
        } catch (MathArithmeticException ex) {
            //OK
        }
    }

    @Test
    public void testPower() {

        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        assertEquals("Power of constant values failed.", 37, gf.power(78, 1000));
        assertEquals("Power of constant values failed.", 102, gf.power(96, 1000));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        for (int x = 0; x < 1000; x++) {
            long value = gf.power(1l, rn.nextInt(1024));
            assertEquals("1^x must be 1 for positive x ", 1l, value);

            value = gf.power(0l, rn.nextInt(1024));
            assertEquals("0 powered to anything must be 0", 0l, value);

            value = gf.power(rn.nextInt(4194303), 0l);
            assertEquals("x^0 must be 1 for positive x", 1l, value);
        }

        try {
            gf.power(7654321l, 1l);
            fail("Exponentiation of values greater than field size should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.power(-9l, 99l);
            fail("Exponentiation of values less than zero should "
                    + "throw ElementNotInFieldException.");
        } catch (ElementNotInFieldException ex) {
            //OK
        }

        try {
            gf.power(1l, -1l);
            fail("Exponentiation to values less than zero should "
                    + "throw MathIllegalArgumentException.");
        } catch (MathIllegalArgumentException ex) {
            //OK
        }

    }

    @Test
    public void testDivideAndMultiply() {

        for (int x = 0; x < 1000; x++) {
            long value1 = rn.nextInt(4194302) + 1;
            long value2 = rn.nextInt(4194302) + 1;
            assertEquals("(x * y) / y must be x", value1, gf.divide(gf.multiply(value1, value2), value2));
            assertEquals("(x / y) * y must be x", value1, gf.multiply(gf.divide(value1, value2), value2));
        }

    }

    @Test
    public void testMultiplyInverse() {

        for (int x = 0; x < 10000; x++) {
            long value = rn.nextInt(4194302) + 1;
            assertEquals("x * x^-1 must be 1 for positive x", 1l, gf.multiply(value, gf.invert(value)));
            assertEquals("x^-1 * x must be 1 for positive x", 1l, gf.multiply(gf.invert(value), value));
        }

    }

    @Test
    public void testIsIrreducible() {
        GF2N galoisField;
        for (int x = 4; x < 1500; x++) {
            galoisField = new GF2N(x);
            if (galoisField.isIrreducible(x)) {
                for (int y = 1; y < (int) Math.pow(2, galoisField.getFieldSize()); y++) {
                    assertEquals("X * Inverse(X) must be 1 in Galois Field with irreducible "
                            + "characteristic reducing polynomial.",
                            1, galoisField.multiply(galoisField.invert(y), y));
                }
            } else {
                try {
                    for (int y = 1; y < (int) Math.pow(2, galoisField.getFieldSize()); y++) {
                        galoisField.invert(y);
                    }
                    fail("Exception should be thrown when computing inverse for"
                            + " some elements in GF without irreducible reducing"
                            + " polynomial.");
                } catch (MathArithmeticException ex) {
                    //OK
                }
            }
        }
    }
}

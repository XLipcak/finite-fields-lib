package GaloisFieldsValidityTesting;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import computation.with.finite.fields.library.GF2N;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class GF2NTest {

    private GF2N gf;

    @Before
    public void setUp() {

        gf = new GF2N(4194307l);

    }

    @Test
    public void testAdd() {

        //basic test
        long value1 = 1234567;
        long value2 = gf.add(value1, 0l);
        assertEquals("x + 0 must be x", value1, value2);
        
        value2 = gf.add(value1, value1);
        assertEquals("x + x must be 0", 0l, value2);

        value1 = 44444l;
        value2 = 774455l;
        assertEquals("x + y must be x XOR y", (value1 ^ value2), gf.add(value1, value2));
        assertEquals("Addidion must be commutative.", gf.add(value1, value2), gf.add(value2, value1));

        try {
            gf.add(123456789l, 0l);
            fail("Addition of values greater than field size should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.add(-1l, 0l);
            fail("Addition of values less than zero should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //TO BE DONE, computing with big GFs and with limit numbers for this fields

    }

    @Test
    public void testSubtract() {

        //basic test
        long value1 = 1234567l;
        long value2 = gf.subtract(value1, 0l);
        assertEquals("x - 0 must be x", value1, value2);

        value2 = gf.subtract(value1, value1);
        assertEquals("x - x must be 0", 0l, value2);

        value1 = 44444l;
        value2 = 774455l;
        assertEquals("x - y must be x XOR y", (value1 ^ value2), gf.subtract(value1, value2));
        assertEquals("Subtraction must be commutative.", gf.subtract(value1, value2), gf.subtract(value2, value1));

        try {
            gf.subtract(123456789l, 0l);
            fail("Subtraction of values greater than field size should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.subtract(-1l, 0l);
            fail("Subtraction of values less than zero should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //TO BE DONE, computing with big GFs and with limit numbers for this fields

    }

    @Test
    public void testMultiply() {

        //basic test
        long value1 = 1234567l;
        long value2 = gf.multiply(value1, 0l);
        assertEquals("x * 0 must be 0", 0l, value2);

        value2 = gf.multiply(value1, 1l);
        assertEquals("x * 1 must be x", value1, value2);

        try {
            gf.multiply(value1, 7654321l);
            fail("Multiplication by values greater than field size should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.multiply(value1, -9l);
            fail("Multiplication by values less than zero should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //TO BE DONE, computing with big GFs and with limit numbers for this fields
    }

    @Test
    public void testDivide() {

        //basic test
        long value1 = 1234567l;
        long value2 = gf.divide(value1, 1l);
        assertEquals("x / 1 must be x", value1, value2);

        value2 = gf.divide(value1, value1);
        assertEquals("x / x must be 1", 1l, value2);

        try {
            gf.divide(value1, 7654321l);
            fail("Division by values greater than field size should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.divide(value1, -9l);
            fail("Division by values less than zero should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.divide(value1, 0l);
            fail("Division by zero should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //TO BE DONE, computing with big GFs and with limit numbers for this fields
    }

    @Test
    public void testInvert() {

        //basic test
        long value = gf.invert(1l);
        assertEquals("1^(-1) must be 1", 1l, value);

        try {
            gf.invert(7654321l);
            fail("Inverting values greater than field size should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.invert(-9l);
            fail("Inverting values less than zero should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.invert(0l);
            fail("Inverting zero should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //TO BE DONE, computing with big GFs and with limit numbers for this fields
    }

    @Test
    public void testPower() {

        //basic test
        long value = gf.power(1l, 999l);
        assertEquals("1^x must be 1 for positive x ", 1l, value);

        value = gf.power(0l, 7777l);
        assertEquals("0 powered to anything must be 0", 0l, value);
        
        value = gf.power(12345l, 0l);
        assertEquals("x^0 must be 1 for positive x", 1l, value);

        try {
            gf.power(7654321l, 1l);
            fail("Exponentiation of values greater than field size should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.power(-9l, 99l);
            fail("Exponentiation of values less than zero should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            gf.power(1l, -1l);
            fail("Exponentiation to values less than zero should "
                    + "throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //TO BE DONE, computing with big GFs and with limit numbers for this fields
    }
    //TO BE DONE, interaction testing of this methods, e.g. a*a^-1 = 1...
}
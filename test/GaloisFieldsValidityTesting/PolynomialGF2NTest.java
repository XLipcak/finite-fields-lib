package GaloisFieldsValidityTesting;

import java.util.Arrays;
import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.Polynomial;
import muni.fi.gf2n.classes.PolynomialGF2N;
import muni.fi.gf2n.exceptions.MathArithmeticException;
import muni.fi.gf2n.exceptions.MathIllegalArgumentException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class PolynomialGF2NTest is used to test computation with polynomials with
 * elements in Galois Field. It includes testing with constants and testing with
 * values generated randomly. Results of all tests with constant values were
 * computed by NTL library.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class PolynomialGF2NTest {

    private GF2N gf;
    private Random rn;

    public PolynomialGF2NTest() {
    }

    @Before
    public void setUp() {
        rn = new Random();
        gf = new GF2N(4194307l);
    }

    @Test
    public void testAdd() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        Polynomial polynomial1 = new Polynomial(3);
        polynomial1.setElement(0, 3);
        polynomial1.setElement(1, 206);
        polynomial1.setElement(2, 60);

        Polynomial polynomial2 = new Polynomial(3);
        polynomial2.setElement(0, 156);
        polynomial2.setElement(1, 68);
        polynomial2.setElement(2, 179);

        Polynomial resultPolynomial = new Polynomial(3);
        resultPolynomial.setElement(0, 159);
        resultPolynomial.setElement(1, 138);
        resultPolynomial.setElement(2, 143);
        assertEquals("Addition of polynomials with constant values failed.",
                resultPolynomial, polyGF.add(polynomial1, polynomial2));

        polynomial1.setElement(0, 239);
        polynomial1.setElement(1, 15);
        polynomial1.setElement(2, 117);

        polynomial2.setElement(0, 246);
        polynomial2.setElement(1, 255);
        polynomial2.setElement(2, 155);

        resultPolynomial.setElement(0, 25);
        resultPolynomial.setElement(1, 240);
        resultPolynomial.setElement(2, 238);
        assertEquals("Addition of polynomials with constant values failed.",
                resultPolynomial, polyGF.add(polynomial1, polynomial2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        polyGF = new PolynomialGF2N(gf);
        for (int x = 0; x < 1000; x++) {
            int length = rn.nextInt(255) + 1;
            polynomial1 = new Polynomial(length, gf.getFieldSize());
            polynomial2 = new Polynomial(length + 3, gf.getFieldSize());
            Polynomial zeroPolynomial = new Polynomial(rn.nextInt(255) + 1);

            assertEquals("Polynomial cannot be changed after addition with zeroPolynomial.",
                    polynomial1, polyGF.add(polynomial1, zeroPolynomial));

            assertEquals("Addidion of two identical polynomials must be zero polynomial.",
                    new Polynomial(), polyGF.add(polynomial1, polynomial1));

            assertEquals("Degree of resultPolynomial after addition is wrong.",
                    polynomial2.getSize(), polyGF.add(polynomial1, polynomial2).getSize());
        }
    }

    @Test
    public void testSubtract() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        Polynomial polynomial1 = new Polynomial(3);
        polynomial1.setElement(0, 101);
        polynomial1.setElement(1, 26);
        polynomial1.setElement(2, 44);

        Polynomial polynomial2 = new Polynomial(3);
        polynomial2.setElement(0, 182);
        polynomial2.setElement(1, 173);
        polynomial2.setElement(2, 125);

        Polynomial resultPolynomial = new Polynomial(3);
        resultPolynomial.setElement(0, 211);
        resultPolynomial.setElement(1, 183);
        resultPolynomial.setElement(2, 81);
        assertEquals("Subtraction of polynomials with constant values failed.",
                resultPolynomial, polyGF.subtract(polynomial1, polynomial2));

        polynomial1.setElement(0, 101);
        polynomial1.setElement(1, 26);
        polynomial1.setElement(2, 44);

        polynomial2.setElement(0, 182);
        polynomial2.setElement(1, 173);
        polynomial2.setElement(2, 125);

        resultPolynomial.setElement(0, 211);
        resultPolynomial.setElement(1, 183);
        resultPolynomial.setElement(2, 81);
        assertEquals("Subtraction of polynomials with constant values failed.",
                resultPolynomial, polyGF.subtract(polynomial1, polynomial2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        polyGF = new PolynomialGF2N(gf);
        for (int x = 0; x < 100; x++) {
            int length = rn.nextInt(255) + 1;
            polynomial1 = new Polynomial(length, gf.getFieldSize());
            polynomial2 = new Polynomial(length + 3, gf.getFieldSize());
            Polynomial zeroPolynomial = new Polynomial(rn.nextInt(255) + 1);

            assertEquals("Polynomial cannot be changed after subtraction of zeroPolynomial.",
                    polynomial1, polyGF.subtract(polynomial1, zeroPolynomial));

            assertEquals("Subtraction of two identical polynomials must be zero polynomial.",
                    zeroPolynomial.clearZeroValues(), polyGF.subtract(polynomial1, polynomial1));

            assertEquals("Degree of resultPolynomial after subtraction is wrong.",
                    polynomial2.getSize(), polyGF.subtract(polynomial1, polynomial2).getSize());
        }
    }

    @Test
    public void testMultiply() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        Polynomial polynomial1 = new Polynomial(3);
        polynomial1.setElement(0, 146);
        polynomial1.setElement(1, 178);
        polynomial1.setElement(2, 12);

        Polynomial polynomial2 = new Polynomial(3);
        polynomial2.setElement(0, 25);
        polynomial2.setElement(1, 128);
        polynomial2.setElement(2, 70);

        Polynomial resultPolynomial = new Polynomial(5);
        resultPolynomial.setElement(0, 141);
        resultPolynomial.setElement(1, 217);
        resultPolynomial.setElement(2, 190);
        resultPolynomial.setElement(3, 162);
        resultPolynomial.setElement(4, 5);
        assertEquals("Multiplication of polynomials with constant values failed.",
                resultPolynomial, polyGF.multiply(polynomial1, polynomial2));

        polynomial1.setElement(0, 244);
        polynomial1.setElement(1, 0);
        polynomial1.setElement(2, 164);

        polynomial2.setElement(0, 220);
        polynomial2.setElement(1, 219);
        polynomial2.setElement(2, 164);

        resultPolynomial.setElement(0, 30);
        resultPolynomial.setElement(1, 228);
        resultPolynomial.setElement(2, 16);
        resultPolynomial.setElement(3, 135);
        resultPolynomial.setElement(4, 230);
        assertEquals("Multiplication of polynomials with constant values failed.",
                resultPolynomial, polyGF.multiply(polynomial1, polynomial2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        polyGF = new PolynomialGF2N(gf);
        for (int x = 0; x < 100; x++) {

            int length1 = rn.nextInt(255) + 1;
            int length2 = rn.nextInt(255) + 1;

            polynomial1 = new Polynomial(length1, gf.getFieldSize());
            polynomial2 = new Polynomial(length2, gf.getFieldSize());

            assertEquals("Length of polynomials after their multiplication is wrong.",
                    length1 + length2 - 1, polyGF.multiply(polynomial1, polynomial2).getSize());

            assertEquals("Highest coefficient value after multiplication is wrong.",
                    gf.multiply(polynomial1.getElement(length1 - 1), polynomial2.getElement(length2 - 1)),
                    polyGF.multiply(polynomial1, polynomial2).getElement(length1 + length2 - 2));

            assertEquals("Lowest coefficient value after multiplication is wrong.",
                    gf.multiply(polynomial1.getElement(0), polynomial2.getElement(0)),
                    polyGF.multiply(polynomial1, polynomial2).getElement(0));

            assertEquals("Polynomial after multiplication with zero must be zeroPolynomial.",
                    new Polynomial(),
                    polyGF.multiply(polynomial1, new Polynomial(length2)));
        }

    }

    @Test
    public void testDivide() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        Polynomial polynomial1 = new Polynomial(6);
        polynomial1.setElement(0, 170);
        polynomial1.setElement(1, 244);
        polynomial1.setElement(2, 105);
        polynomial1.setElement(3, 48);
        polynomial1.setElement(4, 10);
        polynomial1.setElement(5, 32);

        Polynomial polynomial2 = new Polynomial(2);
        polynomial2.setElement(0, 245);
        polynomial2.setElement(1, 69);

        Polynomial resultPolynomial = new Polynomial(5);
        resultPolynomial.setElement(0, 218);
        resultPolynomial.setElement(1, 245);
        resultPolynomial.setElement(2, 45);
        resultPolynomial.setElement(3, 9);
        resultPolynomial.setElement(4, 122);
        assertEquals("Division of polynomials with constant values failed.",
                resultPolynomial, polyGF.divide(polynomial1, polynomial2));

        polynomial1.setElement(0, 65);
        polynomial1.setElement(1, 166);
        polynomial1.setElement(2, 51);
        polynomial1.setElement(3, 250);
        polynomial1.setElement(4, 27);
        polynomial1.setElement(5, 202);

        polynomial2.setSize(3);
        polynomial2.setElement(0, 79);
        polynomial2.setElement(1, 129);
        polynomial2.setElement(2, 155);

        resultPolynomial.setSize(4);
        resultPolynomial.setElement(0, 53);
        resultPolynomial.setElement(1, 93);
        resultPolynomial.setElement(2, 240);
        resultPolynomial.setElement(3, 170);
        assertEquals("Division of polynomials with constant values failed.",
                resultPolynomial, polyGF.divide(polynomial1, polynomial2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        polyGF = new PolynomialGF2N(gf);
        for (int x = 0; x < 100; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1 + length1;
            polynomial1 = new Polynomial(length1, gf.getFieldSize());
            polynomial2 = new Polynomial(length2, gf.getFieldSize());

            assertEquals("Polynomial after being divided by itself must be 1.",
                    new Polynomial(1, 1),
                    polyGF.divide(polynomial1, polynomial1));
            assertEquals("Zero divided by anything must be zero.",
                    new Polynomial(1),
                    polyGF.divide(new Polynomial(1), polynomial2));
            assertEquals("Division of polynomial by longer polynomial must be zero.",
                    new Polynomial(1),
                    polyGF.divide(polynomial1, polynomial2));
            assertEquals("Result's length after division is wrong.",
                    length2 - length1 + 1,
                    polyGF.divide(polynomial2, polynomial1).getSize());

            //test division with remainder
            Polynomial remainder = new Polynomial(length1);
            Polynomial result = polyGF.divide(polynomial2, polynomial1, remainder);
            remainder.clearZeroValues();

            /* Deep test of result
             * X / Y = Z + R    =>  X = (Z * Y) + R
             */
            assertEquals("Result of division with remainder is wrong..",
                    polynomial2,
                    polyGF.add(polyGF.multiply(polynomial1, result), remainder));
        }

        //Tests in binary finite field
        polyGF = new PolynomialGF2N(3);
        for (int x = 0; x < 100; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1 + length1;
            polynomial1 = new Polynomial(length1, 1);
            polynomial2 = new Polynomial(length2, 1);

            assertEquals("Polynomial after being divided by itself must be 1.",
                    new Polynomial(1, 1),
                    polyGF.divide(polynomial1, polynomial1));
            assertEquals("Zero divided by anything must be zero.",
                    new Polynomial(1),
                    polyGF.divide(new Polynomial(1), polynomial2));
            assertEquals("Division of polynomial by longer polynomial must be zero.",
                    new Polynomial(1),
                    polyGF.divide(polynomial1, polynomial2));
            assertEquals("Result's length after division is wrong.",
                    length2 - length1 + 1,
                    polyGF.divide(polynomial2, polynomial1).getSize());

            //test division with remainder
            Polynomial remainder = new Polynomial(length1);
            Polynomial result = polyGF.divide(polynomial2, polynomial1, remainder);
            remainder.clearZeroValues();

            /* Deep test of result
             * X / Y = Z + R    =>  X = (Z * Y) + R
             */
            assertEquals("Result of division with remainder is wrong..",
                    polynomial2,
                    polyGF.add(polyGF.multiply(polynomial1, result), remainder));
        }

        try {
            polyGF.divide(new Polynomial(1), new Polynomial(1));
            fail("Division by zero should throw exception.");
        } catch (MathArithmeticException ex) {
            //OK
        }

        try {
            polyGF.divide(new Polynomial(1), new Polynomial(1), new Polynomial(1));
            fail("Division by zero should throw exception.");
        } catch (MathArithmeticException ex) {
            //OK
        }

    }

    @Test
    public void testGcd() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        Polynomial polynomial1 = new Polynomial(6);
        polynomial1.setElement(0, 79);
        polynomial1.setElement(1, 53);
        polynomial1.setElement(2, 208);
        polynomial1.setElement(3, 62);
        polynomial1.setElement(4, 196);
        polynomial1.setElement(5, 246);

        Polynomial polynomial2 = new Polynomial(3);
        polynomial2.setElement(0, 15);
        polynomial2.setElement(1, 101);
        polynomial2.setElement(2, 179);

        Polynomial resultPolynomial = new Polynomial(1);
        resultPolynomial.setElement(0, 1);
        assertEquals("Gcd of polynomials with constant values failed.",
                resultPolynomial, polyGF.gcd(polynomial1, polynomial2));

        polynomial1.setElement(0, 19);
        polynomial1.setElement(1, 102);
        polynomial1.setElement(2, 5);
        polynomial1.setElement(3, 10);
        polynomial1.setElement(4, 228);
        polynomial1.setElement(5, 17);

        polynomial2.setElement(0, 136);
        polynomial2.setElement(1, 68);
        polynomial2.setElement(2, 209);

        resultPolynomial.setElement(0, 1);
        assertEquals("Gcd of polynomials with constant values failed.",
                resultPolynomial, polyGF.gcd(polynomial1, polynomial2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        polyGF = new PolynomialGF2N(gf);
        for (int x = 0; x < 100; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1;
            polynomial1 = new Polynomial(length1, gf.getFieldSize());
            polynomial2 = new Polynomial(length2, gf.getFieldSize());

            Polynomial monicDiv = new Polynomial(1);
            monicDiv.setElement(0, polynomial1.getElement(polynomial1.getSize() - 1));
            assertEquals("Gcd(poly, poly*poly) must be poly(monic).",
                    polyGF.divide(polynomial1, monicDiv),
                    polyGF.gcd(polynomial1, polyGF.multiply(polynomial1, polynomial1)));
            assertEquals("Gcd(poly1, poly1*poly2) must be poly1(monic).",
                    polyGF.divide(polynomial1, monicDiv),
                    polyGF.gcd(polynomial1, polyGF.multiply(polynomial1, polynomial2)));

            assertEquals("Gcd(poly, 1) must be 1.",
                    new Polynomial(1, 1),
                    polyGF.gcd(polynomial1, new Polynomial(1, 1)));
            assertEquals("Gcd(1, poly) must be 1.",
                    new Polynomial(1, 1),
                    polyGF.gcd(new Polynomial(1, 1), polynomial2));

            assertEquals("Gcd(poly, 0) must be poly.",
                    polynomial1,
                    polyGF.gcd(polynomial1, new Polynomial()));
            assertEquals("Gcd(0, poly) must be poly.",
                    polynomial2,
                    polyGF.gcd(new Polynomial(), polynomial2));
        }
    }

    @Test
    public void testInvert() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        Polynomial polynomial1 = new Polynomial(3);
        polynomial1.setElement(0, 195);
        polynomial1.setElement(1, 103);
        polynomial1.setElement(2, 117);

        Polynomial polynomial2 = new Polynomial(6);
        polynomial2.setElement(0, 163);
        polynomial2.setElement(1, 221);
        polynomial2.setElement(2, 7);
        polynomial2.setElement(3, 51);
        polynomial2.setElement(4, 197);
        polynomial2.setElement(5, 172);

        Polynomial resultPolynomial = new Polynomial(5);
        resultPolynomial.setElement(0, 32);
        resultPolynomial.setElement(1, 142);
        resultPolynomial.setElement(2, 85);
        resultPolynomial.setElement(3, 232);
        resultPolynomial.setElement(4, 71);
        assertEquals("Inverse of polynomials with constant values failed.",
                resultPolynomial, polyGF.invert(polynomial1, polynomial2));

        polynomial1.setElement(0, 180);
        polynomial1.setElement(1, 222);
        polynomial1.setElement(2, 52);

        polynomial2.setElement(0, 106);
        polynomial2.setElement(1, 164);
        polynomial2.setElement(2, 241);
        polynomial2.setElement(3, 37);
        polynomial2.setElement(4, 108);
        polynomial2.setElement(5, 123);

        resultPolynomial.setElement(0, 205);
        resultPolynomial.setElement(1, 59);
        resultPolynomial.setElement(2, 72);
        resultPolynomial.setElement(3, 255);
        resultPolynomial.setElement(4, 128);
        assertEquals("Inverse of polynomials with constant values failed.",
                resultPolynomial, polyGF.invert(polynomial1, polynomial2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        polyGF = new PolynomialGF2N(gf);
        for (int x = 0; x < 128; x++) {
            try {
                int length1 = rn.nextInt(128) + 1;
                int length2 = rn.nextInt(128) + 1 + length1;
                polynomial1 = new Polynomial(length1, gf.getFieldSize());
                polynomial2 = new Polynomial(length2, gf.getFieldSize());

                //Test: inverse(a) % x = b    =>   ( a * b ) % x = 1
                Polynomial inverse = polyGF.invert(polynomial1, polynomial2);
                Polynomial remainder = new Polynomial(length1);
                Polynomial result = polyGF.divide(polyGF.multiply(polynomial1, inverse), polynomial2, remainder);
                remainder.clearZeroValues();

                assertEquals("Inverse result is wrong.",
                        new Polynomial(1, 1),
                        remainder);

                try {
                    polyGF.invert(polynomial2, polynomial1);
                    fail("MathArithmeticException should be thrown, "
                            + "when moduloPolynomial is shorter than polynomial");
                } catch (MathArithmeticException ex) {
                    //OK
                }
            } catch (MathArithmeticException ex) {
                //OK, some polynomials cannot be inverted with some modulos
            }

        }

        //Tests in binary finite field
        polyGF = new PolynomialGF2N(3);
        for (int x = 0; x < 128; x++) {
            try {
                int length1 = rn.nextInt(128) + 1;
                int length2 = rn.nextInt(128) + 1 + length1;
                polynomial1 = new Polynomial(length1, 1);
                polynomial2 = new Polynomial(length2, 1);

                //Test: inverse(a) % x = b    =>   ( a * b ) % x = 1
                Polynomial inverse = polyGF.invert(polynomial1, polynomial2);
                Polynomial remainder = new Polynomial(length1);
                Polynomial result = polyGF.divide(polyGF.multiply(polynomial1, inverse), polynomial2, remainder);
                remainder.clearZeroValues();

                assertEquals("Inverse result is wrong.",
                        new Polynomial(1, 1),
                        remainder);

                try {
                    polyGF.invert(polynomial2, polynomial1);
                    fail("MathArithmeticException should be thrown, "
                            + "when moduloPolynomial is shorter than polynomial");
                } catch (MathArithmeticException ex) {
                    //OK
                }
            } catch (MathArithmeticException ex) {
                //OK, some polynomials cannot be inverted with some modulos
            }

        }

    }

    @Test
    public void testPower() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        Polynomial polynomial = new Polynomial(3);
        polynomial.setElement(0, 147);
        polynomial.setElement(1, 206);
        polynomial.setElement(2, 57);

        Polynomial resultPolynomial = new Polynomial(7);
        resultPolynomial.setElement(0, 163);
        resultPolynomial.setElement(1, 131);
        resultPolynomial.setElement(2, 142);
        resultPolynomial.setElement(3, 56);
        resultPolynomial.setElement(4, 55);
        resultPolynomial.setElement(5, 110);
        resultPolynomial.setElement(6, 74);
        assertEquals("Inverse of polynomials with constant values failed.",
                resultPolynomial, polyGF.power(polynomial, 3));

        polynomial.setElement(0, 90);
        polynomial.setElement(1, 50);
        polynomial.setElement(2, 110);

        resultPolynomial.setElement(0, 130);
        resultPolynomial.setElement(1, 131);
        resultPolynomial.setElement(2, 242);
        resultPolynomial.setElement(3, 117);
        resultPolynomial.setElement(4, 98);
        resultPolynomial.setElement(5, 51);
        resultPolynomial.setElement(6, 175);
        assertEquals("Inverse of polynomials with constant values failed.",
                resultPolynomial, polyGF.power(polynomial, 3));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        polyGF = new PolynomialGF2N(gf);
        for (int x = 1; x < 100; x++) {
            int length = rn.nextInt(16) + 1;
            polynomial = new Polynomial(length);

            assertEquals("ZeroPolynomial powered to anything must be zeroPolynomial.",
                    new Polynomial(),
                    polyGF.power(polynomial, x));

            assertEquals("1 powered to anything must be 1.",
                    new Polynomial(1, 1),
                    polyGF.power(new Polynomial(1, 1), x));

            polynomial = new Polynomial(length, gf.getFieldSize());;

            assertEquals("Powered polynomial has wrong lentgh.",
                    length + ((x - 1) * (length - 1)),
                    polyGF.power(polynomial, x).getSize());
        }

        try {
            polyGF.power(new Polynomial(9), -1);
            fail("Power to negative exponent should throw MathIllegalArgumentException.");
        } catch (MathIllegalArgumentException ex) {
            //OK
        }
    }
}
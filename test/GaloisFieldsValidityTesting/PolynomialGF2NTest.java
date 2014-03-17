package GaloisFieldsValidityTesting;

import java.util.Arrays;
import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.Polynomial;
import muni.fi.gf2n.classes.PolynomialGF2N;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 *
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
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        int length = rn.nextInt(255) + 1;
        Polynomial polynomial = new Polynomial(length, gf.getFieldSize());
        Polynomial zeroPolynomial = new Polynomial(rn.nextInt(255) + 1);

        assertEquals("Polynomial cannot be changed after addition with zeroPolynomial.",
                polynomial, polyGF.add(polynomial, zeroPolynomial));

        assertEquals("Addidion of two identical polynomials must be zero polynomial.",
                new Polynomial(), polyGF.add(polynomial, polynomial));
    }

    @Test
    public void testSubtract() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        int length = rn.nextInt(255) + 1;
        Polynomial polynomial = new Polynomial(length, gf.getFieldSize());
        Polynomial zeroPolynomial = new Polynomial(rn.nextInt(255) + 1);

        assertEquals("Polynomial cannot be changed after subtraction of zeroPolynomial.",
                polynomial, polyGF.subtract(polynomial, zeroPolynomial));

        assertEquals("Subtraction of two identical polynomials must be zero polynomial.",
                zeroPolynomial.clearZeroValues(), polyGF.subtract(polynomial, polynomial));
    }

    @Test
    public void testMultiply() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 0; x < 64; x++) {

            int length1 = rn.nextInt(255) + 1;
            int length2 = rn.nextInt(255) + 1;

            Polynomial polynomial1 = new Polynomial(length1, gf.getFieldSize());
            Polynomial polynomial2 = new Polynomial(length2, gf.getFieldSize());

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
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 0; x < 64; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1 + length1;
            Polynomial polynomial1 = new Polynomial(length1, gf.getFieldSize());
            Polynomial polynomial2 = new Polynomial(length2, gf.getFieldSize());

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
        for (int x = 0; x < 64; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1 + length1;
            Polynomial polynomial1 = new Polynomial(length1, 1);
            Polynomial polynomial2 = new Polynomial(length2, 1);

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
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            polyGF.divide(new Polynomial(1), new Polynomial(1), new Polynomial(1));
            fail("Division by zero should throw exception.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

    }

    @Test
    public void testGcd() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 0; x < 64; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1;
            Polynomial polynomial1 = new Polynomial(length1, gf.getFieldSize());
            Polynomial polynomial2 = new Polynomial(length2, gf.getFieldSize());

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
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 0; x < 128; x++) {
            try {
                int length1 = rn.nextInt(128) + 1;
                int length2 = rn.nextInt(128) + 1 + length1;
                Polynomial polynomial1 = new Polynomial(length1, gf.getFieldSize());
                Polynomial polynomial2 = new Polynomial(length2, gf.getFieldSize());

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
                    fail("Exception should be thrown, when moduloPolynomial is shorter than polynomial");
                } catch (IllegalArgumentException ex) {
                    //OK
                }
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().equals("Cannot compute inverse for this args.")) {
                    //OK, some polynomials cannot be inverted with some modulos
                } else {
                    fail(ex.getMessage());
                }
            }

        }

        //Tests in binary finite field
        polyGF = new PolynomialGF2N(3);
        for (int x = 0; x < 128; x++) {
            try {
                int length1 = rn.nextInt(128) + 1;
                int length2 = rn.nextInt(128) + 1 + length1;
                Polynomial polynomial1 = new Polynomial(length1, 1);
                Polynomial polynomial2 = new Polynomial(length2, 1);

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
                    fail("Exception should be thrown, when moduloPolynomial is shorter than polynomial");
                } catch (IllegalArgumentException ex) {
                    //OK
                }
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().equals("Cannot compute inverse for this args.")) {
                    //OK, some polynomials cannot be inverted with some modulos
                } else {
                    fail(ex.getMessage());
                }
            }

        }

    }

    @Test
    public void testPower() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 1; x < 32; x++) {
            int length = rn.nextInt(16) + 1;
            Polynomial polynomial = new Polynomial(length);

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
            fail("Power to negative exponent should throw exception.");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
}
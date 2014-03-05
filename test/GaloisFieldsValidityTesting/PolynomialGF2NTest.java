/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GaloisFieldsValidityTesting;

import java.util.Arrays;
import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
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
 * Class PolynomialGF2N Testing
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
        long[] polynomial = generateRandomPoly(length);
        long[] zeroPolynomial = new long[rn.nextInt(255) + 1];

        assertArrayEquals("Polynomial cannot be changed after addition with zeroPolynomial.",
                polynomial, polyGF.add(polynomial, zeroPolynomial));

        assertArrayEquals("Addidion of two identical polynomials must be zero polynomial.",
                new long[0], polyGF.add(polynomial, polynomial));
    }

    @Test
    public void testSubtract() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        int length = rn.nextInt(255) + 1;
        long[] polynomial = generateRandomPoly(length);
        long[] zeroPolynomial = new long[rn.nextInt(255) + 1];

        assertArrayEquals("Polynomial cannot be changed after subtraction of zeroPolynomial.",
                polynomial, polyGF.subtract(polynomial, zeroPolynomial));

        assertArrayEquals("Subtraction of two identical polynomials must be zero polynomial.",
                new long[0], polyGF.subtract(polynomial, polynomial));
    }

    @Test
    public void testMultiply() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 0; x < 64; x++) {

            int length1 = rn.nextInt(255) + 1;
            int length2 = rn.nextInt(255) + 1;

            long[] polynomial1 = generateRandomPoly(length1);
            long[] polynomial2 = generateRandomPoly(length2);

            assertEquals("Length of polynomials after their multiplication is wrong.",
                    length1 + length2 - 1, polyGF.multiply(polynomial1, polynomial2).length);

            assertEquals("Highest coefficient value after multiplication is wrong.",
                    gf.multiply(polynomial1[length1 - 1], polynomial2[length2 - 1]),
                    polyGF.multiply(polynomial1, polynomial2)[length1 + length2 - 2]);

            assertEquals("Lowest coefficient value after multiplication is wrong.",
                    gf.multiply(polynomial1[0], polynomial2[0]),
                    polyGF.multiply(polynomial1, polynomial2)[0]);

            assertArrayEquals("Polynomial after multiplication with zero must be zeroPolynomial.",
                    new long[0],
                    polyGF.multiply(polynomial1, new long[length2]));
        }

    }

    @Test
    public void testDivide() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 0; x < 64; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1 + length1;
            long[] polynomial1 = generateRandomPoly(length1);
            long[] polynomial2 = generateRandomPoly(length2);

            assertArrayEquals("Polynomial after being divided by itself must be 1.",
                    new long[]{1},
                    polyGF.divide(polynomial1, polynomial1));
            assertArrayEquals("Polynomial after being divided by itself must be 1.",
                    new long[]{1},
                    polyGF.divide(polynomial2, polynomial2));
            assertArrayEquals("Zero divided by anything must be zero.",
                    new long[]{0},
                    polyGF.divide(new long[]{0}, polynomial2));

            assertArrayEquals("Division of polynomial by longer polynomial must be 0.",
                    new long[]{0},
                    polyGF.divide(polynomial1, polynomial2));
            assertEquals("Result's length after division is wrong.",
                    length2 - length1 + 1,
                    polyGF.divide(polynomial2, polynomial1).length);

            //test division with remainder
            long[] remainder = new long[length1];
            long[] result = polyGF.divide(polynomial2, polynomial1, remainder);
            remainder = clearZeroValuesFromPolynomial(remainder);

            /* Deep test of result
             * X / Y = Z + R    =>  X = (Z * Y) + R
             */
            assertArrayEquals("Result of division with remainder is wrong..",
                    polynomial2,
                    polyGF.add(polyGF.multiply(polynomial1, result), remainder));
        }

        //Tests in binary finite field
        polyGF = new PolynomialGF2N(3);
        for (int x = 0; x < 64; x++) {
            int length1 = rn.nextInt(128) + 1;
            int length2 = rn.nextInt(128) + 1 + length1;
            long[] polynomial1 = generateRandomBinaryPoly(length1);
            long[] polynomial2 = generateRandomBinaryPoly(length2);

            assertArrayEquals("Polynomial after being divided by itself must be 1.",
                    new long[]{1},
                    polyGF.divide(polynomial1, polynomial1));
            assertArrayEquals("Polynomial after being divided by itself must be 1.",
                    new long[]{1},
                    polyGF.divide(polynomial2, polynomial2));

            assertArrayEquals("Division of polynomial by longer polynomial must be 0.",
                    new long[]{0},
                    polyGF.divide(polynomial1, polynomial2));
            assertEquals("Result's length after division is wrong.",
                    length2 - length1 + 1,
                    polyGF.divide(polynomial2, polynomial1).length);

            //test division with remainder
            long[] remainder = new long[length1];
            long[] result = polyGF.divide(polynomial2, polynomial1, remainder);
            remainder = clearZeroValuesFromPolynomial(remainder);

            /* Deep test of result
             * X / Y = Z + R    =>  X = (Z * Y) + R
             */
            assertArrayEquals("Result of division with remainder is wrong..",
                    polynomial2,
                    polyGF.add(polyGF.multiply(polynomial1, result), remainder));
        }

        try {
            polyGF.divide(new long[1], new long[1]);
            fail("Division by zero should throw exception.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            polyGF.divide(new long[1], new long[1], new long[1]);
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
            long[] polynomial1 = generateRandomPoly(length1);
            long[] polynomial2 = generateRandomPoly(length2);

            assertArrayEquals("Gcd(poly, poly*poly) must be poly(monic).",
                    polyGF.divide(polynomial1, new long[]{polynomial1[polynomial1.length - 1]}),
                    polyGF.gcd(polynomial1, polyGF.multiply(polynomial1, polynomial1)));
            assertArrayEquals("Gcd(poly1, poly1*poly2) must be poly1(monic).",
                    polyGF.divide(polynomial1, new long[]{polynomial1[polynomial1.length - 1]}),
                    polyGF.gcd(polynomial1, polyGF.multiply(polynomial1, polynomial2)));

            assertArrayEquals("Gcd(poly, 1) must be 1.",
                    new long[]{1},
                    polyGF.gcd(polynomial1, new long[]{1}));
            assertArrayEquals("Gcd(1, poly) must be 1.",
                    new long[]{1},
                    polyGF.gcd(new long[]{1}, polynomial2));

            assertArrayEquals("Gcd(poly, 0) must be poly.",
                    polynomial1,
                    polyGF.gcd(polynomial1, new long[]{0}));
            assertArrayEquals("Gcd(0, poly) must be poly.",
                    polynomial2,
                    polyGF.gcd(new long[]{0}, polynomial2));

        }
    }

    @Test
    public void testInvert() {
        PolynomialGF2N polyGF = new PolynomialGF2N(gf);

        for (int x = 0; x < 128; x++) {
            try {
                int length1 = rn.nextInt(128) + 1;
                int length2 = rn.nextInt(128) + 1 + length1;
                long[] polynomial1 = generateRandomPoly(length1);
                long[] polynomial2 = generateRandomPoly(length2);

                //Test: inverse(a) % x = b    =>   ( a * b ) % x = 1
                long[] inverse = polyGF.invert(polynomial1, polynomial2);
                long[] remainder = new long[length1];
                long[] result = polyGF.divide(polyGF.multiply(polynomial1, inverse), polynomial2, remainder);
                remainder = clearZeroValuesFromPolynomial(remainder);

                assertArrayEquals("Inverse result is wrong.",
                        new long[]{1},
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
                long[] polynomial1 = generateRandomBinaryPoly(length1);
                long[] polynomial2 = generateRandomBinaryPoly(length2);

                //Test: inverse(a) % x = b    =>   ( a * b ) % x = 1
                long[] inverse = polyGF.invert(polynomial1, polynomial2);
                long[] remainder = new long[length1];
                long[] result = polyGF.divide(polyGF.multiply(polynomial1, inverse), polynomial2, remainder);
                remainder = clearZeroValuesFromPolynomial(remainder);

                assertArrayEquals("Inverse result is wrong.",
                        new long[]{1},
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
            long[] polynomial = new long[length];

            assertArrayEquals("ZeroPolynomial powered to anything must be zeroPolynomial.",
                    new long[0],
                    polyGF.power(polynomial, x));

            assertArrayEquals("1 powered to anything must be 1.",
                    new long[]{1},
                    polyGF.power(new long[]{1}, x));

            polynomial = generateRandomPoly(length);

            assertEquals("Powered polynomial has wrong lentgh.",
                    length + ((x - 1) * (length - 1)),
                    polyGF.power(polynomial, x).length);
        }

        try {
            polyGF.power(new long[]{9}, -1);
            fail("Power to negative exponent should throw exception.");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    private long[] generateRandomPoly(int length) {
        long[] resultPoly = new long[length];

        for (int x = 0; x < length - 1; x++) {
            resultPoly[x] = rn.nextInt(4194303);
        }
        resultPoly[length - 1] = rn.nextInt(4194302) + 1;

        return resultPoly;
    }

    private long[] generateRandomBinaryPoly(int length) {
        long[] resultPoly = new long[length];

        for (int x = 0; x < length - 1; x++) {
            resultPoly[x] = rn.nextInt(2);
        }
        resultPoly[length - 1] = 1;

        return resultPoly;
    }

    //return new array without zero values on highest positions in argument array
    private long[] clearZeroValuesFromPolynomial(long[] polynomial) {

        long[] result;

        for (int x = polynomial.length - 1; x >= 0; x--) {
            if (polynomial[x] != 0) {
                result = new long[x + 1];
                System.arraycopy(polynomial, 0, result, 0, x + 1);
                return result;
            }
        }

        result = new long[0];
        return result;
    }
}
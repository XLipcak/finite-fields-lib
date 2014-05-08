package muni.fi.gf2n.classes;

import java.util.ArrayList;
import muni.fi.gf2n.interfaces.GaloisFieldArithmetic;

/**
 * Class GF2N implements methods for computation with finite fields. Instance of
 * this class is specified by reducing polynomial. This reducing polynomial is
 * used as characteristic polynomial for this Galois Field and methods for
 * computation with this field depend on it.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class GF2N implements GaloisFieldArithmetic {

    private static final long[] BINARY_POWERS = {1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L, 1024L, 2048L,
        4096L, 8192L, 16384L, 32768L, 65536L, 131072L, 262144L, 524288L, 1048576L, 2097152L, 4194304L, 8388608L,
        16777216L, 33554432L, 67108864L, 134217728L, 268435456L, 536870912L, 1073741824L, 2147483648L,
        4294967296L, 8589934592L, 17179869184L, 34359738368L, 68719476736L, 137438953472L, 274877906944L,
        549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L,
        35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L,
        2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L,
        72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L,
        2305843009213693952L, 4611686018427387904L, 9223372036854775807L};
    private long reducingPolynomial;
    private short fieldSize;

    /**
     * Constructs an object of GF2N Class. This object has to be specified by
     * reducing polynomial. This polynomial is used as characteristic polynomial
     * for Galois field, in which we want to perform operations.
     *
     * @param reducingPolynomial Characteristic polynomial of Galois field. It
     * is represented by long, and coefficients of this polynomial are created
     * from binary representation of this number.
     */
    public GF2N(long reducingPolynomial) {

        if (reducingPolynomial <= 0) {
            throw new IllegalArgumentException("Reducing polynomial must be represented by positive number.");
        }

        this.reducingPolynomial = reducingPolynomial;
        fieldSize = countBinarySize(reducingPolynomial);
    }

    @Override
    public long add(long element1, long element2) {
        isInField(element1, element2);
        return element1 ^ element2;
    }

    @Override
    public long subtract(long element1, long element2) {
        isInField(element1, element2);
        return element1 ^ element2;
    }

    @Override
    public long multiply(long element1, long element2) {
        isInField(element1, element2);

        long result = 0;
        long element2Actual = element2;

        while (element2Actual != 0) {
            long actualResult = element1;
            int actualElement2Size = countBinarySize(element2Actual);

            for (int x = 0; x < actualElement2Size; x++) {
                actualResult <<= 1;
                if (actualResult > (BINARY_POWERS[fieldSize] - 1)) {
                    actualResult ^= reducingPolynomial;
                }
            }
            element2Actual ^= BINARY_POWERS[countBinarySize(element2Actual)];
            result ^= actualResult;
        }

        return result;
    }

    @Override
    public long divide(long element1, long element2) {
        isInField(element1, element2);
        if (element2 == 0) {
            throw new IllegalArgumentException("Division by zero.");
        }
        return (multiply(element1, invert(element2)));
    }

    @Override
    public long invert(long element) {
        isInField(element);

        if (element == 1) {
            return 1;
        }

        if (element == 0) {
            throw new IllegalArgumentException("Cannot find inverse for ZERO.");
        }

        //prepare for division
        long remainder = element;
        long numerator = reducingPolynomial;
        long denumerator = element;

        //prepare to find Bezout's identity
        long temp = 0;
        ArrayList<Long> resultList = new ArrayList<>();
        ArrayList<Long> bezoutIdentity = new ArrayList<>();


        //find greatest greatest common divisor, last positive remainder
        while (remainder != 0) {

            short numeratorBinarySize = countBinarySize(numerator);
            short denumeratorBinarySize = countBinarySize(denumerator);
            long tempNumerator = numerator;

            //division of binary polynomial
            long result = 0;
            while (numeratorBinarySize >= denumeratorBinarySize) {
                temp = 1;
                temp <<= (numeratorBinarySize - denumeratorBinarySize);
                result ^= temp;
                tempNumerator ^= (denumerator << (numeratorBinarySize - denumeratorBinarySize));

                numeratorBinarySize = countBinarySize(tempNumerator);
                denumeratorBinarySize = countBinarySize(denumerator);
            }
            remainder = tempNumerator;

            if (remainder == 0 && denumerator != 1) {
                throw new IllegalArgumentException("Cannot compute inverse"
                        + " for this element.");
            }

            //resultList data neccessary to find Bezout's identity
            resultList.add(numerator);
            resultList.add(denumerator);
            resultList.add(result);

            numerator = denumerator;
            denumerator = remainder;
        }

        if (resultList.size() > 3) {
            //we don't need last 3 values
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
        }

        bezoutIdentity.add(1l);
        bezoutIdentity.add(resultList.get(resultList.size() - 3));
        bezoutIdentity.add(resultList.get(resultList.size() - 2));
        bezoutIdentity.add(resultList.get(resultList.size() - 1));


        //find Bezout's identity from resultList data counted in Euclidean algorithm
        while (resultList.size() != 3) {
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);

            temp = bezoutIdentity.get(0);
            bezoutIdentity.set(0, bezoutIdentity.get(3));
            bezoutIdentity.set(1, resultList.get(resultList.size() - 3));
            bezoutIdentity.set(2, resultList.get(resultList.size() - 2));
            bezoutIdentity.set(3, add(temp, multiply(bezoutIdentity.get(3), resultList.get(resultList.size() - 1))));
        }

        //inverted element
        return bezoutIdentity.get(3);
    }

    //Square-and-multiply to compute power
    @Override
    public long power(long element, long exponent) {
        isInField(element);

        //overit este tie 2 podmienky ci je to good pristup
        if (exponent < 0) {
            throw new IllegalArgumentException("Cannot compute power with negative exponent!");
        }

        if (exponent == 0 && element != 0) {
            return 1l;
        }

        if (exponent == 0 && element == 0) {
            return 0l;
        }

        long result = 1;
        long a = element;
        long x = exponent;

        while (x != 1) {
            if ((x % 2) == 1) {
                result = multiply(result, a);
            }
            x /= 2;
            a = multiply(a, a);
        }

        return multiply(result, a);
    }

    /**
     * Polynomial with binary coefficients is created from binary representation
     * of long value taken as parameter. This method determines, whether this
     * polynomial is irreducible or not.
     *
     * @param polynomial binary polynomial represented by long
     * @return true, if polynomial is irreducible, false otherwise
     */
    public static boolean isIrreducible(long polynomial) {

        //Rabin's test of irreducibility
        if (polynomial <= 0) {
            throw new IllegalArgumentException("Polynomial must be represented by positive number.");
        }
        if (polynomial == 2) {
            return false;
        }
        if (countBinarySize(polynomial) > 30) {
            throw new IllegalArgumentException("Cannot test irreducibility for such a big number.");
            //it would last one hundred years anyway...
        }

        Polynomial poly = new Polynomial((int) countBinarySize(polynomial) + 1);
        int degree = poly.getSize() - 1;
        long value = polynomial;

        //create polynomial representation from Long
        for (int x = 0; x < poly.getSize(); x++) {
            if ((value & 1) == 1) {
                poly.setElement(x, 1);
            } else {
                poly.setElement(x, 0);
            }
            value >>= 1;
        }

        PolynomialGF2N polyGF = new PolynomialGF2N(3);
        long[] primes = new long[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
            31, 37, 41, 43, 47, 53, 59, 61};
        ArrayList<Long> divisors = new ArrayList<>();

        //find prime divisors of degree of the polynomial
        for (int x = 0; primes[x] <= degree; x++) {
            if (degree % primes[x] == 0) {
                divisors.add(primes[x]);
            }
        }

        //divide degree by all its prime divisors
        for (int x = 0; x < divisors.size(); x++) {
            divisors.set(x, (long) (degree / divisors.get(x)));
        }


        for (int x = 0; x < divisors.size(); x++) {
            //prepare tempPoly:   x^(2^divisors(x)) + x
            Polynomial tempPoly = new Polynomial((int) Math.pow(2, divisors.get(x)) + 1);
            tempPoly.setElement(1, 1);
            tempPoly.setElement(tempPoly.getSize() - 1, 1);

            Polynomial remainder = new Polynomial(tempPoly.getSize());
            polyGF.divide(tempPoly, poly, remainder);

            //remainder = tempPoly % poly
            remainder.clearZeroValues();

            //gcd must be 1 for every irreducible polynomial
            Polynomial gcd = polyGF.gcd(poly, remainder);
            if (!(gcd.getSize() == 1 && gcd.getElement(0) == 1)) {
                return false;
            }
        }

        //tempPoly = x^(2^degree) + x
        Polynomial tempPoly = new Polynomial((int) Math.pow(2, degree) + 1);
        tempPoly.setElement(1, 1);
        tempPoly.setElement(tempPoly.getSize() - 1, 1);


        //remainder = tempPoly % poly
        Polynomial remainder = new Polynomial(tempPoly.getSize());
        polyGF.divide(tempPoly, poly, remainder);
        remainder.clearZeroValues();

        //tempPoly % poly must be 1 for every irreducible polynomial
        if (remainder.getSize() == 0) {
            return true;
        }

        return false;
    }

    /**
     * Returns reducing polynomial of this Galois field.
     *
     * @return reducing polynomial
     */
    public long getReducingPolynomial() {
        return reducingPolynomial;
    }

    /**
     * Set reducing polynomial for computation in this Galois Field.
     *
     * @param reducingPolynomial reducing polynomial to be set
     */
    public void setReducingPolynomial(long reducingPolynomial) {

        if (reducingPolynomial <= 0) {
            throw new IllegalArgumentException("Reducing polynomial must be represented by positive number.");
        }

        this.reducingPolynomial = reducingPolynomial;
        fieldSize = countBinarySize(reducingPolynomial);
    }

    /**
     * Return max binary size of elements, which belong to this Galois Field.
     *
     * @return field size
     */
    public short getFieldSize() {
        return fieldSize;
    }

    /**
     * Compute binary size of input value.
     *
     * @param value value
     * @return binary size of value
     */
    public static short countBinarySize(long value) {

        short result = -1;

        while (value != 0) {
            value >>= 1;
            result++;
        }

        return result;
    }

    private void isInField(long element) {

        if ((element >= BINARY_POWERS[fieldSize]) || (element < 0)) {
            throw new IllegalArgumentException("Values for this reducing polynomial must be in [0, "
                    + (BINARY_POWERS[fieldSize] - 1) + "].");
        }
    }

    private void isInField(long element1, long element2) {

        if ((element1 >= BINARY_POWERS[fieldSize]) || (element2 >= BINARY_POWERS[fieldSize]) || (element1 < 0) || (element2 < 0)) {
            throw new IllegalArgumentException("Values for this reducing polynomial must be in [0, "
                    + (BINARY_POWERS[fieldSize] - 1) + "].");
        }
    }
}

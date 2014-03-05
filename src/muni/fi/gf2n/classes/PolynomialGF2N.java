/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.classes;

import muni.fi.gf2n.interfaces.GaloisFieldPolynomialArithmetic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 *
 * PolynomialGF2N class
 *
 */
public class PolynomialGF2N implements GaloisFieldPolynomialArithmetic {

    private GF2N galoisField;

    public PolynomialGF2N(GF2N galoisField) {
        this.galoisField = galoisField;
    }

    public PolynomialGF2N(long reducingPolynomial) {
        galoisField = new GF2N(reducingPolynomial);
    }

    @Override
    public long[] add(long[] polynomial1, long[] polynomial2) {

        int length = Math.max(polynomial1.length, polynomial2.length);
        long[] result = new long[length];

        for (int x = 0; x < length; x++) {
            try {
                long value = galoisField.add(polynomial1[x], polynomial2[x]);
                result[x] = value;
            } catch (IndexOutOfBoundsException ex) {
                if (x >= polynomial1.length) {
                    result[x] = polynomial2[x];
                } else {
                    result[x] = polynomial1[x];
                }
            }
        }

        return clearZeroValuesFromPolynomial(result);
    }

    @Override
    public long[] subtract(long[] polynomial1, long[] polynomial2) {
        return add(polynomial1, polynomial2);
    }

    @Override
    public long[] multiply(long[] polynomial1, long[] polynomial2) {

        if (isZero(polynomial1) || isZero(polynomial2)) {
            return new long[0];
        }

        long[] result = new long[polynomial1.length + polynomial2.length - 1];

        for (int x = 0; x < polynomial2.length; x++) {
            for (int y = 0; y < polynomial1.length; y++) {
                if (polynomial1[y] != 0) {
                    int index = y + x;
                    long multiplyResult = galoisField.multiply(polynomial1[y], polynomial2[x]);
                    long originalValue = result[index];
                    result[index] = galoisField.add(originalValue, multiplyResult);
                }
            }
        }

        return clearZeroValuesFromPolynomial(result);
    }

    @Override
    public long[] divide(long[] polynomial1, long[] polynomial2) {

        if (isZero(polynomial2)) {
            throw new IllegalArgumentException("Division by zero!");
        }

        long[] remainder = new long[Math.max(polynomial1.length, polynomial2.length)];
        return divide(polynomial1, polynomial2, remainder);
    }

    //remainder length not controlled yet
    @Override
    public long[] divide(long[] polynomial1, long[] polynomial2, long[] remainder) {

        if (isZero(polynomial2)) {
            throw new IllegalArgumentException("Division by zero!");
        }

        long[] result;
        long[] rem;

        //prepare arrays of good length
        if (polynomial1.length >= polynomial2.length) {
            result = new long[polynomial1.length - polynomial2.length + 1];
            rem = new long[polynomial1.length - polynomial2.length + 1];
        } else {
            result = new long[1];
            rem = new long[polynomial1.length];
        }

        long[] numerator = polynomial1;
        long[] mulResult = new long[polynomial1.length];

        while ((numerator.length >= polynomial2.length)) {

            //divide
            long value = galoisField.divide(numerator[numerator.length - 1], polynomial2[polynomial2.length - 1]);
            result[numerator.length - polynomial2.length] = value;

            //subtract divided part from numerator
            rem[numerator.length - polynomial2.length] = value;
            mulResult = multiply(polynomial2, rem);
            numerator = subtract(numerator, mulResult);

            //set remainder to correct length necessary for next divison
            while ((numerator.length - polynomial2.length + 1) < rem.length) {
                if (rem.length == 0) {
                    break;
                } else {
                    long[] newRem = new long[rem.length - 1];
                    System.arraycopy(rem, 0, newRem, 0, rem.length - 1);
                    rem = newRem;
                }
            }
        }

        //copy remainder of division to remainder attribute of this method
        System.arraycopy(numerator, 0, remainder, 0, numerator.length);

        return result;
    }

    // result = gcd(polynomial1, polynomial2) = a*polynomial1 + b*polynomial2
    private long[] xgcd(long[] polynomial1, long[] polynomial2, long[] a, long[] b) {

        if (polynomial2.length > polynomial1.length) {
            //swap
            long[] swapArray = polynomial1;
            polynomial1 = polynomial2;
            polynomial2 = swapArray;
            swapArray = a;
            a = b;
            b = swapArray;
        }

        //prepare for division
        long[] result = new long[polynomial2.length];
        long[] remainder = polynomial2;
        long[] numerator = polynomial1;
        long[] denumerator = polynomial2;

        //prepare to find Bezout's identity
        long[] temp;
        ArrayList<long[]> resultList = new ArrayList<>();
        ArrayList<long[]> bezoutIdentity = new ArrayList<>();


        //find greatest greatest common divisor, last positive remainder
        while (!(remainder.length == 0)) {
            result = remainder;
            remainder = new long[Math.max(numerator.length, denumerator.length)];
            temp = divide(numerator, denumerator, remainder);

            //resultList data neccessary to find Bezout's identity
            resultList.add(numerator);
            resultList.add(denumerator);
            resultList.add(temp);

            remainder = clearZeroValuesFromPolynomial(remainder);
            numerator = denumerator;
            denumerator = remainder;
        }

        if (resultList.size() > 3) {
            //we don't need last 3 values
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
        }

        long[] value = {1l};
        bezoutIdentity.add(value);
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

        //save Bezout's coefficients and return gcd
        System.arraycopy(bezoutIdentity.get(0), 0, a, 0, bezoutIdentity.get(0).length);
        System.arraycopy(bezoutIdentity.get(3), 0, b, 0, bezoutIdentity.get(3).length);

        //gcd, not normalized
        return result;

    }

    @Override
    public long[] gcd(long[] polynomial1, long[] polynomial2) {

        if (!isZero(polynomial1) && isZero(polynomial2)) {
            return polynomial1;
        }
        if (isZero(polynomial1) && !isZero(polynomial2)) {
            return polynomial2;
        }
        if (isZero(polynomial1) && isZero(polynomial2)) {
            return new long[0];
        }

        long[] result = new long[polynomial2.length];
        long[] remainder = polynomial2;
        long[] numerator = polynomial1;
        long[] denumerator = polynomial2;


        //find greatest greatest common divisor, last positive remainder
        while (!(remainder.length == 0)) {
            result = remainder;
            remainder = new long[Math.max(numerator.length, denumerator.length)];
            divide(numerator, denumerator, remainder);
            remainder = clearZeroValuesFromPolynomial(remainder);
            numerator = denumerator;
            denumerator = remainder;
        }

        //make gcd monic
        return divide(result, new long[]{result[result.length - 1]});

    }

    @Override
    public long[] invert(long[] polynomial, long[] moduloPolynomial) {

        if (isZero(polynomial) || isZero(moduloPolynomial)) {
            throw new IllegalArgumentException("Cannot compute inverse for zero polynomials.");
        }

        if ((polynomial.length >= moduloPolynomial.length) || (moduloPolynomial.length == 1)) {
            throw new IllegalArgumentException("Cannot compute inverse for this args.");
        }

        if (polynomial.length == 1) {
            //special solution for special case :)
            return divide(add(new long[]{1}, multiply(moduloPolynomial, polynomial)), polynomial);
        }

        long[] gcd;
        long[] result = new long[moduloPolynomial.length];
        long[] temp = new long[moduloPolynomial.length];

        //xgcd to set the result
        gcd = xgcd(moduloPolynomial, polynomial, temp, result);

        if (gcd.length != 1) {
            throw new IllegalArgumentException("Cannot compute inverse for this args.");
        }

        //normalize result, after this, remainder after division by moduloPolynomial will be 1
        result = clearZeroValuesFromPolynomial(result);
        return divide(result, gcd);
    }

    @Override
    public long[] power(long[] polynomial, long exponent) {

        if (exponent <= 0) {
            throw new IllegalArgumentException("Exponent must be positive number!");
        }

        if (isZero(polynomial)) {
            return new long[0];
        }

        long[] result = polynomial;

        for (int x = 1; x < exponent; x++) {
            result = multiply(polynomial, result);
        }

        return result;
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

    private boolean isZero(long[] polynomial) {
        if (polynomial.length == 0) {
            return true;
        }

        for (int x = 0; x < polynomial.length; x++) {
            if (polynomial[x] != 0) {
                return false;
            }
        }
        return true;
    }
}

package muni.fi.gf2n.classes;

import muni.fi.gf2n.interfaces.GaloisFieldPolynomialArithmetic;
import java.util.ArrayList;

/**
 * Class PolynomialGF2N implements interface GaloisFieldPolynomialArithmetic for
 * computation with polynomials in Galois Fields. Object of PolynomialGF2N class
 * has to be specified by reducing polynomial for Galois Field, or by object of
 * class GF2N.
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class PolynomialGF2N implements GaloisFieldPolynomialArithmetic {

    private GF2N galoisField;

    /**
     * Constructs an object of PolynomialGF2N class. Object of PolynomialGF2N
     * class has to be specified by galoisField, which is used for computation
     * with polynomials over Galois Field.
     *
     * @param galoisField GF2N galoisField used for computation with polynomials
     * over Galois Field
     *
     */
    public PolynomialGF2N(GF2N galoisField) {
        this.galoisField = galoisField;
    }

    /**
     * Constructs an object of PolynomialGF2N class. Object of PolynomialGF2N
     * class has to be specified by reducingPolynomial, which is used as
     * characteristic reducingPolynomial for computation with polynomials over
     * Galois Field.
     *
     * @param reducingPolynomial reducing polynomial for galoisField used for
     * computation with polynomials over Galois Field.
     *
     */
    public PolynomialGF2N(long reducingPolynomial) {
        galoisField = new GF2N(reducingPolynomial);
    }

    @Override
    public Polynomial add(Polynomial polynomial1, Polynomial polynomial2) {

        int length = Math.max(polynomial1.getSize(), polynomial2.getSize());
        Polynomial result = new Polynomial(length);

        for (int x = 0; x < length; x++) {
            try {
                long value = galoisField.add(polynomial1.getElement(x), polynomial2.getElement(x));
                result.setElement(x, value);
            } catch (IndexOutOfBoundsException ex) {
                if (x >= polynomial1.getSize()) {
                    result.setElement(x, polynomial2.getElement(x));
                } else {
                    result.setElement(x, polynomial1.getElement(x));
                }
            }
        }

        return result.clearZeroValues();
    }

    @Override
    public Polynomial subtract(Polynomial polynomial1, Polynomial polynomial2) {
        return add(polynomial1, polynomial2);
    }

    @Override
    public Polynomial multiply(Polynomial polynomial1, Polynomial polynomial2) {

        if (isZero(polynomial1) || isZero(polynomial2)) {
            return new Polynomial();
        }

        Polynomial result = new Polynomial(polynomial1.getSize() + polynomial2.getSize() - 1);

        for (int x = 0; x < polynomial2.getSize(); x++) {
            for (int y = 0; y < polynomial1.getSize(); y++) {
                if (polynomial1.getElement(y) != 0) {
                    int index = y + x;
                    long multiplyResult = galoisField.multiply(polynomial1.getElement(y), polynomial2.getElement(x));
                    long originalValue = result.getElement(index);
                    result.setElement(index, galoisField.add(originalValue, multiplyResult));
                }
            }
        }

        return result.clearZeroValues();
    }

    @Override
    public Polynomial divide(Polynomial polynomial1, Polynomial polynomial2) {

        if (isZero(polynomial2)) {
            throw new IllegalArgumentException("Division by zero!");
        }

        Polynomial remainder = new Polynomial(Math.max(polynomial1.getSize(), polynomial2.getSize()));
        return divide(polynomial1, polynomial2, remainder);
    }

    @Override
    public Polynomial divide(Polynomial polynomial1, Polynomial polynomial2, Polynomial remainder) {

        if (isZero(polynomial2)) {
            throw new IllegalArgumentException("Division by zero!");
        }

        Polynomial result;
        Polynomial rem;

        //prepare arrays of good length
        if (polynomial1.getSize() >= polynomial2.getSize()) {
            result = new Polynomial(polynomial1.getSize() - polynomial2.getSize() + 1);
            rem = new Polynomial(polynomial1.getSize() - polynomial2.getSize() + 1);
        } else {
            result = new Polynomial(1);
            rem = new Polynomial(polynomial1.getSize());
        }

        Polynomial numerator = polynomial1;
        Polynomial mulResult = new Polynomial(polynomial1.getSize());

        while ((numerator.getSize() >= polynomial2.getSize())) {

            //divide
            long value = galoisField.divide(numerator.getElement(numerator.getSize() - 1), polynomial2.getElement(polynomial2.getSize() - 1));
            result.setElement(numerator.getSize() - polynomial2.getSize(), value);

            //subtract divided part from numerator
            rem.setElement(numerator.getSize() - polynomial2.getSize(), value);
            mulResult = multiply(polynomial2, rem);
            numerator = subtract(numerator, mulResult);

            //set remainder to correct length necessary for next divison
            while ((numerator.getSize() - polynomial2.getSize() + 1) < rem.getSize()) {
                if (rem.getSize() == 0) {
                    break;
                } else {
                    Polynomial newRem = new Polynomial(rem.getSize() - 1);
                    for (int x = 0; x < newRem.getSize(); x++) {
                        newRem.setElement(x, rem.getElement(x));
                    }
                    rem = newRem;
                }
            }
        }

        //copy remainder of division to remainder attribute of this method
        remainder.setSize(numerator.getSize());
        for (int x = 0; x < numerator.getSize(); x++) {
            remainder.setElement(x, numerator.getElement(x));
        }

        return result;
    }

    // result = gcd(polynomial1, polynomial2) = a*polynomial1 + b*polynomial2
    private Polynomial xgcd(Polynomial polynomial1, Polynomial polynomial2, Polynomial a, Polynomial b) {

        if (polynomial2.getSize() > polynomial1.getSize()) {
            //swap
            Polynomial swapPoly = polynomial1;
            polynomial1 = polynomial2;
            polynomial2 = swapPoly;
            swapPoly = a;
            a = b;
            b = swapPoly;
        }

        //prepare for division
        Polynomial result = new Polynomial(polynomial2.getSize());
        Polynomial remainder = polynomial2;
        Polynomial numerator = polynomial1;
        Polynomial denumerator = polynomial2;

        //prepare to find Bezout's identity
        Polynomial temp;
        ArrayList<Polynomial> resultList = new ArrayList<>();
        ArrayList<Polynomial> bezoutIdentity = new ArrayList<>();


        //find greatest greatest common divisor, last positive remainder
        while (!(remainder.getSize() == 0)) {
            result = remainder;
            remainder = new Polynomial(Math.max(numerator.getSize(), denumerator.getSize()));
            temp = divide(numerator, denumerator, remainder);

            //resultList data neccessary to find Bezout's identity
            resultList.add(numerator);
            resultList.add(denumerator);
            resultList.add(temp);

            remainder.clearZeroValues();
            numerator = denumerator;
            denumerator = remainder;
        }

        if (resultList.size() > 3) {
            //we don't need last 3 values
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
        }

        Polynomial value = new Polynomial(1, 1);
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
        for (int x = 0; x < bezoutIdentity.get(0).getSize(); x++) {
            a.setElement(x, bezoutIdentity.get(0).getElement(x));
        }
        for (int x = 0; x < bezoutIdentity.get(3).getSize(); x++) {
            b.setElement(x, bezoutIdentity.get(3).getElement(x));
        }

        //gcd, not normalized
        return result;
    }

    @Override
    public Polynomial gcd(Polynomial polynomial1, Polynomial polynomial2) {

        if (!isZero(polynomial1) && isZero(polynomial2)) {
            return polynomial1;
        }
        if (isZero(polynomial1) && !isZero(polynomial2)) {
            return polynomial2;
        }
        if (isZero(polynomial1) && isZero(polynomial2)) {
            return new Polynomial(0);
        }

        Polynomial result = new Polynomial(polynomial2.getSize());
        Polynomial remainder = polynomial2;
        Polynomial numerator = polynomial1;
        Polynomial denumerator = polynomial2;


        //find greatest greatest common divisor, last positive remainder
        while (!(remainder.getSize() == 0)) {
            result = remainder;
            remainder = new Polynomial(Math.max(numerator.getSize(), denumerator.getSize()));
            divide(numerator, denumerator, remainder);
            remainder.clearZeroValues();
            numerator = denumerator;
            denumerator = remainder;
        }

        //make gcd monic
        Polynomial monicDiv = new Polynomial(1);
        monicDiv.setElement(0, result.getElement(result.getSize() - 1));
        return divide(result, monicDiv);

    }

    @Override
    public Polynomial invert(Polynomial polynomial, Polynomial moduloPolynomial) {

        if (isZero(polynomial) || isZero(moduloPolynomial)) {
            throw new IllegalArgumentException("Cannot compute inverse for zero polynomials.");
        }

        if ((polynomial.getSize() >= moduloPolynomial.getSize()) || (moduloPolynomial.getSize() == 1)) {
            throw new IllegalArgumentException("Cannot compute inverse for this args.");
        }

        if (polynomial.getSize() == 1) {
            //special solution for special case :)
            return divide(add(new Polynomial(1, 1), multiply(moduloPolynomial, polynomial)), polynomial);
        }

        Polynomial gcd;
        Polynomial result = new Polynomial(moduloPolynomial.getSize());
        Polynomial temp = new Polynomial(moduloPolynomial.getSize());

        //xgcd to set the result
        gcd = xgcd(moduloPolynomial, polynomial, temp, result);

        if (gcd.getSize() != 1) {
            throw new IllegalArgumentException("Cannot compute inverse for this args.");
        }

        //normalize result, after this, remainder after division by moduloPolynomial will be 1
        result.clearZeroValues();
        return divide(result, gcd);
    }

    @Override
    public Polynomial power(Polynomial polynomial, long exponent) {

        if (exponent <= 0) {
            throw new IllegalArgumentException("Exponent must be positive number!");
        }

        if (isZero(polynomial)) {
            return new Polynomial();
        }

        Polynomial result = polynomial;

        for (int x = 1; x < exponent; x++) {
            result = multiply(polynomial, result);
        }

        return result;
    }

    //check, if polynomial is not equal to zero
    private boolean isZero(Polynomial polynomial) {
        if (polynomial.getSize() == 0) {
            return true;
        }

        for (int x = 0; x < polynomial.getSize(); x++) {
            if (polynomial.getElement(x) != 0) {
                return false;
            }
        }
        return true;
    }
}

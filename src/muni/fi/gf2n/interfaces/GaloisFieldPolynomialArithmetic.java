package muni.fi.gf2n.interfaces;

import muni.fi.gf2n.classes.Polynomial;

/**
 * Interface GaloisFieldPolynomialArithmetic describes basic operations for
 * computation with polynomials with elements in Galois Fields.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldPolynomialArithmetic {

    /**
     * This method implements addition of two polynomials with elements in
     * Galois Field. Polynomials are taken as parameters and new polynomial is
     * returned.
     *
     * @param polynomial1 augend polynomial
     * @param polynomial2 addend polynomial
     * @return sum of polynomial1 and polynomial2
     */
    Polynomial add(Polynomial polynomial1, Polynomial polynomial2);

    /**
     * This method implements subtraction of two polynomials with elements in
     * Galois Field. Polynomials are taken as parameters and new polynomial is
     * returned.
     *
     * @param polynomial1 minuend polynomial
     * @param polynomial2 subtrahend polynomial
     * @return difference of polynomial1 and polynomial2
     */
    Polynomial subtract(Polynomial polynomial1, Polynomial polynomial2);

    /**
     * This method implements multiplication of two polynomials with elements in
     * Galois Field. Polynomials are taken as parameters and new polynomial is
     * returned.
     *
     * @param polynomial1 multiplicand polynomial1
     * @param polynomial2 multiplier polynomial2
     * @return product of polynomial1 and polynomial2
     */
    Polynomial multiply(Polynomial polynomial1, Polynomial polynomial2);

    /**
     * This method implements division of two polynomials with elements in
     * Galois Field. Polynomials are taken as parameters and new polynomial is
     * returned.
     *
     * @param polynomial1 dividend polynomial
     * @param polynomial2 divisor polynomial
     * @return quotient of polynomial1 and polynomial2
     */
    Polynomial divide(Polynomial polynomial1, Polynomial polynomial2);

    /**
     * This method implements division of two polynomials with elements in
     * Galois Field. Polynomials are taken as parameters and new polynomial is
     * returned. Remainder of division is set to remainder parameter of this
     * method.
     *
     * @param polynomial1 dividend polynomial
     * @param polynomial2 divisor polynomial
     * @param remainder remainder of division will be set to this parameter
     * @return quotient of polynomial1 and polynomial2
     */
    Polynomial divide(Polynomial polynomial1, Polynomial polynomial2, Polynomial remainder);

    /**
     * Euclidean algorithm is used in this method. Greatest common divisor of
     * polynomials taken as parameters is returned.
     *
     * @param polynomial1 polynomial1
     * @param polynomial2 polynomial2
     * @return greatest common divisor of polynomial1 and polynomial2
     */
    Polynomial gcd(Polynomial polynomial1, Polynomial polynomial2);

    /**
     * This method computes inverse of polynomial in Galois Field. Inverse is
     * computed for polynomial and modulo polynomial. 1 = (polynomial *
     * invert(polynomial)) mod moduloPolynomial.
     *
     * @param polynomial polynomial to invert
     * @param moduloPolynomial modulo polynomial
     * @return inverse of input polynomial
     */
    Polynomial invert(Polynomial polynomial, Polynomial moduloPolynomial);

    /**
     * Compute power of polynomial powered to exponent.
     *
     * @param polynomial polynomial to exponentiate
     * @param exponent exponent of power function
     * @return power of polynomial
     */
    Polynomial power(Polynomial polynomial, long exponent);
}

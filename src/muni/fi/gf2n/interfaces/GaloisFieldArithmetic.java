package muni.fi.gf2n.interfaces;

/**
 * Interface GaloisFieldArithmetic describes basic operations for computation
 * with values in Galois Fields.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldArithmetic {

    /**
     * This method implements addition of two elements of Galois Field. Elements
     * are taken as parameters and new value is returned.
     *
     * @param element1 augend
     * @param element2 addend
     * @return sum of element1 and element2
     */
    long add(long element1, long element2);

    /**
     * This method implements subtraction of two elements of Galois Field.
     * Elements are taken as parameters and new value is returned.
     *
     * @param element1 minuend
     * @param element2 subtrahend
     * @return difference of element1 and element2
     */
    long subtract(long element1, long element2);

    /**
     * This method implements multiplication of two elements of Galois Field.
     * Elements are taken as parameters and new value is returned.
     *
     * @param element1 multiplicand
     * @param element2 multiplier
     * @return product of element1 and element2
     */
    long multiply(long element1, long element2);

    /**
     * This method implements division of two elements of Galois Field. Elements
     * are taken as parameters and new value is returned.
     *
     * @param element1 dividend
     * @param element2 divisor
     * @return quotient of element1 and element2
     */
    long divide(long element1, long element2);

    /**
     * Method Invert computes inverse of element taken as parameter in Galois
     * Field.
     *
     * @param element value to invert
     * @return inverse of element
     */
    long invert(long element);

    /**
     * Computes power of element in Galois Field.
     *
     * @param element value to exponentiate
     * @param exponent exponent of power function
     * @return power of element
     */
    long power(long element, long exponent);

}

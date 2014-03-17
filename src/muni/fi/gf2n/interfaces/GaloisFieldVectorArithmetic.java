package muni.fi.gf2n.interfaces;

import muni.fi.gf2n.classes.Vector;

/**
 * Interface GaloisFieldVectorArithmetic describes basic operations for
 * computation with vectors with elements in Galois Fields.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldVectorArithmetic {

    /**
     * This method implements addition of two vectors with elements in Galois
     * Field. Vectors are taken as parameters and new vector is returned.
     *
     * @param vector1 augend vector
     * @param vector2 addend vector
     * @return sum of vector1 and vector2
     */
    Vector add(Vector vector1, Vector vector2);

    /**
     * This method implements subtraction of two vectors with elements in Galois
     * Field. Vectors are taken as parameters and new vector is returned.
     *
     * @param vector1 minuend vector
     * @param vector2 subtrahend vector
     * @return difference of vector1 and vector2
     *
     */
    Vector subtract(Vector vector1, Vector vector2);

    /**
     * This method implements multiplication of vector with elements in Galois
     * Field by scalar value.
     *
     * @param vector input vector
     * @param scalarValue scalar value
     * @return product of vector and scalarValue
     */
    Vector multiply(Vector vector, long scalarValue);
}

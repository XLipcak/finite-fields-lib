package muni.fi.gf2n.classes;

import muni.fi.gf2n.exceptions.DimensionMismatchException;
import muni.fi.gf2n.interfaces.GaloisFieldVectorArithmetic;

/**
 * Class VectorGF2N implements interface GaloisFieldVectorArithmetic for
 * computation with vectors in Galois Fields. Object of VectorGF2N class has to
 * be specified by reducing polynomial for Galois Field, or by object of class
 * GF2N.
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class VectorGF2N implements GaloisFieldVectorArithmetic {

    private GF2N galoisField;

    /**
     * Constructs an object of VectorGF2N class. Object of VectorGF2N class has
     * to be specified by galoisField, which is used for computation with
     * vectors over Galois Field.
     *
     * @param galoisField GF2N galoisField used for computation with vectors
     * over Galois Field
     *
     */
    public VectorGF2N(GF2N galoisField) {
        this.galoisField = galoisField;
    }

    /**
     * Constructs an object of VectorGF2N class. Object of VectorGF2N class has
     * to be specified by reducingPolynomial, which is used as characteristic
     * reducingPolynomial for computation with vectors over Galois Field.
     *
     * @param reducingPolynomial reducing polynomial for galoisField used for
     * computation with vectors over Galois Field.
     *
     */
    public VectorGF2N(long reducingPolynomial) {
        galoisField = new GF2N(reducingPolynomial);
    }

    @Override
    public Vector add(Vector vector1, Vector vector2) {
        isValid(vector1, vector2);

        Vector result = new Vector(vector1.getSize());

        for (int x = 0; x < vector1.getSize(); x++) {
            long value = galoisField.add(vector1.getElement(x), vector2.getElement(x));
            result.setElement(x, value);
        }

        return result;
    }

    @Override
    public Vector subtract(Vector vector1, Vector vector2) {
        isValid(vector1, vector2);
        return add(vector1, vector2);
    }

    @Override
    public Vector multiply(Vector vector, long scalarValue) {
        isValid(vector);

        Vector result = new Vector(vector.getSize());

        for (int x = 0; x < vector.getSize(); x++) {
            long value = galoisField.multiply(vector.getElement(x), scalarValue);
            result.setElement(x, value);
        }

        return result;
    }

    private void isValid(Vector vect1, Vector vect2) {

        if (vect1.getSize() == 0 || vect2.getSize() == 0) {
            throw new DimensionMismatchException("Vector argument is empty.");
        }

        if (vect1.getSize() != vect2.getSize()) {
            throw new DimensionMismatchException("Operation cannot be performed with vectors of different length.");
        }
    }

    private void isValid(Vector vect) {

        if (vect.getSize() == 0) {
            throw new DimensionMismatchException("Vector argument is empty.");
        }
    }
}

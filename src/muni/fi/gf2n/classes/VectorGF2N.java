/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.classes;

import muni.fi.gf2n.interfaces.GaloisFieldVectorArithmetic;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 * 
 * Class VectorGF2N 
 */
public class VectorGF2N implements GaloisFieldVectorArithmetic {

    private GF2N galoisField;

    public VectorGF2N(GF2N galoisField) {
        this.galoisField = galoisField;
    }

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
            throw new IllegalArgumentException("Vector argument is empty.");
        }

        if (vect1.getSize() != vect2.getSize()) {
            throw new IllegalArgumentException("Operation cannot be performed with vectors of different length.");
        }
    }

    private void isValid(Vector vect) {

        if (vect.getSize() == 0) {
            throw new IllegalArgumentException("Vector argument is empty.");
        }
    }
}

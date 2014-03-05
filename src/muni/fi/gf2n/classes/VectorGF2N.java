/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.classes;

import muni.fi.gf2n.interfaces.GaloisFieldVectorArithmetic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Lipcak, Masaryk University
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
    public long[] add(long[] vector1, long[] vector2) {
        isValid(vector1, vector2);

        long[] result = new long[vector1.length];

        for (int x = 0; x < vector1.length; x++) {
            long value = galoisField.add(vector1[x], vector2[x]);
            result[x] = value;
        }

        return result;
    }

    @Override
    public long[] subtract(long[] vector1, long[] vector2) {
        isValid(vector1, vector2);
        return add(vector1, vector2);
    }

    @Override
    public long[] multiply(long[] vector, long scalarValue) {
        isValid(vector);

        long[] result = new long[vector.length];

        for (int x = 0; x < vector.length; x++) {
            long value = galoisField.multiply(vector[x], scalarValue);
            result[x] = value;
        }

        return result;
    }

    private void isValid(long[] vect1, long[] vect2) {

        if (vect1.length == 0 || vect2.length == 0) {
            throw new IllegalArgumentException("Vector argument is empty.");
        }

        if (vect1.length != vect2.length) {
            throw new IllegalArgumentException("Operation cannot be performed with vectors of different length.");
        }
    }

    private void isValid(long[] vect) {

        if (vect.length == 0) {
            throw new IllegalArgumentException("Vector argument is empty.");
        }
    }
}

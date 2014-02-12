/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.library;

import computation.with.finite.fields.library.interfaces.GaloisFieldVectorArithmetic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class VectorGF2N implements GaloisFieldVectorArithmetic {

    GF2N galoisField;

    public VectorGF2N(GF2N galoisField) {
        this.galoisField = galoisField;
    }

    public VectorGF2N(long reducingPolynomial) {
        galoisField = new GF2N(reducingPolynomial);
    }

    @Override
    public ArrayList<Long> add(List<Long> vector1, List<Long> vector2) {

        isValid(vector1, vector2);

        if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("Vectors must have the same length.");
        }

        ArrayList<Long> result = new ArrayList<>();

        for (int x = 0; x < vector1.size(); x++) {
            long value = galoisField.add(vector1.get(x), vector2.get(x));
            result.add(value);
        }

        return result;
    }

    @Override
    public ArrayList<Long> subtract(List<Long> vector1, List<Long> vector2) {
        isValid(vector1, vector2);
        return add(vector1, vector2);
    }

    @Override
    public ArrayList<Long> multiply(List<Long> vector, long scalarValue) {

        isValid(vector);

        ArrayList<Long> result = new ArrayList<>();

        for (int x = 0; x < vector.size(); x++) {
            long value = galoisField.multiply(vector.get(x), scalarValue);
            result.add(value);
        }

        return result;
    }

    @Override
    public ArrayList<Long> transpose(List<Long> vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compare(List<Long> vector1, List<Long> vector2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void isValid(List<Long> vect1, List<Long> vect2) {

        if (vect1.isEmpty() || vect2.isEmpty()) {
            throw new IllegalArgumentException("Vector argument is empty.");
        }

    }

    private void isValid(List<Long> vect) {

        if (vect.isEmpty()) {
            throw new IllegalArgumentException("Vector argument is empty.");
        }

    }
}

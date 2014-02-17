/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.classes;

import muni.fi.gf2n.interfaces.GaloisFieldMatrixArithmetic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class MatrixGF2N implements GaloisFieldMatrixArithmetic {

    GF2N galoisField;

    public MatrixGF2N(GF2N galoisField) {
        this.galoisField = galoisField;
    }

    public MatrixGF2N(long reducingPolynomial) {
        galoisField = new GF2N(reducingPolynomial);
    }

    @Override
    public ArrayList<ArrayList<Long>> add(List<List<Long>> matrix1, List<List<Long>> matrix2) {
        isValid(matrix1, matrix2);

        ArrayList<ArrayList<Long>> result = new ArrayList<>();
        for (int row = 0; row < matrix1.size(); row++) {

            ArrayList<Long> resultRow = new ArrayList<>();
            for (int col = 0; col < matrix1.get(0).size(); col++) {
                resultRow.add(galoisField.add(matrix1.get(row).get(col), matrix2.get(row).get(col)));
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public ArrayList<ArrayList<Long>> subtract(List<List<Long>> matrix1, List<List<Long>> matrix2) {
        isValid(matrix1, matrix2);
        return add(matrix1, matrix2);
    }

    @Override
    public ArrayList<ArrayList<Long>> transpose(List<List<Long>> matrix) {
        isValid(matrix);

        ArrayList<ArrayList<Long>> result = new ArrayList<>();
        for (int col = 0; col < matrix.size(); col++) {

            ArrayList<Long> resultRow = new ArrayList<>();
            for (int row = 0; row < matrix.get(0).size(); row++) {
                resultRow.add(matrix.get(row).get(col));
            }
            result.add(resultRow);
        }
        return result;
    }

    @Override
    public ArrayList<ArrayList<Long>> multiply(List<List<Long>> matrix1, List<List<Long>> matrix2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ArrayList<Long>> multiply(List<List<Long>> matrix, long scalarValue) {
        isValid(matrix);

        ArrayList<ArrayList<Long>> result = new ArrayList<>();
        for (int row = 0; row < matrix.size(); row++) {

            ArrayList<Long> resultRow = new ArrayList<>();
            for (int col = 0; col < matrix.get(0).size(); col++) {
                resultRow.add(galoisField.multiply(matrix.get(row).get(col), scalarValue));
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public ArrayList<ArrayList<Long>> multiplyByVector(List<List<Long>> matrix, List<Long> vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ArrayList<Long>> inverse(List<List<Long>> matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ArrayList<Long>> power(List<List<Long>> matrix, long exponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long determinant(List<List<Long>> matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long rank(List<List<Long>> matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ArrayList<Long>> gauss(List<List<Long>> matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Long> solveLinearEquationsSystem(List<List<Long>> equationMatrix, List<Long> results) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Long> image(List<List<Long>> matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Long> kernel(List<List<Long>> matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compare(List<List<Long>> matrix1, List<List<Long>> matrix2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void isValid(List<List<Long>> matrix1, List<List<Long>> matrix2) {

        if (matrix1.isEmpty() || matrix2.isEmpty()) {
            throw new IllegalArgumentException("Matrix argument is empty.");
        }

        try {
            int length = matrix1.get(0).size();
            if (length == 0) {
                throw new IllegalArgumentException("Argument matrix has empty row.");
            }

            for (int position = 0; position < matrix1.size(); position++) {
                if ((matrix1.get(position).size() != length) || (matrix2.get(position).size() != length)) {
                    throw new IndexOutOfBoundsException("Thrown to be catched.");
                }
            }

        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Argument matrices have different dimensions or rows with "
                    + "different lengths, operation cannot be performed.");
        }


    }

    private void isValid(List<List<Long>> matrix) {

        if (matrix.isEmpty()) {
            throw new IllegalArgumentException("Matrix argument is empty.");
        }

        int length = matrix.get(0).size();
        if (length == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row.");
        }

        for (int position = 1; position < matrix.size(); position++) {
            if ((matrix.get(position).size() != length)) {
                throw new IllegalArgumentException("Argument matrices have different dimensions or rows with "
                        + "different lengths, operation cannot be performed.");
            }
        }
    }
}

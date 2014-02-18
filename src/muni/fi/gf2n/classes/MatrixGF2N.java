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
    public long[][] add(long[][] matrix1, long[][] matrix2) {
        isValid(matrix1, matrix2);

        long[][] result = new long[matrix1.length][matrix1[0].length];
        for (int row = 0; row < matrix1.length; row++) {

            for (int col = 0; col < matrix1[0].length; col++) {
                result[row][col] = galoisField.add(matrix1[row][col], matrix2[row][col]);
            }
        }

        return result;
    }

    @Override
    public long[][] subtract(long[][] matrix1, long[][] matrix2) {
        isValid(matrix1, matrix2);
        return add(matrix1, matrix2);
    }

    @Override
    public long[][] transpose(long[][] matrix) {
        isValid(matrix);

        long[][] result = new long[matrix[0].length][matrix.length];
        for (int col = 0; col < matrix.length; col++) {

            for (int row = 0; row < matrix[0].length; row++) {
                result[row][col] = matrix[col][row];
            }
        }
        return result;
    }

    @Override
    public long[][] multiply(long[][] matrix1, long[][] matrix2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[][] multiply(long[][] matrix, long scalarValue) {
        isValid(matrix);

        long[][] result = new long[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {

            for (int col = 0; col < matrix[0].length; col++) {
                result[row][col] = galoisField.multiply(matrix[row][col], scalarValue);
            }
        }

        return result;
    }

    @Override
    public long[][] multiplyByVector(long[][] matrix, List<Long> vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[][] inverse(long[][] matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[][] power(long[][] matrix, long exponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long determinant(long[][] matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long rank(long[][] matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[][] gauss(long[][] matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[] solveLinearEquationsSystem(long[][] equationMatrix, long[] results) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[] image(long[][] matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[] kernel(long[][] matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compare(long[][] matrix1, long[][] matrix2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void isValid(long[][] matrix1, long[][] matrix2) {

        if (matrix1.length == 0 || matrix2.length == 0) {
            throw new IllegalArgumentException("Matrix argument is empty.");
        }

        try {
            int length = matrix1[0].length;
            if (length == 0) {
                throw new IllegalArgumentException("Argument matrix has empty row.");
            }

            for (int position = 0; position < matrix1.length; position++) {
                if ((matrix1[position].length != length) || (matrix2[position].length != length)) {
                    throw new IllegalArgumentException("Argument matrices have different dimensions or rows with "
                            + "different lengths, operation cannot be performed.");
                }
            }

        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Argument matrices have different dimensions or rows with "
                    + "different lengths, operation cannot be performed.");
        }


    }

    private void isValid(long[][] matrix) {

        if (matrix.length == 0) {
            throw new IllegalArgumentException("Matrix argument is empty.");
        }

        int length = matrix[0].length;
        if (length == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row.");
        }

        for (int position = 1; position < matrix.length; position++) {
            if ((matrix[position].length != length)) {
                throw new IllegalArgumentException("Argument matrices have different dimensions or rows with "
                        + "different lengths, operation cannot be performed.");
            }
        }
    }
}

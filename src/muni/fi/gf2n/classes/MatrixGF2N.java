/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import muni.fi.gf2n.interfaces.GaloisFieldMatrixArithmetic;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 *
 * Class MatrixGF2N for computation with Matrices in Galois Fields.
 *
 */
public class MatrixGF2N implements GaloisFieldMatrixArithmetic {

    private GF2N galoisField;

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
        long[][] result = new long[matrix1.length][matrix2[0].length];

        if (matrix1.length == 0 || matrix2.length == 0) {
            throw new IllegalArgumentException("Matrix argument is empty, "
                    + "operation cannot be performed.");
        }

        if (matrix1[0].length == 0 || matrix2[0].length == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row, "
                    + "operation cannot be performed.");
        }

        if (matrix1[0].length != matrix2.length) {
            throw new IllegalArgumentException("Argument matrices cannot be multiplied, "
                    + "their dimensions are wrong.");
        }


        for (int x = 0; x < matrix1.length; x++) {
            for (int y = 0; y < matrix2[0].length; y++) {
                long value = 0;
                for (int z = 0; z < matrix1[0].length; z++) {
                    value = galoisField.add(value, galoisField.multiply(matrix1[x][z], matrix2[z][y]));
                }
                result[x][y] = value;
            }
        }

        return result;
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
    public long[][] multiply(long[][] matrix, long[] vector) {
        long matrixVector[][] = new long[vector.length][1];

        for (int x = 0; x < vector.length; x++) {
            matrixVector[x][0] = vector[x];
        }
        return multiply(matrix, matrixVector);
    }

    @Override
    public long[][] multiply(long[] vector, long[][] matrix) {
        long matrixVector[][] = new long[1][vector.length];

        for (int x = 0; x < vector.length; x++) {
            matrixVector[0][x] = vector[x];
        }
        return multiply(matrixVector, matrix);
    }

    @Override
    public long[][] inverse(long[][] matrix) {

        isValid(matrix);

        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("Cannot compute inverse of nonsquare matrix.");
        }

        //NESKOR MOZNO KONTROLOVAT NEJAK INAK, PRE VELKE MATICE POMALE
        if (determinant(matrix) == 0) {
            throw new IllegalArgumentException("Matrix is non-invertible.");
        }

        //Prepare double-wide inverseMatrix, created from original matrix, and in the other half from identity matrix
        long[][] inverseMatrix = new long[matrix.length][matrix[0].length * 2];
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < (matrix[0].length * 2); y++) {
                if (y < matrix[0].length) {
                    inverseMatrix[x][y] = matrix[x][y];
                } else {
                    if (x == y - matrix.length) {
                        inverseMatrix[x][y] = 1;
                    }
                }
            }
        }

        //Modified Gauss elimination, reduce all rows under the main diagonal, apply the changes to identity matrix 
        for (int diagPos = 1; diagPos < Math.min(inverseMatrix.length, inverseMatrix[0].length) + 1; diagPos++) {

            long value;
            for (int rowsUnderDiagPos = diagPos; rowsUnderDiagPos < inverseMatrix.length; rowsUnderDiagPos++) {

                //find row with pivot at column, that we want to set to zero
                if (inverseMatrix[diagPos - 1][diagPos - 1] == 0) {
                    inverseMatrix = findPivot(inverseMatrix, diagPos - 1, diagPos - 1);
                }

                try {
                    //set value, it will be used to set column at diagPos to zero
                    value = galoisField.divide(inverseMatrix[rowsUnderDiagPos][diagPos - 1], inverseMatrix[diagPos - 1][diagPos - 1]);

                    //subtract from line, pivot will be set to zero and other values will be edited
                    for (int colsUnderDiagPos = diagPos - 1; colsUnderDiagPos < inverseMatrix[0].length; colsUnderDiagPos++) {
                        inverseMatrix[rowsUnderDiagPos][colsUnderDiagPos] = galoisField.subtract(galoisField.multiply(inverseMatrix[diagPos - 1][colsUnderDiagPos], value), inverseMatrix[rowsUnderDiagPos][colsUnderDiagPos]);
                    }
                } catch (IllegalArgumentException ex) {
                    //division by zero, column full of zeroes, special case, check it later
                }
            }
        }

        //Modified Gauss elimination, reduce all rows over the main diagonal, apply the changes to identity matrix 
        for (int diagPos = inverseMatrix.length - 1; diagPos >= 0; diagPos--) {

            long value;
            for (int rowsOverDiagPos = diagPos - 1; rowsOverDiagPos >= 0; rowsOverDiagPos--) {

                try {
                    //set value, it will be used to set column at diagPos to zero
                    value = galoisField.divide(inverseMatrix[rowsOverDiagPos][diagPos], inverseMatrix[diagPos][diagPos]);

                    //subtract from line, pivot will be set to zero and other values will be edited
                    for (int colsOverDiagPos = diagPos; colsOverDiagPos < inverseMatrix[0].length; colsOverDiagPos++) {
                        inverseMatrix[rowsOverDiagPos][colsOverDiagPos] = galoisField.subtract(galoisField.multiply(inverseMatrix[diagPos][colsOverDiagPos], value), inverseMatrix[rowsOverDiagPos][colsOverDiagPos]);
                    }
                } catch (IllegalArgumentException ex) {
                    //division by zero, column full of zeroes, special case, check it later
                }
            }
        }

        //Prepare result, it is the second half of the inverseMatrix
        long[][] result = new long[matrix.length][matrix.length];
        for (int x = 0; x < inverseMatrix.length; x++) {
            long value = inverseMatrix[x][x];
            for (int y = 0; y < inverseMatrix[0].length; y++) {
                //First half of the inverseMatrix is diagonal matrix, we divide it to create identity matrix 
                inverseMatrix[x][y] = galoisField.divide(inverseMatrix[x][y], value);
                if (y >= matrix.length) {
                    result[x][y - matrix.length] = inverseMatrix[x][y];
                }
            }
        }

        return result;
    }

    @Override
    public long[][] power(long[][] matrix, long exponent) {

        isValid(matrix);

        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("Cannot compute power of non-square matrix!");
        }

        if (exponent <= 0) {
            throw new IllegalArgumentException("Exponent must be positive number!");
        }

        long[][] result = matrix;
        for (int x = 1; x < exponent; x++) {
            result = multiply(matrix, result);
        }

        return result;
    }

    //Laplace expansion
    @Override
    public long determinant(long[][] matrix) {

        isValid(matrix);

        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("Cannot compute determinant of nonsquare matrix.");
        }

        if (matrix.length == 1) {
            return matrix[0][0];
        }

        long result = 0;
        for (int x = 0; x < matrix.length; x++) {
            //Recursive call is used to compute determinant with Laplace expansion along the first row
            result = galoisField.add(result, galoisField.multiply(matrix[0][x], determinant(subMatrix(matrix, 0, x))));
        }

        return result;

    }

    @Override
    public int rank(long[][] matrix) {

        isValid(matrix);

        int rank = 0;
        long[][] result = gauss(matrix);

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                if (result[x][y] != 0) {
                    rank++;
                    break;
                }
            }
        }
        return rank;
    }

    //return input matrix in row echelon form
    @Override
    public long[][] gauss(long[][] matrix) {

        isValid(matrix);

        //prepare result
        long[][] result = new long[matrix.length][matrix[0].length];
        for (int x = 0; x < matrix.length; x++) {
            System.arraycopy(matrix[x], 0, result[x], 0, matrix[0].length);
        }

        for (int diagPos = 1; diagPos < Math.min(matrix.length, matrix[0].length) + 1; diagPos++) {

            long value;

            //find column with pivot, swap lines if necessary
            int colPos = diagPos - 1;
            while (colPos < matrix[0].length && result[diagPos - 1][colPos] == 0) {
                result = findPivot(result, colPos, diagPos - 1);
                if (result[diagPos - 1][colPos] == 0) {
                    colPos++;
                }
            }
            if (colPos == matrix[0].length) {
                colPos = diagPos - 1;
            }

            for (int rowsUnderDiagPos = diagPos; rowsUnderDiagPos < matrix.length; rowsUnderDiagPos++) {

                try {
                    //set value, it will be used to set column at diagPos to zero
                    value = galoisField.divide(result[rowsUnderDiagPos][colPos], result[diagPos - 1][colPos]);

                    //subtract from line, pivot will be set to zero and other values will be edited
                    for (int colsUnderDiagPos = diagPos - 1; colsUnderDiagPos < matrix[0].length; colsUnderDiagPos++) {
                        result[rowsUnderDiagPos][colsUnderDiagPos] = galoisField.subtract(galoisField.multiply(result[diagPos - 1][colsUnderDiagPos], value), result[rowsUnderDiagPos][colsUnderDiagPos]);
                    }
                } catch (IllegalArgumentException ex) {
                    //catched division by zero, OK, thrown by columns full of zeroes
                    if (ex.toString().contains("Values for this reducing polynomial must be in")) {
                        //also catched, when some values are not in GF, new exception is thrown then
                        throw new IllegalArgumentException(ex);
                    }
                }
            }
        }

        //Result matrix is in row echelon form
        return result;
    }

    //equationMatrix*result = results
    @Override
    public long[] solveLinearEquationsSystem(long[][] equationMatrix, long[] results) {

        isValid(equationMatrix);

        if (equationMatrix.length != equationMatrix[0].length) {
            throw new IllegalArgumentException("Cannot solve linear equations system for nonsquare matrix.");
        }

        if (equationMatrix.length != results.length) {
            throw new IllegalArgumentException("Cannot solve linear equations system: dimension mismatch.");
        }

        if (rank(equationMatrix) != equationMatrix.length) {
            throw new IllegalArgumentException("Cannot solve linear equations system: linearly dependent rows.");
        }


        //prepare equation matrix
        long[][] eqMat = new long[equationMatrix.length][equationMatrix[0].length + 1];
        for (int x = 0; x < equationMatrix.length; x++) {
            System.arraycopy(equationMatrix[x], 0, eqMat[x], 0, equationMatrix[0].length);
            eqMat[x][equationMatrix[0].length] = results[x];
        }
        
        eqMat = gauss(eqMat);

        //set result vector from values prepared in eqMat
        long[] result = new long[equationMatrix[0].length];
        for (int x = eqMat[0].length - 2; x >= 0; x--) {
            for (int y = eqMat[0].length - 2; y >= x; y--) {
                if (y == x) {
                    result[x] = galoisField.divide(eqMat[x][eqMat[0].length - 1], eqMat[x][x]);
                } else {
                    eqMat[x][eqMat[0].length - 1] = galoisField.subtract(eqMat[x][eqMat[0].length - 1],
                            galoisField.multiply(eqMat[x][y], result[y]));
                }
            }
        }

        return result;
    }

    //image of matrix
    @Override
    public long[][] image(long[][] matrix) {
        isValid(matrix);
        return reduceLinearlyDependentRows(matrix);
    }

    //Kernel(Matrix) * (Matrix) = zeroMatrix
    @Override
    public long[][] kernel(long[][] matrix) {
        isValid(matrix);

        long[][] kernelMatrix = transpose(matrix);
        kernelMatrix = gauss(kernelMatrix);

        //prepare result matrix
        long[][] result = new long[matrix.length - /*rank(matrix)*/ matrix[0].length][matrix.length];
        for (int x = 0; x < result.length; x++) {
            result[x][result[0].length - x - 1] = 1;
        }

        HashSet rowsToDelete = new HashSet();

        int index = result.length;
        int position = 0;
        for (int counter = result.length - 1; counter >= 0; counter--) {

            index--;
            position++;

            //set kernel vector for every row
            for (int row = kernelMatrix.length - 1; row >= 0; row--) {

                long value = 0;
                //loop used to find a good value in current row
                for (int col = row + 1; col <= kernelMatrix[0].length - 1; col++) {
                    value = galoisField.add(value, galoisField.multiply(kernelMatrix[row][col], result[index][col]));
                }

                if (row == kernelMatrix.length - 1) {
                    long numerator = kernelMatrix[row][(kernelMatrix.length - 1) + position];
                    long denumerator = kernelMatrix[row][row];
                    if (denumerator != 0) {
                        result[index][row] = galoisField.divide(numerator, denumerator);
                    } else {
                        if (numerator == 0) {
                            result[index][row] = 0;
                        } else {
                            rowsToDelete.add(counter);
                        }
                    }

                } else {
                    if (kernelMatrix[row][row] != 0) {
                        result[index][row] = galoisField.divide(value, kernelMatrix[row][row]);
                    } else {
                        if (value == 0) {
                            result[index][row] = 0;
                        } else {
                            rowsToDelete.add(counter);
                        }
                    }
                }
            }
        }

        //For some matrices, kernel cannot be found for every parameter
        //deleteRows is called to delete this rows from result matrix
        return deleteRows(result, rowsToDelete);
    }

    private void isValid(long[][] matrix1, long[][] matrix2) {

        if (matrix1.length == 0 || matrix2.length == 0) {
            throw new IllegalArgumentException("Matrix argument is empty, "
                    + "operation cannot be performed.");
        }

        if (matrix1[0].length == 0 || matrix2[0].length == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row, "
                    + "operation cannot be performed.");
        }

        if ((matrix1[0].length != matrix2[0].length) || (matrix1.length != matrix2.length)) {
            throw new IllegalArgumentException("Argument matrices have different dimensions, "
                    + "operation cannot be performed.");
        }
    }

    private void isValid(long[][] matrix) {

        if (matrix.length == 0) {
            throw new IllegalArgumentException("Matrix argument is empty, "
                    + "operation cannot be performed.");
        }

        int length = matrix[0].length;
        if (length == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row, "
                    + "operation cannot be performed.");
        }

    }

    //change line row with line with pivot at position column
    private long[][] findPivot(long[][] matrix, int column, int row) {

        for (int x = row; x < matrix.length; x++) {
            for (int y = 0; y < column + 1; y++) {
                if (matrix[x][y] != 0 && y == column) {
                    return swapLines(matrix, row, x);
                }
                if (matrix[x][y] != 0) {
                    break;
                }
            }
        }

        return matrix;
    }

    //return matrix with swapped rows (row1, row2)
    private long[][] swapLines(long[][] matrix, int row1, int row2) {

        long[][] result = new long[matrix.length][matrix[0].length];

        for (int x = 0; x < matrix.length; x++) {
            if (x == row1) {
                System.arraycopy(matrix[row2], 0, result[row1], 0, matrix[row2].length);
            }
            if (x == row2) {
                System.arraycopy(matrix[row1], 0, result[row2], 0, matrix[row1].length);
            }
            if (x != row1 && x != row2) {
                System.arraycopy(matrix[x], 0, result[x], 0, matrix[x].length);
            }

        }
        return result;
    }

    //return matrix without row row and column column
    private long[][] subMatrix(long[][] matrix, int row, int column) {

        long[][] result = new long[matrix.length - 1][matrix[0].length - 1];

        int rowIndex = 0;
        int colIndex = 0;

        for (int x = 0; x < matrix.length; x++) {
            colIndex = 0;
            for (int y = 0; y < matrix[0].length; y++) {
                if ((x != row) && (y != column)) {
                    result[rowIndex][colIndex] = matrix[x][y];
                }
                if (column != y) {
                    colIndex++;
                }
            }
            if (row != x) {
                rowIndex++;
            }
        }

        return result;
    }

    //return matrix in row echelon form without rows full of zeroes
    private long[][] reduceLinearlyDependentRows(long[][] matrix) {

        int rank = rank(matrix);
        long[][] result = new long[rank][matrix[0].length];
        long[][] gaussMatrix = gauss(matrix);

        for (int x = 0; x < rank; x++) {
            System.arraycopy(gaussMatrix[x], 0, result[x], 0, matrix[0].length);
        }

        return result;
    }

    //return matrix without rows in Set rowsToDelete
    private long[][] deleteRows(long[][] matrix, HashSet rowsToDelete) {
        long[][] result = new long[matrix.length - rowsToDelete.size()][matrix[0].length];

        int position = 0;
        for (int x = matrix.length - 1; x >= 0; x--) {
            if (rowsToDelete.contains(x)) {
                rowsToDelete.remove(x);
            } else {
                System.arraycopy(matrix[x], 0, result[ result.length - position - 1], 0, matrix[0].length);
                position++;
            }
        }

        return result;
    }

    public static void printMatrix(long[][] matrix) {

        for (int x = 0; x < matrix.length; x++) {
            System.out.print("[ ");
            for (int y = 0; y < matrix[x].length - 1; y++) {
                System.out.print(matrix[x][y] + ", ");
            }
            System.out.println(matrix[x][matrix[x].length - 1] + " ]");
        }
    }
}

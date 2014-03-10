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
    public Matrix add(Matrix matrix1, Matrix matrix2) {
        isValid(matrix1, matrix2);

        Matrix result = new Matrix(matrix1.getRows(), matrix1.getColumns());
        for (int row = 0; row < matrix1.getRows(); row++) {

            for (int col = 0; col < matrix1.getColumns(); col++) {
                result.setElement(row, col,
                        galoisField.add(matrix1.getElement(row, col), matrix2.getElement(row, col)));
            }
        }

        return result;
    }

    @Override
    public Matrix subtract(Matrix matrix1, Matrix matrix2) {
        isValid(matrix1, matrix2);
        return add(matrix1, matrix2);
    }

    @Override
    public Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(matrix1.getRows(), matrix2.getColumns());

        if (matrix1.getRows() == 0 || matrix2.getRows() == 0) {
            throw new IllegalArgumentException("Matrix argument is empty, "
                    + "operation cannot be performed.");
        }

        if (matrix1.getColumns() == 0 || matrix2.getColumns() == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row, "
                    + "operation cannot be performed.");
        }

        if (matrix1.getColumns() != matrix2.getRows()) {
            throw new IllegalArgumentException("Argument matrices cannot be multiplied, "
                    + "their dimensions are wrong.");
        }


        for (int x = 0; x < matrix1.getRows(); x++) {
            for (int y = 0; y < matrix2.getColumns(); y++) {
                long value = 0;
                for (int z = 0; z < matrix1.getColumns(); z++) {
                    value = galoisField.add(value,
                            galoisField.multiply(matrix1.getElement(x, z), matrix2.getElement(z, y)));
                }
                result.setElement(x, y, value);
            }
        }

        return result;
    }

    @Override
    public Matrix multiply(Matrix matrix, long scalarValue) {
        isValid(matrix);

        Matrix result = new Matrix(matrix.getRows(), matrix.getColumns());
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getColumns(); col++) {
                result.setElement(row, col, galoisField.multiply(matrix.getElement(row, col), scalarValue));
            }
        }

        return result;
    }

    @Override
    public Matrix multiply(Matrix matrix, Vector vector) {
        Matrix matrixVector = new Matrix(vector.getSize(), 1);

        for (int x = 0; x < vector.getSize(); x++) {
            matrixVector.setElement(x, 0, vector.getElement(x));
        }
        return multiply(matrix, matrixVector);
    }

    @Override
    public Matrix multiply(Vector vector, Matrix matrix) {
        Matrix matrixVector = new Matrix(1, vector.getSize());

        for (int x = 0; x < vector.getSize(); x++) {
            matrixVector.setElement(0, x, vector.getElement(x));
        }
        return multiply(matrixVector, matrix);
    }

    @Override
    public Matrix inverse(Matrix matrix) {

        isValid(matrix);

        if (matrix.getRows() != matrix.getColumns()) {
            throw new IllegalArgumentException("Cannot compute inverse of nonsquare matrix.");
        }

        if (determinant(matrix) == 0) {
            throw new IllegalArgumentException("Matrix is non-invertible.");
        }

        //Prepare double-wide inverseMatrix, created from original matrix, and in the other half from identity matrix
        Matrix inverseMatrix = new Matrix(matrix.getRows(), matrix.getColumns() * 2);
        for (int x = 0; x < matrix.getRows(); x++) {
            for (int y = 0; y < (matrix.getColumns() * 2); y++) {
                if (y < matrix.getColumns()) {
                    inverseMatrix.setElement(x, y, matrix.getElement(x, y));
                } else {
                    if (x == y - matrix.getRows()) {
                        inverseMatrix.setElement(x, y, 1);
                    }
                }
            }
        }

        //Reduce all rows under the main diagonal, apply the changes to identity matrix 
        inverseMatrix = gauss(inverseMatrix);

        //Modified Gauss elimination, reduce all rows over the main diagonal, 
        //apply the changes to identity matrix 
        for (int diagPos = inverseMatrix.getRows() - 1; diagPos >= 0; diagPos--) {

            long value;
            for (int rowsOverDiagPos = diagPos - 1; rowsOverDiagPos >= 0; rowsOverDiagPos--) {

                try {
                    //set value, it will be used to set column at diagPos to zero
                    value = galoisField.divide(inverseMatrix.getElement(rowsOverDiagPos, diagPos), inverseMatrix.getElement(diagPos, diagPos));

                    //subtract from line, pivot will be set to zero and other values will be edited
                    for (int colsOverDiagPos = diagPos; colsOverDiagPos < inverseMatrix.getColumns(); colsOverDiagPos++) {
                        inverseMatrix.setElement(rowsOverDiagPos, colsOverDiagPos,
                                galoisField.subtract(galoisField.multiply(inverseMatrix.getElement(diagPos, colsOverDiagPos), value),
                                inverseMatrix.getElement(rowsOverDiagPos, colsOverDiagPos)));
                    }
                } catch (IllegalArgumentException ex) {
                    //division by zero, column full of zeroes, special case, check it later
                }
            }
        }

        //Prepare result, it is the second half of the inverseMatrix
        Matrix result = new Matrix(matrix.getRows(), matrix.getRows());
        for (int x = 0; x < inverseMatrix.getRows(); x++) {
            long value = inverseMatrix.getElement(x, x);
            for (int y = 0; y < inverseMatrix.getColumns(); y++) {
                //First half of the inverseMatrix is diagonal matrix, we divide it to create identity matrix 
                inverseMatrix.setElement(x, y, galoisField.divide(inverseMatrix.getElement(x, y), value));
                if (y >= matrix.getRows()) {
                    result.setElement(x, y - matrix.getRows(), inverseMatrix.getElement(x, y));
                }
            }
        }

        return result;
    }

    @Override
    public Matrix power(Matrix matrix, long exponent) {

        isValid(matrix);

        if (matrix.getRows() != matrix.getColumns()) {
            throw new IllegalArgumentException("Cannot compute power of non-square matrix!");
        }

        if (exponent <= 0) {
            throw new IllegalArgumentException("Exponent must be positive number!");
        }

        Matrix result = matrix;
        for (int x = 1; x < exponent; x++) {
            result = multiply(matrix, result);
        }

        return result;
    }

    //Laplace expansion used to compute determinant
    @Override
    public long determinant(Matrix matrix) {

        isValid(matrix);

        if (matrix.getRows() != matrix.getColumns()) {
            throw new IllegalArgumentException("Cannot compute determinant of nonsquare matrix.");
        }

        if (matrix.getRows() == 1) {
            return matrix.getElement(0, 0);
        }

        long result = 0;
        for (int x = 0; x < matrix.getRows(); x++) {
            //Recursive call is used to compute determinant with Laplace expansion along the first row
            result = galoisField.add(result, galoisField.multiply(matrix.getElement(0, x), determinant(subMatrix(matrix, 0, x))));
        }
        return result;
    }

    @Override
    public int rank(Matrix matrix) {

        isValid(matrix);

        int rank = 0;
        Matrix result = gauss(matrix);

        for (int x = 0; x < matrix.getRows(); x++) {
            for (int y = 0; y < matrix.getColumns(); y++) {
                if (result.getElement(x, y) != 0) {
                    rank++;
                    break;
                }
            }
        }
        return rank;
    }

    //return input matrix in row echelon form
    @Override
    public Matrix gauss(Matrix matrix) {

        isValid(matrix);

        //prepare result
        Matrix result = new Matrix(matrix);

        for (int diagPos = 1; diagPos < Math.min(matrix.getRows(), matrix.getColumns()) + 1; diagPos++) {

            long value;

            //find column with pivot, swap lines if necessary
            int colPos = diagPos - 1;
            while (colPos < matrix.getColumns() && result.getElement(diagPos - 1, colPos) == 0) {
                result = findPivot(result, colPos, diagPos - 1);
                if (result.getElement(diagPos - 1, colPos) == 0) {
                    colPos++;
                }
            }
            if (colPos == matrix.getColumns()) {
                colPos = diagPos - 1;
            }

            for (int rowsUnderDiagPos = diagPos; rowsUnderDiagPos < matrix.getRows(); rowsUnderDiagPos++) {

                try {
                    //set value, it will be used to set column at diagPos to zero
                    value = galoisField.divide(result.getElement(rowsUnderDiagPos, colPos), result.getElement(diagPos - 1, colPos));

                    //subtract from line, pivot will be set to zero and other values will be edited
                    for (int colsUnderDiagPos = diagPos - 1; colsUnderDiagPos < matrix.getColumns(); colsUnderDiagPos++) {
                        result.setElement(rowsUnderDiagPos, colsUnderDiagPos,
                                galoisField.subtract(galoisField.multiply(result.getElement(diagPos - 1, colsUnderDiagPos), value),
                                result.getElement(rowsUnderDiagPos, colsUnderDiagPos)));
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
    public Vector solveLinearEquationsSystem(Matrix equationMatrix, Vector results) {

        isValid(equationMatrix);

        if (equationMatrix.getRows() != equationMatrix.getColumns()) {
            throw new IllegalArgumentException("Cannot solve linear equations system for nonsquare matrix.");
        }

        if (equationMatrix.getRows() != results.getSize()) {
            throw new IllegalArgumentException("Cannot solve linear equations system: dimension mismatch.");
        }

        if (rank(equationMatrix) != equationMatrix.getRows()) {
            throw new IllegalArgumentException("Cannot solve linear equations system: linearly dependent rows.");
        }


        //prepare equation matrix
        Matrix eqMat = new Matrix(equationMatrix.getRows(), equationMatrix.getColumns() + 1);
        for (int x = 0; x < equationMatrix.getRows(); x++) {
            for (int y = 0; y < equationMatrix.getColumns(); y++) {
                eqMat.setElement(x, y, equationMatrix.getElement(x, y));
            }
            eqMat.setElement(x, equationMatrix.getColumns(), results.getElement(x));
        }

        eqMat = gauss(eqMat);

        //set result vector from values prepared in eqMat
        Vector result = new Vector(equationMatrix.getColumns());
        for (int x = eqMat.getColumns() - 2; x >= 0; x--) {
            for (int y = eqMat.getColumns() - 2; y >= x; y--) {
                if (y == x) {
                    result.setElement(x, galoisField.divide(eqMat.getElement(x, eqMat.getColumns() - 1),
                            eqMat.getElement(x, x)));
                } else {
                    eqMat.setElement(x, eqMat.getColumns() - 1,
                            galoisField.subtract(eqMat.getElement(x, eqMat.getColumns() - 1),
                            galoisField.multiply(eqMat.getElement(x, y), result.getElement(y))));
                }
            }
        }

        return result;
    }

    //image of matrix
    @Override
    public Matrix image(Matrix matrix) {
        isValid(matrix);
        return reduceLinearlyDependentRows(matrix);
    }

    //Kernel(Matrix) * (Matrix) = zeroMatrix
    @Override
    public Matrix kernel(Matrix matrix) {
        isValid(matrix);

        Matrix kernelMatrix = new Matrix(matrix);
        kernelMatrix.transpose();
        kernelMatrix = gauss(kernelMatrix);

        //prepare result matrix
        Matrix result = new Matrix(matrix.getRows() - matrix.getColumns(), matrix.getRows());
        for (int x = 0; x < result.getRows(); x++) {
            result.setElement(x, result.getColumns() - x - 1, 1);
        }

        HashSet rowsToDelete = new HashSet();

        int index = result.getRows();
        int position = 0;
        for (int counter = result.getRows() - 1; counter >= 0; counter--) {

            index--;
            position++;

            //set kernel vector for every row
            for (int row = kernelMatrix.getRows() - 1; row >= 0; row--) {

                long value = 0;
                //loop used to find a good value in current row
                for (int col = row + 1; col <= kernelMatrix.getColumns() - 1; col++) {
                    value = galoisField.add(value, galoisField.multiply(kernelMatrix.getElement(row, col),
                            result.getElement(index, col)));
                }

                if (row == kernelMatrix.getRows() - 1) {
                    long numerator = kernelMatrix.getElement(row, (kernelMatrix.getRows() - 1) + position);
                    long denumerator = kernelMatrix.getElement(row, row);
                    if (denumerator != 0) {
                        result.setElement(index, row, galoisField.divide(numerator, denumerator));
                    } else {
                        if (numerator == 0) {
                            result.setElement(index, row, 0);
                        } else {
                            rowsToDelete.add(counter);
                        }
                    }

                } else {
                    if (kernelMatrix.getElement(row, row) != 0) {
                        result.setElement(index, row, galoisField.divide(value, kernelMatrix.getElement(row, row)));
                    } else {
                        if (value == 0) {
                            result.setElement(index, row, 0);
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

    //check, if matrices have the same size and no zero rows or columns size
    private void isValid(Matrix matrix1, Matrix matrix2) {

        if (matrix1.getRows() == 0 || matrix2.getRows() == 0) {
            throw new IllegalArgumentException("Matrix argument is empty, "
                    + "operation cannot be performed.");
        }

        if (matrix1.getColumns() == 0 || matrix2.getColumns() == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row, "
                    + "operation cannot be performed.");
        }

        if ((matrix1.getColumns() != matrix2.getColumns()) || (matrix1.getRows() != matrix2.getRows())) {
            throw new IllegalArgumentException("Argument matrices have different dimensions, "
                    + "operation cannot be performed.");
        }
    }

    //check, if matrix has no zero rows or columns size
    private void isValid(Matrix matrix) {

        if (matrix.getRows() == 0) {
            throw new IllegalArgumentException("Matrix argument is empty, "
                    + "operation cannot be performed.");
        }

        if (matrix.getColumns() == 0) {
            throw new IllegalArgumentException("Argument matrix has empty row, "
                    + "operation cannot be performed.");
        }

    }

    //change line row with line with pivot at position column
    private Matrix findPivot(Matrix matrix, int column, int row) {

        for (int x = row; x < matrix.getRows(); x++) {
            for (int y = 0; y < column + 1; y++) {
                if (matrix.getElement(x, y) != 0 && y == column) {
                    return swapLines(matrix, row, x);
                }
                if (matrix.getElement(x, y) != 0) {
                    break;
                }
            }
        }
        return matrix;
    }

    //return matrix with swapped rows (row1, row2)
    private Matrix swapLines(Matrix matrix, int row1, int row2) {

        Matrix result = new Matrix(matrix.getRows(), matrix.getColumns());

        for (int x = 0; x < matrix.getRows(); x++) {
            if (x == row1) {
                for (int y = 0; y < matrix.getColumns(); y++) {
                    result.setElement(row1, y, matrix.getElement(row2, y));
                }
            }
            if (x == row2) {
                for (int y = 0; y < matrix.getColumns(); y++) {
                    result.setElement(row2, y, matrix.getElement(row1, y));
                }
            }
            if (x != row1 && x != row2) {
                for (int y = 0; y < matrix.getColumns(); y++) {
                    result.setElement(x, y, matrix.getElement(x, y));
                }
            }

        }
        return result;
    }

    //return matrix without row row and column column
    private Matrix subMatrix(Matrix matrix, int row, int column) {

        Matrix result = new Matrix(matrix.getRows() - 1, matrix.getColumns() - 1);

        int rowIndex = 0;
        int colIndex = 0;

        for (int x = 0; x < matrix.getRows(); x++) {
            colIndex = 0;
            for (int y = 0; y < matrix.getColumns(); y++) {
                if ((x != row) && (y != column)) {
                    result.setElement(rowIndex, colIndex, matrix.getElement(x, y));
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
    private Matrix reduceLinearlyDependentRows(Matrix matrix) {

        int rank = rank(matrix);
        Matrix result = new Matrix(rank, matrix.getColumns());
        Matrix gaussMatrix = gauss(matrix);

        for (int x = 0; x < rank; x++) {
            for (int y = 0; y < matrix.getColumns(); y++) {
                result.setElement(x, y, gaussMatrix.getElement(x, y));
            }
        }
        return result;
    }

    //return matrix without rows in Set rowsToDelete
    private Matrix deleteRows(Matrix matrix, HashSet rowsToDelete) {
        Matrix result = new Matrix(matrix.getRows() - rowsToDelete.size(), matrix.getColumns());

        int position = 0;
        for (int x = matrix.getRows() - 1; x >= 0; x--) {
            if (rowsToDelete.contains(x)) {
                rowsToDelete.remove(x);
            } else {
                for (int y = 0; y < matrix.getColumns(); y++) {
                    result.setElement(result.getRows() - position - 1, y, matrix.getElement(x, y));
                }
                position++;
            }
        }
        return result;
    }
}

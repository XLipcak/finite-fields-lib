package muni.fi.gf2n.interfaces;

import muni.fi.gf2n.classes.Matrix;
import muni.fi.gf2n.classes.Vector;

/**
 * Interface GaloisFieldMatrixArithmetic describes basic operations for
 * computation with matrices with elements in Galois Fields.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldMatrixArithmetic {

    /**
     * This method implements addition of two Matrices with elements in Galois
     * Field. Matrices are taken as parameters and new matrix is returned.
     *
     * @param matrix1 augend matrix
     * @param matrix2 addend matrix
     * @return sum of matrix1 and matrix2
     */
    Matrix add(Matrix matrix1, Matrix matrix2);

    /**
     * This method implements subtraction of two Matrices with elements in
     * Galois Field. Matrices are taken as parameters and new matrix is
     * returned.
     *
     * @param matrix1 minuend matrix
     * @param matrix2 subtrahend matrix
     * @return difference of matrix1 and matrix2
     */
    Matrix subtract(Matrix matrix1, Matrix matrix2);

    /**
     * This method implements multiplication of two Matrices with elements in
     * Galois Field. Matrices are taken as parameters and new matrix is
     * returned.
     *
     * @param matrix1 multiplicand matrix1, format (x rows, y columns)
     * @param matrix2 multiplier matrix2, format (y rows, z columns)
     * @return product of matrix1 and matrix2
     */
    Matrix multiply(Matrix matrix1, Matrix matrix2);

    /**
     * This method implements multiplication of Matrix with elements in Galois
     * Field by scalar value. Matrix and scalar are taken as parameters and new
     * matrix is returned.
     *
     * @param matrix matrix to be multiplied by scalar value
     * @param scalarValue multiplier scalar
     * @return multiplication of matrix by scalarValue
     */
    Matrix multiply(Matrix matrix, long scalarValue);

    /**
     * This method implements multiplication of Matrix with elements in Galois
     * Field by Vector. Matrix and vector are taken as parameters and new matrix
     * is returned.
     *
     * @param matrix matrix to be multiplied by vector
     * @param vector multiplier vector
     * @return multiplication of matrix by vector (result matrix in format n
     * rows, 1 column)
     */
    Matrix multiply(Matrix matrix, Vector vector);

    /**
     * This method implements multiplication of Vector with elements in Galois
     * Field by Matrix. Matrix and vector are taken as parameters and new matrix
     * is returned.
     *
     * @param vector multiplicand vector
     * @param matrix multiplier matrix
     * @return multiplication of vector by matrix (result matrix in format 1
     * row, n columns)
     */
    Matrix multiply(Vector vector, Matrix matrix);

    /**
     * This method returns inverse matrix for matrix taken as parameter. Inverse
     * matrix can be computed only for square-matrices.
     *
     * @param matrix matrix to invert
     * @return inverse matrix
     */
    Matrix inverse(Matrix matrix);

    /**
     * Compute power of matrix powered to exponent.
     *
     * @param matrix matrix to exponentiate
     * @param exponent exponent of power function
     * @return power of matrix
     */
    Matrix power(Matrix matrix, long exponent);

    /**
     * Compute determinant of matrix taken as parameter.
     *
     * @param matrix input matrix (must be square)
     * @return determinant of matrix
     */
    long determinant(Matrix matrix);

    /**
     * Compute rank(number of linearly independent rows) of matrix taken as
     * parameter.
     *
     * @param matrix input matrix
     * @return rank of matrix
     */
    int rank(Matrix matrix);

    /**
     * Gaussian elimination (also known as row reduction) is performed. Matrix
     * taken as parameter in row echelon form is returned.
     *
     * @param matrix input matrix
     * @return input matrix in row echelon form
     */
    Matrix gauss(Matrix matrix);

    /**
     * Solves linear equations system for square matrix. Vector x is returned.
     * x*equationMatrix = results. Rows of equationMatrix must be linearly
     * independent, equationMatrix has to be in form N x N, and results vector
     * must have N elements.
     *
     * @param equationMatrix left side of equations system
     * @param results right side of equations system
     * @return result vector, solved linear equations system
     */
    Vector solveLinearEquationsSystem(Matrix equationMatrix, Vector results);

    /**
     * The rows of result matrix are computed as basis of inputMatrix's row
     * space. Result matrix in row echelon form is returned.
     *
     * @param matrix input matrix
     * @return image of input matrix
     */
    Matrix image(Matrix matrix);

    /**
     * Computes the kernel of input matrix. ResultMatrix * inputMatrix =
     * zeroMatrix.
     *
     * @param matrix input matrix
     * @return kernel of input matrix
     */
    Matrix kernel(Matrix matrix);
}

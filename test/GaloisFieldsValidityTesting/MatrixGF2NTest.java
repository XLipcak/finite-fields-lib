package GaloisFieldsValidityTesting;

import java.util.Arrays;
import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.Matrix;
import muni.fi.gf2n.classes.MatrixGF2N;
import muni.fi.gf2n.classes.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class MatrixGF2NTest {

    private GF2N gf;
    private Random rn;

    public MatrixGF2NTest() {
    }

    @Before
    public void setUp() {
        rn = new Random();
        gf = new GF2N(4194307l);
    }

    @Test
    public void testAdd() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        int rowsCount = rn.nextInt(255) + 1;
        int colsCount = rn.nextInt(255) + 1;
        Matrix matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
        Matrix matrix2 = new Matrix(rowsCount, colsCount);

        assertEquals("Addidion of two identical matrices must be zero matrix.",
                matrix2, mat.add(matrix1, matrix1));
        assertEquals("Matrix1 + zeroMatrix must be Matrix1.",
                matrix1, mat.add(matrix1, matrix2));
        assertEquals("zeroMatrix + zeroMatrix must be zeroMatrix.",
                matrix2, mat.add(matrix2, matrix2));

        try {
            mat.add(matrix1, new Matrix(rowsCount, colsCount + 1));
            fail("Addition of matrices with different dimensions should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            mat.add(matrix1, new Matrix(rowsCount + 1, colsCount));
            fail("Addition of matrices with different dimensions should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testSubtract() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        int rowsCount = rn.nextInt(255) + 1;
        int colsCount = rn.nextInt(255) + 1;
        Matrix matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
        Matrix matrix2 = new Matrix(rowsCount, colsCount);

        assertEquals("Subtraction of two identical matrices must be zero matrix.",
                matrix2, mat.add(matrix1, matrix1));
        assertEquals("Matrix1 - zeroMatrix must be Matrix1.",
                matrix1, mat.add(matrix1, matrix2));
        assertEquals("zeroMatrix - zeroMatrix must be zeroMatrix.",
                matrix2, mat.add(matrix2, matrix2));

        try {
            mat.add(matrix1, new Matrix(rowsCount, colsCount + 1));
            fail("Subtraction of matrices with different dimensions should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            mat.add(matrix1, new Matrix(rowsCount + 1, colsCount));
            fail("Subtraction of matrices with different dimensions should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testMultiply() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int x = 0; x < 32; x++) {

            int size = rn.nextInt(32) + 1;

            Matrix matrix1 = new Matrix(size, size, gf.getFieldSize());
            Matrix identityMatrix = generateIdentityMatrix(size);

            assertEquals("Matrix after being multiplied by identity matrix cannot be changed.",
                    matrix1, mat.multiply(matrix1, identityMatrix));
            assertEquals("Matrix after being multiplied by identity matrix cannot be changed.",
                    matrix1, mat.multiply(identityMatrix, matrix1));

        }

        int rowsCount = rn.nextInt(255) + 1;
        int colsCount = rn.nextInt(255) + 1;

        try {
            mat.multiply(new Matrix(rowsCount, colsCount), new Matrix(colsCount, rowsCount + 1));
            //OK
        } catch (IllegalArgumentException ex) {
            fail("IllegalArgumentException was thrown when two matrices of good dimensions were multiplied.");
        }

        try {
            mat.multiply(new Matrix(rowsCount, colsCount), new Matrix(colsCount + 1, rowsCount));
            fail("IllegalArgumentException should be thrown when multiplying two matrices of wrong dimensions.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            mat.multiply(new Matrix(rowsCount, colsCount + 1), new Matrix(colsCount, rowsCount));
            fail("IllegalArgumentException should be thrown when multiplying two matrices of wrong dimensions.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //TODO test other multiplication overloaded methods

    }

    @Test
    public void testInverseMultiply() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int x = 0; x < 32; x++) {

            int size = rn.nextInt(9) + 1;

            Matrix matrix1 = new Matrix(size, size, gf.getFieldSize());
            Matrix identityMatrix = generateIdentityMatrix(size);
            try {
                Matrix inverseMatrix = mat.inverse(matrix1);

                assertEquals("Matrix after being multiplied by inverse matrix must be identity matrix.",
                        identityMatrix, mat.multiply(matrix1, inverseMatrix));
                assertEquals("Matrix after being multiplied by inverse matrix must be identity matrix.",
                        identityMatrix, mat.multiply(inverseMatrix, matrix1));
            } catch (IllegalArgumentException ex) {
                //Matrix is non-invertible, this may happen sometimes
            }

        }

    }

    @Test
    public void testDeterminant() {
        //not very clever testing

        MatrixGF2N mat = new MatrixGF2N(gf);

        int size = rn.nextInt(9) + 1;
        Matrix matrix1 = new Matrix(size, size + 1, gf.getFieldSize());
        Matrix identityMatrix = generateIdentityMatrix(size);

        try {
            mat.determinant(matrix1);
            fail("Computing determinant of nonsquare matrix should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        assertEquals("Determinant of identity matrix must be equal to 1.", 1, mat.determinant(identityMatrix));

        matrix1 = new Matrix(size + 1, size + 1);
        long value = rn.nextInt(4194303);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                matrix1.setElement(x, y, value);
            }
        }

        assertEquals("Determinant of matrix consisting of one value:" + value + " must be equal to 0.",
                0, mat.determinant(matrix1));

    }

    @Test
    public void testPower() {

        MatrixGF2N mat = new MatrixGF2N(gf);

        int size = rn.nextInt(9) + 1;
        Matrix matrix1 = new Matrix(size, size + 1, gf.getFieldSize());
        Matrix identityMatrix = generateIdentityMatrix(size);

        try {
            mat.power(matrix1, rn.nextInt(255) + 1);
            fail("Computing power of nonsquare matrix should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            mat.power(matrix1, -1);
            fail("Computing power with negative exponent should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        for (int x = 1; x < 32; x++) {
            assertEquals("IdentityMatrix powered to anything muse be IdentityMatrix.",
                    identityMatrix, mat.power(identityMatrix, x));
        }
    }

    @Test
    public void testRank() {

        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 10; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rowsCount + rn.nextInt(128) + 1;
            int value = (rn.nextInt(4194303) + (rowsCount + colsCount)) % 4194303;

            Matrix matrix1 = new Matrix(rowsCount, colsCount);
            for (int x = 0; x < rowsCount; x++) {
                for (int y = 0; y < colsCount; y++) {
                    matrix1.setElement(x, y, value + x + y);
                }
            }
            assertEquals("Rank of this matrix should be equal to number of its rows.",
                    matrix1.getRows(), mat.rank(matrix1));
        }

        for (int test = 0; test < 10; test++) {
            int colsCount = rn.nextInt(128) + 1;
            int rowsCount = colsCount + rn.nextInt(128) + 1;
            int value = (rn.nextInt(4194303) + (rowsCount + colsCount)) % 4194303;

            Matrix matrix1 = new Matrix(rowsCount, colsCount);
            for (int x = 0; x < rowsCount; x++) {
                for (int y = 0; y < colsCount; y++) {
                    matrix1.setElement(x, y, value + x + y);
                }
            }
            assertEquals("Rank of this matrix should be equal to number of its columns.",
                    matrix1.getColumns(), mat.rank(matrix1));
        }


    }

    @Test
    public void testGauss() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 32; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            Matrix matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
            assertTrue("Matrix should be in row echelon form after Gauss elimination.",
                    isInRowEchelonForm(mat.gauss(matrix1)));
        }


        mat = new MatrixGF2N(3);
        for (int test = 0; test < 32; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            Matrix matrix1 = new Matrix(rowsCount, colsCount, 1);

            assertTrue("Matrix should be in row echelon form after Gauss elimination.",
                    isInRowEchelonForm(mat.gauss(matrix1)));
        }
    }

    @Test
    public void testSolveLinearEquationsSystem() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 16; test++) {

            int size = rn.nextInt(128) + 1;
            Matrix matrix1 = new Matrix(size, size, gf.getFieldSize());
            Vector resultVect = new Vector(size, gf.getFieldSize());

            try {
                Matrix resultMat = mat.multiply(matrix1, mat.solveLinearEquationsSystem(matrix1, resultVect));
                for (int y = 0; y < resultMat.getColumns(); y++) {
                    assertEquals("Linear equations system solved wrong.",
                            resultVect.getElement(y), resultMat.getElement(0, y));
                }
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().equals("Cannot solve linear equations system: linearly dependent rows.")) {
                    //this may sometimes happen
                } else {
                    fail(ex.toString());
                }
            }
        }

        mat = new MatrixGF2N(3);
        for (int test = 0; test < 64; test++) {

            int size = rn.nextInt(128) + 1;
            Matrix matrix1 = new Matrix(size, size, 1);
            Vector resultVect = new Vector(size, 1);

            try {
                Matrix resultMat = mat.multiply(matrix1, mat.solveLinearEquationsSystem(matrix1, resultVect));
                for (int y = 0; y < resultMat.getColumns(); y++) {
                    assertEquals("Linear equations system solved wrong.",
                            resultVect.getElement(y), resultMat.getElement(0, y));
                }
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().equals("Cannot solve linear equations system: linearly dependent rows.")) {
                    //this may sometimes happen
                } else {
                    fail(ex.toString());
                }
            }
        }
    }

    @Test
    public void testImage() {
        //not very clever testing

        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 16; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            Matrix matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
            assertEquals("Number of image rows must be equal to rank of a matrix.", mat.rank(matrix1),
                    mat.image(matrix1).getRows());
        }
    }

    @Test
    public void testKernel() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 32; test++) {
            int colsCount = rn.nextInt(64) + 1;
            int rowsCount = rn.nextInt(64) + 1 + colsCount;
            Matrix matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());

            Matrix resultMat = mat.kernel(matrix1);

            try {
                resultMat = mat.multiply(resultMat, matrix1);
                if (!isZeroMatrix(resultMat)) {
                    fail("Kernel of Matrix multiplied by Matrix must be zero matrix.");
                }
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().contains("Matrix argument is empty")) {
                    //OK, kernel cannot be computed for some matrices
                    //Exception is thrown when multiplying empty kernel matrix
                } else {
                    throw new IllegalArgumentException(ex.getMessage());
                }
            }
        }

        //Tests in binary GF
        mat = new MatrixGF2N(3);
        for (int test = 0; test < 32; test++) {
            int colsCount = rn.nextInt(64) + 1;
            int rowsCount = rn.nextInt(64) + 1 + colsCount;
            Matrix matrix1 = new Matrix(rowsCount, colsCount, 1);

            Matrix resultMat = mat.kernel(matrix1);

            try {
                resultMat = mat.multiply(resultMat, matrix1);
                if (!isZeroMatrix(resultMat)) {
                    fail("Kernel of Matrix multiplied by Matrix must be zero matrix.");
                }
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().contains("Matrix argument is empty")) {
                    //OK, kernel cannot be computed for some matrices
                    //Exception is thrown when multiplying empty kernel matrix
                } else {
                    throw new IllegalArgumentException(ex.getMessage());
                }
            }
        }
    }

    //equals test for matrices, may be changed in the future for isZeroMatrix method in Matrix separate class
    private boolean isZeroMatrix(Matrix matrix) {

        for (int x = 0; x < matrix.getRows(); x++) {
            for (int y = 0; y < matrix.getColumns(); y++) {
                if (matrix.getElement(x, y) != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private Matrix generateIdentityMatrix(int size) {

        Matrix matrix = new Matrix(size, size);

        for (int x = 0; x < size; x++) {
            matrix.setElement(x, x, 1);
        }

        return matrix;
    }

    private boolean isInRowEchelonForm(Matrix matrix) {

        boolean tempCtrl = false;
        for (int x = 0; x < matrix.getRows(); x++) {
            int pivot = findPivotInRow(matrix, x);
            if (pivot == -1) {
                tempCtrl = true;
            }

            if (tempCtrl && pivot != -1) {
                return false;
            }

            for (int y = x + 1; y < matrix.getRows() && pivot != -1; y++) {
                if (matrix.getElement(y, pivot) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //return position of pivot in row row, -1 returned for row full of zeroes
    private int findPivotInRow(Matrix matrix, int row) {

        for (int y = 0; y < matrix.getColumns(); y++) {
            if (matrix.getElement(row, y) != 0) {
                return y;
            }
        }
        return -1;
    }
}
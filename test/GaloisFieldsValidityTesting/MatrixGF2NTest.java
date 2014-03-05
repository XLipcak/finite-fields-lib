/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GaloisFieldsValidityTesting;

import java.util.Arrays;
import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.MatrixGF2N;
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
 * Class MatrixGF2N Testing
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
        long[][] matrix1 = new long[rowsCount][colsCount];
        long[][] matrix2 = new long[rowsCount][colsCount];

        matrix1 = generateRandomMatrix(rowsCount, colsCount);

        if (!isEqual(mat.add(matrix1, matrix1), matrix2)) {
            fail("Addidion of two identical matrices must be zero matrix.");
        }

        if (!isEqual(mat.add(matrix1, matrix2), matrix1)) {
            fail("Matrix1 + zeroMatrix must be Matrix1.");
        }

        if (!isEqual(mat.add(matrix2, matrix2), matrix2)) {
            fail("zeroMatrix + zeroMatrix must be zeroMatrix.");
        }

        try {
            mat.add(matrix1, new long[rowsCount][colsCount + 1]);
            fail("Addition of matrices with different dimensions should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            mat.add(matrix1, new long[rowsCount + 1][colsCount]);
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
        long[][] matrix1 = new long[rowsCount][colsCount];
        long[][] matrix2 = new long[rowsCount][colsCount];

        matrix1 = generateRandomMatrix(rowsCount, colsCount);

        if (!isEqual(mat.subtract(matrix1, matrix1), matrix2)) {
            fail("Subtraction of two identical matrices must be zero matrix.");
        }

        if (!isEqual(mat.subtract(matrix1, matrix2), matrix1)) {
            fail("Matrix1 - zeroMatrix must be Matrix1.");
        }

        if (!isEqual(mat.subtract(matrix2, matrix2), matrix2)) {
            fail("zeroMatrix - zeroMatrix must be zeroMatrix.");
        }

        try {
            mat.subtract(matrix1, new long[rowsCount][colsCount + 1]);
            fail("Subtraction of matrices with different dimensions should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            mat.subtract(matrix1, new long[rowsCount + 1][colsCount]);
            fail("Subtraction of matrices with different dimensions should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testTranspose() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int x = 0; x < 100; x++) {

            int rowsCount = rn.nextInt(255) + 1;
            int colsCount = rn.nextInt(255) + 1;

            long[][] matrix1 = new long[rowsCount][colsCount];
            long[][] matrix2 = mat.transpose(matrix1);

            assertEquals("Transposed matrix rowCount and colCount must be swapped.",
                    matrix1.length, matrix2[0].length);
            assertEquals("Transposed matrix rowCount and colCount must be swapped.",
                    matrix1[0].length, matrix2.length);
            if (!isEqual(matrix1, mat.transpose(matrix2))) {
                fail("Matrix after double transposion cannot be changed.");
            }
        }

    }

    @Test
    public void testMultiply() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int x = 0; x < 32; x++) {

            int size = rn.nextInt(255) + 1;

            long[][] matrix1 = new long[size][size];
            long[][] identityMatrix = generateIdentityMatrix(size);

            if (!isEqual(matrix1, mat.multiply(matrix1, identityMatrix))) {
                fail("Matrix after being multiplied by identity matrix cannot be changed.");
            }
            if (!isEqual(matrix1, mat.multiply(identityMatrix, matrix1))) {
                fail("Matrix after being multiplied by identity matrix cannot be changed.");
            }
        }

        int rowsCount = rn.nextInt(255) + 1;
        int colsCount = rn.nextInt(255) + 1;

        try {
            mat.multiply(new long[rowsCount][colsCount], new long[colsCount][rowsCount + 1]);
            //OK
        } catch (IllegalArgumentException ex) {
            fail("IllegalArgumentException was thrown when two matrices of good dimensions were multiplied.");
        }

        try {
            mat.multiply(new long[rowsCount][colsCount], new long[colsCount + 1][rowsCount]);
            fail("IllegalArgumentException should be thrown when multiplying two matrices of wrong dimensions.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            mat.multiply(new long[rowsCount][colsCount + 1], new long[colsCount][rowsCount]);
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

            long[][] matrix1 = generateRandomMatrix(size, size);
            long[][] identityMatrix = generateIdentityMatrix(size);
            try {
                long[][] inverseMatrix = mat.inverse(matrix1);

                if (!isEqual(identityMatrix, mat.multiply(matrix1, inverseMatrix))) {
                    fail("Matrix after being multiplied by inverse matrix must be identity matrix.");
                }
                if (!isEqual(identityMatrix, mat.multiply(inverseMatrix, matrix1))) {
                    fail("Matrix after being multiplied by inverse matrix must be identity matrix.");
                }
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
        long[][] matrix1 = generateRandomMatrix(size, size + 1);
        long[][] identityMatrix = generateIdentityMatrix(size);

        try {
            mat.determinant(matrix1);
            fail("Computing determinant of nonsquare matrix should throw IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        assertEquals("Determinant of identity matrix must be equal to 1.", 1, mat.determinant(identityMatrix));

        matrix1 = new long[size + 1][size + 1];
        long value = rn.nextInt(4194303);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                matrix1[x][y] = value;
            }
        }

        assertEquals("Determinant of matrix consisting of one value:" + value + " must be equal to 0.",
                0, mat.determinant(matrix1));

    }

    @Test
    public void testPower() {

        MatrixGF2N mat = new MatrixGF2N(gf);

        int size = rn.nextInt(9) + 1;
        long[][] matrix1 = generateRandomMatrix(size, size + 1);
        long[][] identityMatrix = generateIdentityMatrix(size);

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
            if (!isEqual(identityMatrix, mat.power(identityMatrix, x))) {
                fail("IdentityMatrix powered to anything muse be IdentityMatrix.");
            }
        }
    }

    @Test
    public void testRank() {

        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 10; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rowsCount + rn.nextInt(128) + 1;
            int value = (rn.nextInt(4194303) + (rowsCount + colsCount)) % 4194303;

            long[][] matrix1 = new long[rowsCount][colsCount];
            for (int x = 0; x < rowsCount; x++) {
                for (int y = 0; y < colsCount; y++) {
                    matrix1[x][y] = value + x + y;
                }
            }
            assertEquals("Rank of this matrix should be equal to number of its rows.",
                    matrix1.length, mat.rank(matrix1));
        }

        for (int test = 0; test < 10; test++) {
            int colsCount = rn.nextInt(128) + 1;
            int rowsCount = colsCount + rn.nextInt(128) + 1;
            int value = (rn.nextInt(4194303) + (rowsCount + colsCount)) % 4194303;

            long[][] matrix1 = new long[rowsCount][colsCount];
            for (int x = 0; x < rowsCount; x++) {
                for (int y = 0; y < colsCount; y++) {
                    matrix1[x][y] = value + x + y;
                }
            }
            assertEquals("Rank of this matrix should be equal to number of its columns.",
                    matrix1[0].length, mat.rank(matrix1));
        }


    }

    @Test
    public void testGauss() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 32; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            long[][] matrix1 = generateRandomMatrix(rowsCount, colsCount);
            assertEquals("Matrix should be in row echelon form after Gauss elimination.",
                    true, isInRowEchelonForm(mat.gauss(matrix1)));
        }

        mat = new MatrixGF2N(3);

        for (int test = 0; test < 32; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            long[][] matrix1 = new long[rowsCount][colsCount];

            for (int x = 0; x < rowsCount; x++) {
                for (int y = 0; y < colsCount; y++) {
                    matrix1[x][y] = rn.nextInt(2);
                }
            }
            if (!isInRowEchelonForm(mat.gauss(matrix1))) {
                MatrixGF2N.printMatrix(mat.gauss(matrix1));
            }
            assertEquals("Matrix should be in row echelon form after Gauss elimination.",
                    true, isInRowEchelonForm(mat.gauss(matrix1)));
        }
    }

    @Test
    public void testSolveLinearEquationsSystem() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 16; test++) {

            int size = rn.nextInt(128) + 1;
            long[][] matrix1 = generateRandomMatrix(size, size);
            long[] resultVect = new long[size];

            for (int x = 0; x < size; x++) {
                resultVect[x] = rn.nextInt(4194303);
            }

            try {
                long[][] resultMat = mat.multiply(matrix1, mat.solveLinearEquationsSystem(matrix1, resultVect));
                for (int y = 0; y < resultMat[0].length; y++) {
                    assertEquals("Linear equations system solved wrong.", resultVect[y], resultMat[0][y]);
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
            long[][] matrix1 = new long[size][size];
            long[] resultVect = new long[size];

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    matrix1[x][y] = rn.nextInt(2);
                }
            }

            for (int x = 0; x < size; x++) {
                resultVect[x] = rn.nextInt(2);
            }

            try {
                long[][] resultMat = mat.multiply(matrix1, mat.solveLinearEquationsSystem(matrix1, resultVect));
                for (int y = 0; y < resultMat[0].length; y++) {
                    assertEquals("Linear equations system solved wrong.", resultVect[y], resultMat[0][y]);
                }
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().equals("Cannot solve linear equations system: linearly dependent rows.")) {
                    //this may happen, very often with binary finite fields
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

            long[][] matrix1 = generateRandomMatrix(rowsCount, colsCount);
            assertEquals("Number of image rows must be equal to rank of a matrix.", mat.rank(matrix1),
                    mat.image(matrix1).length);
        }
    }

    @Test
    public void testKernel() {
        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 16; test++) {
            int colsCount = rn.nextInt(64) + 1;
            int rowsCount = rn.nextInt(64) + 1 + colsCount;
            long[][] matrix1 = generateRandomMatrix(rowsCount, colsCount);

            long[][] resultMat = mat.kernel(matrix1);
            assertEquals("Number of kernel rows is wrong.", (rowsCount - colsCount),
                    resultMat.length);

            resultMat = mat.multiply(resultMat, matrix1);
            for (int x = 0; x < resultMat.length; x++) {
                for (int y = 0; y < resultMat[0].length; y++) {
                    assertEquals("Kernel of Matrix multiplied by Matrix must be zero matrix.",
                            0, resultMat[x][y]);
                }
            }
        }
    }

    //equals test for matrices, may be changed in the future for equals method in Matrix separate class
    private boolean isEqual(long[][] matrix1, long[][] matrix2) {

        if ((matrix1[0].length != matrix2[0].length) || (matrix1.length != matrix2.length)) {
            return false;
        }

        for (int x = 0; x < matrix1.length; x++) {
            for (int y = 0; y < matrix1[0].length; y++) {
                if (matrix1[x][y] != matrix2[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }

    private long[][] generateRandomMatrix(int rowsCount, int colsCount) {

        long[][] matrix = new long[rowsCount][colsCount];

        for (int x = 0; x < rowsCount; x++) {
            for (int y = 0; y < colsCount; y++) {
                matrix[x][y] = rn.nextInt(4194303);
            }
        }

        return matrix;
    }

    private long[][] generateIdentityMatrix(int size) {

        long[][] matrix = new long[size][size];

        for (int x = 0; x < size; x++) {
            matrix[x][x] = 1;
        }

        return matrix;
    }

    private boolean isInRowEchelonForm(long[][] matrix) {

        boolean tempCtrl = false;
        for (int x = 0; x < matrix.length; x++) {
            int pivot = findPivotInRow(matrix, x);
            if (pivot == -1) {
                tempCtrl = true;
            }

            if (tempCtrl && pivot != -1) {
                return false;
            }

            for (int y = x + 1; y < matrix.length && pivot != -1; y++) {
                if (matrix[y][pivot] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //return position of pivot in row row, -1 returned for row full of zeroes
    private int findPivotInRow(long[][] matrix, int row) {

        for (int y = 0; y < matrix[0].length; y++) {
            if (matrix[row][y] != 0) {
                return y;
            }
        }
        return -1;
    }
}
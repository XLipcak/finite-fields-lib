package GaloisFieldsValidityTesting;

import java.util.Arrays;
import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.Matrix;
import muni.fi.gf2n.classes.MatrixGF2N;
import muni.fi.gf2n.classes.Vector;
import muni.fi.gf2n.exceptions.DimensionMismatchException;
import muni.fi.gf2n.exceptions.MathArithmeticException;
import muni.fi.gf2n.exceptions.MathIllegalArgumentException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class MatrixGF2NTest is used to test computation with matrices with elements
 * in Galois Field. It includes testing with constants and testing with values
 * generated randomly. Results of all tests with constant values were computed
 * by NTL library.
 *
 * @author Jakub Lipcak, Masaryk University
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
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 80);
        matrix1.setElement(0, 1, 123);
        matrix1.setElement(0, 2, 138);
        matrix1.setElement(1, 0, 9);
        matrix1.setElement(1, 1, 202);
        matrix1.setElement(1, 2, 53);
        matrix1.setElement(2, 0, 75);
        matrix1.setElement(2, 1, 180);
        matrix1.setElement(2, 2, 201);

        Matrix matrix2 = new Matrix(3, 3);
        matrix2.setElement(0, 0, 191);
        matrix2.setElement(0, 1, 87);
        matrix2.setElement(0, 2, 253);
        matrix2.setElement(1, 0, 112);
        matrix2.setElement(1, 1, 144);
        matrix2.setElement(1, 2, 62);
        matrix2.setElement(2, 0, 23);
        matrix2.setElement(2, 1, 230);
        matrix2.setElement(2, 2, 202);

        Matrix resultMatrix = new Matrix(3, 3);
        resultMatrix.setElement(0, 0, 239);
        resultMatrix.setElement(0, 1, 44);
        resultMatrix.setElement(0, 2, 119);
        resultMatrix.setElement(1, 0, 121);
        resultMatrix.setElement(1, 1, 90);
        resultMatrix.setElement(1, 2, 11);
        resultMatrix.setElement(2, 0, 92);
        resultMatrix.setElement(2, 1, 82);
        resultMatrix.setElement(2, 2, 3);
        assertEquals("Addition of matrices with constant values failed.",
                resultMatrix, matGF.add(matrix1, matrix2));

        matrix1.setElement(0, 0, 171);
        matrix1.setElement(0, 1, 17);
        matrix1.setElement(0, 2, 218);
        matrix1.setElement(1, 0, 180);
        matrix1.setElement(1, 1, 201);
        matrix1.setElement(1, 2, 251);
        matrix1.setElement(2, 0, 102);
        matrix1.setElement(2, 1, 120);
        matrix1.setElement(2, 2, 43);

        matrix2.setElement(0, 0, 34);
        matrix2.setElement(0, 1, 59);
        matrix2.setElement(0, 2, 124);
        matrix2.setElement(1, 0, 252);
        matrix2.setElement(1, 1, 238);
        matrix2.setElement(1, 2, 255);
        matrix2.setElement(2, 0, 218);
        matrix2.setElement(2, 1, 72);
        matrix2.setElement(2, 2, 20);

        resultMatrix.setElement(0, 0, 137);
        resultMatrix.setElement(0, 1, 42);
        resultMatrix.setElement(0, 2, 166);
        resultMatrix.setElement(1, 0, 72);
        resultMatrix.setElement(1, 1, 39);
        resultMatrix.setElement(1, 2, 4);
        resultMatrix.setElement(2, 0, 188);
        resultMatrix.setElement(2, 1, 48);
        resultMatrix.setElement(2, 2, 63);
        assertEquals("Addition of matrices with constant values failed.",
                resultMatrix, matGF.add(matrix1, matrix2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);

        for (int x = 0; x < 100; x++) {
            int rowsCount = rn.nextInt(255) + 1;
            int colsCount = rn.nextInt(255) + 1;
            matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
            matrix2 = new Matrix(rowsCount, colsCount);

            assertEquals("Addidion of two identical matrices must be zero matrix.",
                    matrix2, matGF.add(matrix1, matrix1));
            assertEquals("Matrix1 + zeroMatrix must be Matrix1.",
                    matrix1, matGF.add(matrix1, matrix2));
            assertEquals("zeroMatrix + zeroMatrix must be zeroMatrix.",
                    matrix2, matGF.add(matrix2, matrix2));

            try {
                matGF.add(matrix1, new Matrix(rowsCount, colsCount + 1));
                fail("Addition of matrices with different dimensions should "
                        + "throw DimensionMismatchException.");
            } catch (DimensionMismatchException ex) {
                //OK
            }

            try {
                matGF.add(matrix1, new Matrix(rowsCount + 1, colsCount));
                fail("Addition of matrices with different dimensions should "
                        + "throw DimensionMismatchException.");
            } catch (DimensionMismatchException ex) {
                //OK
            }
        }
    }

    @Test
    public void testSubtract() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 80);
        matrix1.setElement(0, 1, 211);
        matrix1.setElement(0, 2, 99);
        matrix1.setElement(1, 0, 186);
        matrix1.setElement(1, 1, 63);
        matrix1.setElement(1, 2, 216);
        matrix1.setElement(2, 0, 245);
        matrix1.setElement(2, 1, 17);
        matrix1.setElement(2, 2, 77);

        Matrix matrix2 = new Matrix(3, 3);
        matrix2.setElement(0, 0, 13);
        matrix2.setElement(0, 1, 158);
        matrix2.setElement(0, 2, 214);
        matrix2.setElement(1, 0, 219);
        matrix2.setElement(1, 1, 251);
        matrix2.setElement(1, 2, 123);
        matrix2.setElement(2, 0, 209);
        matrix2.setElement(2, 1, 142);
        matrix2.setElement(2, 2, 8);

        Matrix resultMatrix = new Matrix(3, 3);
        resultMatrix.setElement(0, 0, 93);
        resultMatrix.setElement(0, 1, 77);
        resultMatrix.setElement(0, 2, 181);
        resultMatrix.setElement(1, 0, 97);
        resultMatrix.setElement(1, 1, 196);
        resultMatrix.setElement(1, 2, 163);
        resultMatrix.setElement(2, 0, 36);
        resultMatrix.setElement(2, 1, 159);
        resultMatrix.setElement(2, 2, 69);
        assertEquals("Subtraction of matrices with constant values failed.",
                resultMatrix, matGF.subtract(matrix1, matrix2));

        matrix1.setElement(0, 0, 88);
        matrix1.setElement(0, 1, 95);
        matrix1.setElement(0, 2, 196);
        matrix1.setElement(1, 0, 126);
        matrix1.setElement(1, 1, 241);
        matrix1.setElement(1, 2, 124);
        matrix1.setElement(2, 0, 255);
        matrix1.setElement(2, 1, 46);
        matrix1.setElement(2, 2, 176);

        matrix2.setElement(0, 0, 11);
        matrix2.setElement(0, 1, 75);
        matrix2.setElement(0, 2, 248);
        matrix2.setElement(1, 0, 222);
        matrix2.setElement(1, 1, 105);
        matrix2.setElement(1, 2, 104);
        matrix2.setElement(2, 0, 255);
        matrix2.setElement(2, 1, 205);
        matrix2.setElement(2, 2, 93);

        resultMatrix.setElement(0, 0, 83);
        resultMatrix.setElement(0, 1, 20);
        resultMatrix.setElement(0, 2, 60);
        resultMatrix.setElement(1, 0, 160);
        resultMatrix.setElement(1, 1, 152);
        resultMatrix.setElement(1, 2, 20);
        resultMatrix.setElement(2, 0, 0);
        resultMatrix.setElement(2, 1, 227);
        resultMatrix.setElement(2, 2, 237);
        assertEquals("Subtraction of matrices with constant values failed.",
                resultMatrix, matGF.subtract(matrix1, matrix2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);

        for (int x = 0; x < 100; x++) {

            int rowsCount = rn.nextInt(255) + 1;
            int colsCount = rn.nextInt(255) + 1;
            matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
            matrix2 = new Matrix(rowsCount, colsCount);

            assertEquals("Subtraction of two identical matrices must be zero matrix.",
                    matrix2, matGF.subtract(matrix1, matrix1));
            assertEquals("Matrix1 - zeroMatrix must be Matrix1.",
                    matrix1, matGF.subtract(matrix1, matrix2));
            assertEquals("zeroMatrix - zeroMatrix must be zeroMatrix.",
                    matrix2, matGF.subtract(matrix2, matrix2));

            try {
                matGF.subtract(matrix1, new Matrix(rowsCount, colsCount + 1));
                fail("Subtraction of matrices with different dimensions should "
                        + "throw DimensionMismatchException.");
            } catch (DimensionMismatchException ex) {
                //OK
            }

            try {
                matGF.subtract(matrix1, new Matrix(rowsCount + 1, colsCount));
                fail("Subtraction of matrices with different dimensions should "
                        + "throw DimensionMismatchException.");
            } catch (DimensionMismatchException ex) {
                //OK
            }
        }
    }

    @Test
    public void testMultiply() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 202);
        matrix1.setElement(0, 1, 3);
        matrix1.setElement(0, 2, 29);
        matrix1.setElement(1, 0, 254);
        matrix1.setElement(1, 1, 68);
        matrix1.setElement(1, 2, 149);
        matrix1.setElement(2, 0, 249);
        matrix1.setElement(2, 1, 45);
        matrix1.setElement(2, 2, 230);

        Matrix matrix2 = new Matrix(3, 3);
        matrix2.setElement(0, 0, 222);
        matrix2.setElement(0, 1, 174);
        matrix2.setElement(0, 2, 156);
        matrix2.setElement(1, 0, 95);
        matrix2.setElement(1, 1, 21);
        matrix2.setElement(1, 2, 17);
        matrix2.setElement(2, 0, 58);
        matrix2.setElement(2, 1, 56);
        matrix2.setElement(2, 2, 23);

        Matrix resultMatrix = new Matrix(3, 3);
        resultMatrix.setElement(0, 0, 17);
        resultMatrix.setElement(0, 1, 153);
        resultMatrix.setElement(0, 2, 16);
        resultMatrix.setElement(1, 0, 246);
        resultMatrix.setElement(1, 1, 157);
        resultMatrix.setElement(1, 2, 197);
        resultMatrix.setElement(2, 0, 180);
        resultMatrix.setElement(2, 1, 192);
        resultMatrix.setElement(2, 2, 172);
        assertEquals("Multiplication of matrices with constant values failed.",
                resultMatrix, matGF.multiply(matrix1, matrix2));

        matrix1.setElement(0, 0, 200);
        matrix1.setElement(0, 1, 243);
        matrix1.setElement(0, 2, 34);
        matrix1.setElement(1, 0, 35);
        matrix1.setElement(1, 1, 26);
        matrix1.setElement(1, 2, 18);
        matrix1.setElement(2, 0, 100);
        matrix1.setElement(2, 1, 143);
        matrix1.setElement(2, 2, 134);

        matrix2.setElement(0, 0, 231);
        matrix2.setElement(0, 1, 115);
        matrix2.setElement(0, 2, 103);
        matrix2.setElement(1, 0, 0);
        matrix2.setElement(1, 1, 93);
        matrix2.setElement(1, 2, 19);
        matrix2.setElement(2, 0, 246);
        matrix2.setElement(2, 1, 229);
        matrix2.setElement(2, 2, 215);

        resultMatrix.setElement(0, 0, 83);
        resultMatrix.setElement(0, 1, 81);
        resultMatrix.setElement(0, 2, 65);
        resultMatrix.setElement(1, 0, 195);
        resultMatrix.setElement(1, 1, 183);
        resultMatrix.setElement(1, 2, 2);
        resultMatrix.setElement(2, 0, 32);
        resultMatrix.setElement(2, 1, 81);
        resultMatrix.setElement(2, 2, 191);
        assertEquals("Multiplication of matrices with constant values failed.",
                resultMatrix, matGF.multiply(matrix1, matrix2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);
        for (int x = 0; x < 100; x++) {

            int size = rn.nextInt(32) + 1;

            matrix1 = new Matrix(size, size, gf.getFieldSize());
            Matrix identityMatrix = generateIdentityMatrix(size);

            assertEquals("Matrix after being multiplied by identity matrix cannot be changed.",
                    matrix1, matGF.multiply(matrix1, identityMatrix));
            assertEquals("Matrix after being multiplied by identity matrix cannot be changed.",
                    matrix1, matGF.multiply(identityMatrix, matrix1));



            int rowsCount = rn.nextInt(255) + 1;
            int colsCount = rn.nextInt(255) + 1;

            try {
                matGF.multiply(new Matrix(rowsCount, colsCount), new Matrix(colsCount, rowsCount + 1));
                //OK
            } catch (DimensionMismatchException ex) {
                fail("DimensionMismatchException was thrown when two matrices "
                        + "of good dimensions were multiplied.");
            }

            try {
                matGF.multiply(new Matrix(rowsCount, colsCount), new Matrix(colsCount + 1, rowsCount));
                fail("DimensionMismatchException should be thrown when multiplying "
                        + "two matrices of wrong dimensions.");
            } catch (DimensionMismatchException ex) {
                //OK
            }

            try {
                matGF.multiply(new Matrix(rowsCount, colsCount + 1), new Matrix(colsCount, rowsCount));
                fail("DimensionMismatchException should be thrown when multiplying "
                        + "two matrices of wrong dimensions.");
            } catch (DimensionMismatchException ex) {
                //OK
            }
        }
        //TODO test other multiplication overloaded methods

    }

    @Test
    public void testInverseMultiply() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 147);
        matrix1.setElement(0, 1, 122);
        matrix1.setElement(0, 2, 235);
        matrix1.setElement(1, 0, 191);
        matrix1.setElement(1, 1, 253);
        matrix1.setElement(1, 2, 41);
        matrix1.setElement(2, 0, 205);
        matrix1.setElement(2, 1, 64);
        matrix1.setElement(2, 2, 42);

        Matrix resultMatrix = new Matrix(3, 3);
        resultMatrix.setElement(0, 0, 169);
        resultMatrix.setElement(0, 1, 124);
        resultMatrix.setElement(0, 2, 37);
        resultMatrix.setElement(1, 0, 13);
        resultMatrix.setElement(1, 1, 227);
        resultMatrix.setElement(1, 2, 150);
        resultMatrix.setElement(2, 0, 177);
        resultMatrix.setElement(2, 1, 215);
        resultMatrix.setElement(2, 2, 77);
        assertEquals("Inverse of matrix with constant values failed.",
                resultMatrix, matGF.inverse(matrix1));

        matrix1.setElement(0, 0, 42);
        matrix1.setElement(0, 1, 207);
        matrix1.setElement(0, 2, 181);
        matrix1.setElement(1, 0, 175);
        matrix1.setElement(1, 1, 32);
        matrix1.setElement(1, 2, 130);
        matrix1.setElement(2, 0, 138);
        matrix1.setElement(2, 1, 126);
        matrix1.setElement(2, 2, 146);

        resultMatrix.setElement(0, 0, 153);
        resultMatrix.setElement(0, 1, 113);
        resultMatrix.setElement(0, 2, 31);
        resultMatrix.setElement(1, 0, 64);
        resultMatrix.setElement(1, 1, 14);
        resultMatrix.setElement(1, 2, 94);
        resultMatrix.setElement(2, 0, 87);
        resultMatrix.setElement(2, 1, 202);
        resultMatrix.setElement(2, 2, 21);
        assertEquals("Inverse of matrix with constant values failed.",
                resultMatrix, matGF.inverse(matrix1));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);
        for (int x = 0; x < 1000; x++) {

            int size = rn.nextInt(9) + 1;

            matrix1 = new Matrix(size, size, gf.getFieldSize());
            Matrix identityMatrix = generateIdentityMatrix(size);
            try {
                Matrix inverseMatrix = matGF.inverse(matrix1);

                assertEquals("Matrix after being multiplied by inverse matrix must be identity matrix.",
                        identityMatrix, matGF.multiply(matrix1, inverseMatrix));
                assertEquals("Matrix after being multiplied by inverse matrix must be identity matrix.",
                        identityMatrix, matGF.multiply(inverseMatrix, matrix1));
            } catch (MathArithmeticException ex) {
                //Matrix is non-invertible, this may happen sometimes
            }

        }

    }

    @Test
    public void testDeterminant() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 104);
        matrix1.setElement(0, 1, 240);
        matrix1.setElement(0, 2, 155);
        matrix1.setElement(1, 0, 43);
        matrix1.setElement(1, 1, 81);
        matrix1.setElement(1, 2, 193);
        matrix1.setElement(2, 0, 111);
        matrix1.setElement(2, 1, 248);
        matrix1.setElement(2, 2, 82);
        assertEquals("Determinant of matrix with constant values failed.",
                172, matGF.determinant(matrix1));

        matrix1.setElement(0, 0, 187);
        matrix1.setElement(0, 1, 235);
        matrix1.setElement(0, 2, 45);
        matrix1.setElement(1, 0, 50);
        matrix1.setElement(1, 1, 191);
        matrix1.setElement(1, 2, 123);
        matrix1.setElement(2, 0, 21);
        matrix1.setElement(2, 1, 0);
        matrix1.setElement(2, 2, 2);
        assertEquals("Determinant of matrix with constant values failed.",
                160, matGF.determinant(matrix1));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);

        for (int z = 0; z < 100; z++) {
            int size = rn.nextInt(7) + 1;
            matrix1 = new Matrix(size, size + 1, gf.getFieldSize());
            Matrix identityMatrix = generateIdentityMatrix(size);

            try {
                matGF.determinant(matrix1);
                fail("Computing determinant of nonsquare matrix should throw MathArithmeticException.");
            } catch (MathArithmeticException ex) {
                //OK
            }

            assertEquals("Determinant of identity matrix must be equal to 1.", 1, matGF.determinant(identityMatrix));

            matrix1 = new Matrix(size + 1, size + 1);
            long value = rn.nextInt(4194303);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    matrix1.setElement(x, y, value);
                }
            }

            assertEquals("Determinant of matrix consisting of one value:" + value + " must be equal to 0.",
                    0, matGF.determinant(matrix1));

            matrix1 = new Matrix(size + 1, size + 1, gf.getFieldSize());
            value = rn.nextInt(4194303);
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < size + 1; y++) {
                    matrix1.setElement(x, y, value);
                }
            }

            assertEquals("Determinant of matrix with linearly dependent rows must be equal to 0.",
                    0, matGF.determinant(matrix1));
        }
    }

    @Test
    public void testPower() {

        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 200);
        matrix1.setElement(0, 1, 127);
        matrix1.setElement(0, 2, 66);
        matrix1.setElement(1, 0, 127);
        matrix1.setElement(1, 1, 98);
        matrix1.setElement(1, 2, 201);
        matrix1.setElement(2, 0, 244);
        matrix1.setElement(2, 1, 27);
        matrix1.setElement(2, 2, 66);

        Matrix resultMatrix = new Matrix(3, 3);
        resultMatrix.setElement(0, 0, 16);
        resultMatrix.setElement(0, 1, 62);
        resultMatrix.setElement(0, 2, 148);
        resultMatrix.setElement(1, 0, 244);
        resultMatrix.setElement(1, 1, 124);
        resultMatrix.setElement(1, 2, 181);
        resultMatrix.setElement(2, 0, 131);
        resultMatrix.setElement(2, 1, 99);
        resultMatrix.setElement(2, 2, 49);
        assertEquals("Power of matrix with constant values failed.",
                resultMatrix, matGF.power(matrix1, 100));

        matrix1.setElement(0, 0, 175);
        matrix1.setElement(0, 1, 45);
        matrix1.setElement(0, 2, 52);
        matrix1.setElement(1, 0, 228);
        matrix1.setElement(1, 1, 13);
        matrix1.setElement(1, 2, 159);
        matrix1.setElement(2, 0, 169);
        matrix1.setElement(2, 1, 184);
        matrix1.setElement(2, 2, 204);

        resultMatrix.setElement(0, 0, 88);
        resultMatrix.setElement(0, 1, 206);
        resultMatrix.setElement(0, 2, 179);
        resultMatrix.setElement(1, 0, 134);
        resultMatrix.setElement(1, 1, 115);
        resultMatrix.setElement(1, 2, 193);
        resultMatrix.setElement(2, 0, 146);
        resultMatrix.setElement(2, 1, 120);
        resultMatrix.setElement(2, 2, 111);
        assertEquals("Power of matrix with constant values failed.",
                resultMatrix, matGF.power(matrix1, 100));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);

        for (int z = 0; z < 100; z++) {
            int size = rn.nextInt(9) + 1;
            matrix1 = new Matrix(size, size + 1, gf.getFieldSize());
            Matrix identityMatrix = generateIdentityMatrix(size);

            try {
                matGF.power(matrix1, rn.nextInt(255) + 1);
                fail("Computing power of nonsquare matrix should throw MathArithmeticException.");
            } catch (MathArithmeticException ex) {
                //OK
            }

            try {
                matGF.power(matrix1, -1);
                fail("Computing power with negative exponent should throw "
                        + "MathIllegalArgumentException.");
            } catch (MathIllegalArgumentException ex) {
                //OK
            }

            for (int x = 1; x < 30; x++) {
                assertEquals("IdentityMatrix powered to anything muse be IdentityMatrix.",
                        identityMatrix, matGF.power(identityMatrix, x));
            }
        }
    }

    @Test
    public void testRank() {

        MatrixGF2N mat = new MatrixGF2N(gf);

        for (int test = 0; test < 50; test++) {
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

        for (int test = 0; test < 100; test++) {
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
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 44);
        matrix1.setElement(0, 1, 37);
        matrix1.setElement(0, 2, 152);
        matrix1.setElement(1, 0, 170);
        matrix1.setElement(1, 1, 173);
        matrix1.setElement(1, 2, 154);
        matrix1.setElement(2, 0, 38);
        matrix1.setElement(2, 1, 16);
        matrix1.setElement(2, 2, 7);

        Matrix resultMatrix = new Matrix(3, 3);
        resultMatrix.setElement(0, 0, 44);
        resultMatrix.setElement(0, 1, 37);
        resultMatrix.setElement(0, 2, 152);
        resultMatrix.setElement(1, 0, 0);
        resultMatrix.setElement(1, 1, 31);
        resultMatrix.setElement(1, 2, 203);
        resultMatrix.setElement(2, 0, 0);
        resultMatrix.setElement(2, 1, 0);
        resultMatrix.setElement(2, 2, 59);
        assertEquals("Gauss elimination in matrix with constant values failed.",
                resultMatrix, matGF.gauss(matrix1));

        matrix1.setElement(0, 0, 210);
        matrix1.setElement(0, 1, 252);
        matrix1.setElement(0, 2, 141);
        matrix1.setElement(1, 0, 200);
        matrix1.setElement(1, 1, 80);
        matrix1.setElement(1, 2, 13);
        matrix1.setElement(2, 0, 159);
        matrix1.setElement(2, 1, 164);
        matrix1.setElement(2, 2, 45);

        resultMatrix.setElement(0, 0, 210);
        resultMatrix.setElement(0, 1, 252);
        resultMatrix.setElement(0, 2, 141);
        resultMatrix.setElement(1, 0, 0);
        resultMatrix.setElement(1, 1, 76);
        resultMatrix.setElement(1, 2, 167);
        resultMatrix.setElement(2, 0, 0);
        resultMatrix.setElement(2, 1, 0);
        resultMatrix.setElement(2, 2, 166);
        assertEquals("Gauss elimination in matrix with constant values failed.",
                resultMatrix, matGF.gauss(matrix1));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);
        for (int test = 0; test < 100; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
            assertTrue("Matrix should be in row echelon form after Gauss elimination.",
                    isInRowEchelonForm(matGF.gauss(matrix1)));
        }

        //tests in binary GF
        matGF = new MatrixGF2N(3);
        for (int test = 0; test < 100; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            matrix1 = new Matrix(rowsCount, colsCount, 1);

            assertTrue("Matrix should be in row echelon form after Gauss elimination.",
                    isInRowEchelonForm(matGF.gauss(matrix1)));
        }
    }

    @Test
    public void testSolveLinearEquationsSystem() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 112);
        matrix1.setElement(0, 1, 0);
        matrix1.setElement(0, 2, 137);
        matrix1.setElement(1, 0, 67);
        matrix1.setElement(1, 1, 73);
        matrix1.setElement(1, 2, 44);
        matrix1.setElement(2, 0, 201);
        matrix1.setElement(2, 1, 119);
        matrix1.setElement(2, 2, 19);

        Vector resultVect = new Vector(3);
        resultVect.setElement(0, 44);
        resultVect.setElement(1, 78);
        resultVect.setElement(2, 203);

        Vector solutionVect = new Vector(3);
        solutionVect.setElement(0, 15);
        solutionVect.setElement(1, 194);
        solutionVect.setElement(2, 232);
        assertEquals("Linear equations system solution with constant values failed.",
                solutionVect, matGF.solveLinearEquationsSystem(matrix1, resultVect));

        matrix1.setElement(0, 0, 11);
        matrix1.setElement(0, 1, 184);
        matrix1.setElement(0, 2, 50);
        matrix1.setElement(1, 0, 88);
        matrix1.setElement(1, 1, 174);
        matrix1.setElement(1, 2, 78);
        matrix1.setElement(2, 0, 223);
        matrix1.setElement(2, 1, 88);
        matrix1.setElement(2, 2, 208);

        resultVect.setElement(0, 110);
        resultVect.setElement(1, 254);
        resultVect.setElement(2, 89);


        solutionVect.setElement(0, 220);
        solutionVect.setElement(1, 62);
        solutionVect.setElement(2, 76);
        assertEquals("Linear equations system solution with constant values failed.",
                solutionVect, matGF.solveLinearEquationsSystem(matrix1, resultVect));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);
        for (int test = 0; test < 100; test++) {

            int size = rn.nextInt(128) + 1;
            matrix1 = new Matrix(size, size, gf.getFieldSize());
            resultVect = new Vector(size, gf.getFieldSize());

            try {
                Matrix resultMat = matGF.multiply(matGF.solveLinearEquationsSystem(matrix1, resultVect), matrix1);
                for (int y = 0; y < resultMat.getColumns(); y++) {
                    assertEquals("Linear equations system solved wrong.",
                            resultVect.getElement(y), resultMat.getElement(0, y));
                }
            } catch (MathArithmeticException ex) {
                //linearly dependent rows, this may sometimes happen
            }
        }

        //tests with binary values
        matGF = new MatrixGF2N(3);
        for (int test = 0; test < 100; test++) {

            int size = rn.nextInt(128) + 1;
            matrix1 = new Matrix(size, size, 1);
            resultVect = new Vector(size, 1);

            try {
                Matrix resultMat = matGF.multiply(matGF.solveLinearEquationsSystem(matrix1, resultVect), matrix1);
                for (int y = 0; y < resultMat.getColumns(); y++) {
                    assertEquals("Linear equations system solved wrong.",
                            resultVect.getElement(y), resultMat.getElement(0, y));
                }
            } catch (MathArithmeticException ex) {
                //linearly dependent rows, this may sometimes happen
            }
        }
    }

    @Test
    public void testImage() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setElement(0, 0, 133);
        matrix1.setElement(0, 1, 156);
        matrix1.setElement(0, 2, 117);
        matrix1.setElement(1, 0, 235);
        matrix1.setElement(1, 1, 242);
        matrix1.setElement(1, 2, 227);
        matrix1.setElement(2, 0, 104);
        matrix1.setElement(2, 1, 5);
        matrix1.setElement(2, 2, 178);

        Matrix resultMatrix = new Matrix(3, 3);
        resultMatrix.setElement(0, 0, 133);
        resultMatrix.setElement(0, 1, 156);
        resultMatrix.setElement(0, 2, 117);
        resultMatrix.setElement(1, 0, 0);
        resultMatrix.setElement(1, 1, 99);
        resultMatrix.setElement(1, 2, 221);
        resultMatrix.setElement(2, 0, 0);
        resultMatrix.setElement(2, 1, 0);
        resultMatrix.setElement(2, 2, 64);
        assertEquals("Image of matrix with constant values failed.",
                resultMatrix, matGF.image(matrix1));

        matrix1.setElement(0, 0, 147);
        matrix1.setElement(0, 1, 64);
        matrix1.setElement(0, 2, 58);
        matrix1.setElement(1, 0, 55);
        matrix1.setElement(1, 1, 212);
        matrix1.setElement(1, 2, 120);
        matrix1.setElement(2, 0, 27);
        matrix1.setElement(2, 1, 241);
        matrix1.setElement(2, 2, 133);

        resultMatrix.setElement(0, 0, 147);
        resultMatrix.setElement(0, 1, 64);
        resultMatrix.setElement(0, 2, 58);
        resultMatrix.setElement(1, 0, 0);
        resultMatrix.setElement(1, 1, 152);
        resultMatrix.setElement(1, 2, 232);
        resultMatrix.setElement(2, 0, 0);
        resultMatrix.setElement(2, 1, 0);
        resultMatrix.setElement(2, 2, 99);
        assertEquals("Image of matrix with constant values failed.",
                resultMatrix, matGF.image(matrix1));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);
        for (int test = 0; test < 100; test++) {
            int rowsCount = rn.nextInt(128) + 1;
            int colsCount = rn.nextInt(128) + 1;

            matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());
            assertEquals("Number of image rows must be equal to rank of a matrix.", matGF.rank(matrix1),
                    matGF.image(matrix1).getRows());
        }
    }

    @Test
    public void testKernel() {
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        MatrixGF2N matGF = new MatrixGF2N(gf);

        Matrix matrix1 = new Matrix(5, 3);
        matrix1.setElement(0, 0, 113);
        matrix1.setElement(0, 1, 39);
        matrix1.setElement(0, 2, 70);
        matrix1.setElement(1, 0, 68);
        matrix1.setElement(1, 1, 116);
        matrix1.setElement(1, 2, 213);
        matrix1.setElement(2, 0, 81);
        matrix1.setElement(2, 1, 47);
        matrix1.setElement(2, 2, 215);
        matrix1.setElement(3, 0, 173);
        matrix1.setElement(3, 1, 237);
        matrix1.setElement(3, 2, 12);
        matrix1.setElement(4, 0, 44);
        matrix1.setElement(4, 1, 0);
        matrix1.setElement(4, 2, 20);

        Matrix resultMatrix = new Matrix(2, 5);
        resultMatrix.setElement(0, 0, 173);
        resultMatrix.setElement(0, 1, 45);
        resultMatrix.setElement(0, 2, 172);
        resultMatrix.setElement(0, 3, 0);
        resultMatrix.setElement(0, 4, 1);
        resultMatrix.setElement(1, 0, 233);
        resultMatrix.setElement(1, 1, 122);
        resultMatrix.setElement(1, 2, 159);
        resultMatrix.setElement(1, 3, 1);
        resultMatrix.setElement(1, 4, 0);
        assertEquals("Kernel of matrix with constant values failed.",
                resultMatrix, matGF.kernel(matrix1));

        matrix1.setElement(0, 0, 163);
        matrix1.setElement(0, 1, 118);
        matrix1.setElement(0, 2, 129);
        matrix1.setElement(1, 0, 22);
        matrix1.setElement(1, 1, 53);
        matrix1.setElement(1, 2, 216);
        matrix1.setElement(2, 0, 223);
        matrix1.setElement(2, 1, 240);
        matrix1.setElement(2, 2, 135);
        matrix1.setElement(3, 0, 19);
        matrix1.setElement(3, 1, 97);
        matrix1.setElement(3, 2, 42);
        matrix1.setElement(4, 0, 254);
        matrix1.setElement(4, 1, 157);
        matrix1.setElement(4, 2, 81);

        resultMatrix.setElement(0, 0, 252);
        resultMatrix.setElement(0, 1, 187);
        resultMatrix.setElement(0, 2, 16);
        resultMatrix.setElement(0, 3, 0);
        resultMatrix.setElement(0, 4, 1);
        resultMatrix.setElement(1, 0, 104);
        resultMatrix.setElement(1, 1, 207);
        resultMatrix.setElement(1, 2, 123);
        resultMatrix.setElement(1, 3, 1);
        resultMatrix.setElement(1, 4, 0);
        assertEquals("Kernel of matrix with constant values failed.",
                resultMatrix, matGF.kernel(matrix1));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        matGF = new MatrixGF2N(gf);
        for (int test = 0; test < 32; test++) {
            int colsCount = rn.nextInt(64) + 1;
            int rowsCount = rn.nextInt(64) + 1 + colsCount;
            matrix1 = new Matrix(rowsCount, colsCount, gf.getFieldSize());

            Matrix resultMat = matGF.kernel(matrix1);

            try {
                resultMat = matGF.multiply(resultMat, matrix1);
                if (!isZeroMatrix(resultMat)) {
                    fail("Kernel of Matrix multiplied by Matrix must be zero matrix.");
                }
            } catch (MathIllegalArgumentException ex) {
                //OK, kernel cannot be computed for some matrices
                //Exception is thrown when multiplying empty kernel matrix
            }
        }


        //Tests in binary GF
        matGF = new MatrixGF2N(3);
        for (int test = 0; test < 32; test++) {
            int colsCount = rn.nextInt(64) + 1;
            int rowsCount = rn.nextInt(64) + 1 + colsCount;
            matrix1 = new Matrix(rowsCount, colsCount, 1);

            Matrix resultMat = matGF.kernel(matrix1);

            try {
                resultMat = matGF.multiply(resultMat, matrix1);
                if (!isZeroMatrix(resultMat)) {
                    fail("Kernel of Matrix multiplied by Matrix must be zero matrix.");
                }
            } catch (MathIllegalArgumentException ex) {
                //OK, kernel cannot be computed for some matrices
                //Exception is thrown when multiplying empty kernel matrix
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n;

import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.PolynomialGF2N;
import muni.fi.gf2n.classes.VectorGF2N;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import muni.fi.aes.AES;
import muni.fi.aes.BlockCipherMode;
import muni.fi.gf2n.classes.MatrixGF2N;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class ComputationWithFiniteFields {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        GF2N gf = new GF2N(19);//...4194307l..rnd k tomu 4194303l
        int randomMax = 16;
        Random rn = new Random();
        

        //TEST Polynomials
        int poly1Length = 4;
        int poly2Length = 7;
        PolynomialGF2N poly = new PolynomialGF2N(gf);
        long[] arrayPoly = generateRandomPoly(poly1Length, randomMax);
        long[] arrayPoly2 = generateRandomPoly(poly2Length, randomMax);

        System.out.println("Polynomials output: ");
        System.out.println("Poly1: " + Arrays.toString(arrayPoly));
        System.out.println("Poly2: " + Arrays.toString(arrayPoly2));
        System.out.println("Poly1 + Poly2: " + Arrays.toString(poly.add(arrayPoly, arrayPoly2)));
        System.out.println("Poly1 * Poly2: " + Arrays.toString(poly.multiply(arrayPoly, arrayPoly2)));
        System.out.println("Poly1 / Poly2: " + Arrays.toString(poly.divide(arrayPoly, arrayPoly2)));
        System.out.println("Poly2 / Poly1: " + Arrays.toString(poly.divide(arrayPoly2, arrayPoly)));
        System.out.println("GCD(Poly1, Poly2)" + Arrays.toString(poly.gcd(arrayPoly, arrayPoly2)));
        System.out.println("Inverse Poly1 = Poly1^{-1} % Poly2: " + Arrays.toString(poly.invert(arrayPoly, arrayPoly2)));
        System.out.println("Poly1 * Poly1" + Arrays.toString(poly.multiply(arrayPoly, arrayPoly)));
        System.out.println("GCD(Poly1 * Poly1, Poly1)"
                + Arrays.toString(poly.gcd(poly.multiply(arrayPoly, arrayPoly), arrayPoly)));
        
        System.out.println("____________________________________________");
        
        //TEST vectors
        int vec1Length = 7;
        int vec2Length = 7;
        long[] vect1 = generateRandomPoly(vec1Length, randomMax);
        long[] vect2 = generateRandomPoly(vec2Length, randomMax);
        VectorGF2N vectGF = new VectorGF2N(gf);
        
        System.out.println("Vectors output: ");
        System.out.println("Vector1: ");
        System.out.println(Arrays.toString(vect1));
        System.out.println("Vector2: ");
        System.out.println(Arrays.toString(vect2));
        System.out.println("Vector1 + Vector2");
        System.out.println(Arrays.toString(vectGF.add(vect1, vect2)));
        System.out.println("Vector1 - Vector2");
        System.out.println(Arrays.toString(vectGF.subtract(vect1, vect2)));
        long scalar = rn.nextInt(randomMax);
        System.out.println("Vector1 * " + scalar);
        System.out.println(Arrays.toString(vectGF.multiply(vect1, scalar)));
        System.out.println("Vector2 * " + scalar);
        System.out.println(Arrays.toString(vectGF.multiply(vect2, scalar)));
        System.out.println("____________________________________________");
        
        //TEST Matrices
        int mat1Rows = 7;
        int mat1Cols = 7;
        int mat2Rows = 7;
        int mat2Cols = 4;
        MatrixGF2N matGF = new MatrixGF2N(gf);
        long[][] matrix1 = generateRandomMatrix(mat1Rows, mat1Cols, randomMax);
        long[][] matrix2 = generateRandomMatrix(mat2Rows, mat2Cols, randomMax);
        
        System.out.println("Matrices output: ");
        System.out.println("Matrix1: ");
        MatrixGF2N.printMatrix(matrix1);
        System.out.println("Matrix2: ");
        MatrixGF2N.printMatrix(matrix2);
        System.out.println("Matrix1 + Matrix 1");
        MatrixGF2N.printMatrix(matGF.add(matrix1, matrix1));
        int scalarValue = rn.nextInt(randomMax);
        System.out.println("Matrix1 * " + scalarValue );
        MatrixGF2N.printMatrix(matGF.multiply(matrix1, scalarValue));
        System.out.println("Matrix1 determinant: " + matGF.determinant(matrix1));
        System.out.println("Matrix1 * Matrix2");
        MatrixGF2N.printMatrix(matGF.multiply(matrix1, matrix2));
        System.out.println("Matrix1 Gauss: ");
        MatrixGF2N.printMatrix(matGF.gauss(matrix1));
        System.out.println("Matrix2 Gauss: ");
        MatrixGF2N.printMatrix(matGF.gauss(matrix2));
        System.out.println("Matrix2 kernel: ");
        MatrixGF2N.printMatrix(matGF.kernel(matrix2));
        System.out.println("Matrix2 image: ");
        MatrixGF2N.printMatrix(matGF.image(matrix2));
        System.out.println("Matrix2 rank: " + matGF.rank(matrix2));
        System.out.println("____________________________________________");
        

    }
    
    public static long[] generateRandomPoly(int length, int randomMax) {
        long[] resultPoly = new long[length];
        Random rn = new Random();
        for (int x = 0; x < length - 1; x++) {
            resultPoly[x] = rn.nextInt(randomMax);
        }
        resultPoly[length - 1] = rn.nextInt(randomMax - 1) + 1;

        return resultPoly;
    }
    
    public static long[][] generateRandomMatrix(int rowsCount, int colsCount, int randomMax) {

        long[][] matrix = new long[rowsCount][colsCount];
        Random rn = new Random();

        for (int x = 0; x < rowsCount; x++) {
            for (int y = 0; y < colsCount; y++) {
                matrix[x][y] = rn.nextInt(randomMax);
            }
        }

        return matrix;
    }
}

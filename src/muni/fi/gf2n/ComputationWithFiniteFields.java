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

        int a = 38;
        int b = 95;
        GF2N gf = new GF2N(11l);//32771...4194307l
        PolynomialGF2N poly = new PolynomialGF2N(gf);
        VectorGF2N vect = new VectorGF2N(gf);
        Random rn = new Random();

        long[] arrayPoly = new long[a];

        long[] arrayPoly2 = new long[b];


        for (int x = 0; x < a; x++) {
            long random = rn.nextInt(8);
            arrayPoly[x] = random;
        }
        
        for (int x = 0; x < b; x++) {
            long random = rn.nextInt(8);
            arrayPoly2[x] = random;
        }

        long[] remainder = new long[Math.max(a, b)];
        long[] remainder2 = new long[Math.max(a, b)];
        
        System.out.println("Add: " + Arrays.toString(arrayPoly) + " + " + Arrays.toString(arrayPoly2) + " = " + Arrays.toString(poly.add(arrayPoly, arrayPoly2)));
  
        System.out.println("Mul: " + Arrays.toString(arrayPoly) + " * "
                + Arrays.toString(arrayPoly2) + " = " + Arrays.toString(poly.multiply(arrayPoly, arrayPoly2)));

        System.out.println("Div: " + Arrays.toString(arrayPoly) + " / " + Arrays.toString(arrayPoly2) + " = " + Arrays.toString(poly.divide(arrayPoly, arrayPoly2, remainder)));
        
        System.out.println("Remainder: " + Arrays.toString(remainder));
        
        System.out.println("Power: " + Arrays.toString(poly.power(arrayPoly2, 3)));
        
        System.out.println("Gcd: " + Arrays.toString(poly.gcd(arrayPoly, arrayPoly2)));
        System.out.println("XGcd: " + Arrays.toString(poly.xgcd(arrayPoly, arrayPoly2, remainder2, remainder)));
        System.out.println("Inverse: " + Arrays.toString(poly.divide(poly.invert(arrayPoly, arrayPoly2), poly.gcd(arrayPoly, arrayPoly2))));
        remainder = new long[Math.max(a, b)];
        System.out.println("TEST: "  + Arrays.toString(poly.divide(poly.multiply(arrayPoly, (poly.divide(poly.invert(arrayPoly, arrayPoly2), poly.gcd(arrayPoly, arrayPoly2))) ), arrayPoly2, remainder) ));
        System.out.println("TEST remainder: " + Arrays.toString(remainder));
        
        long[][] matrix1 = new long[4][4];
        long[][] matrix2 = new long[4][4];
        
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                matrix1[x][y] = rn.nextInt(8);
                matrix2[x][y] = rn.nextInt(8);
            }
        }
        
        MatrixGF2N mat = new MatrixGF2N(gf);
        for(int x = 0; x < 4; x++){
            System.out.println( Arrays.toString(matrix1[x]) + " + " + Arrays.toString(matrix2[x]) + " = " 
                    + Arrays.toString(mat.transpose(matrix1)[x]));
        }
    }
}

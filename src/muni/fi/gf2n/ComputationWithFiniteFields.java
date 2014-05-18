package muni.fi.gf2n;

import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.PolynomialGF2N;
import muni.fi.gf2n.classes.VectorGF2N;
import java.util.Random;
import muni.fi.aes.AES;
import muni.fi.gf2n.classes.Matrix;
import muni.fi.gf2n.classes.MatrixGF2N;
import muni.fi.gf2n.classes.Polynomial;
import muni.fi.gf2n.classes.Vector;

/**
 * Main class using some basic functionality of library.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class ComputationWithFiniteFields {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Random rn = new Random();

        /*
         * GF2NLIB
         */

        /*
         * Computation with elements
         */
        long irreduciblePolynomial = 1033;
        GF2N galoisField = new GF2N(irreduciblePolynomial);

        // random elements
        long element1 = rn.nextInt(1024);
        long element2 = rn.nextInt(1024);

        System.out.println("Computation with elements: ");
        System.out.println(element1 + " + " + element2 + " = "
                + galoisField.add(element1, element2));

        System.out.println(element1 + " / " + element2 + " = "
                + galoisField.divide(element1, element2));

        System.out.println(element1 + " * " + element2 + " = "
                + galoisField.multiply(element1, element2) + "\n");

        /*
         * Computation with vectors
         */
        VectorGF2N vecGf = new VectorGF2N(galoisField);

        // random vectors of size 4
        Vector vector1 = new Vector(4, galoisField.getFieldSize());
        Vector vector2 = new Vector(4, galoisField.getFieldSize());

        System.out.println("Computation with vectors: ");
        System.out.println(vector1 + " + " + vector2
                + " = " + vecGf.add(vector1, vector2));

        System.out.println(vector1 + " * " + element1
                + " = " + vecGf.multiply(vector1, element1) + "\n");


        /*
         * Computation with polynomials
         */
        PolynomialGF2N polyGf = new PolynomialGF2N(galoisField);

        // random polynomials
        Polynomial polynomial1 = new Polynomial(5, galoisField.getFieldSize());
        Polynomial polynomial2 = new Polynomial(3, galoisField.getFieldSize());

        System.out.println("Computation with polynomials: ");
        System.out.println(polynomial1 + " + " + polynomial2
                + " = " + polyGf.add(polynomial1, polynomial2));

        System.out.println("Inverse of: " + polynomial2 + " modulo " + polynomial1
                + " = " + polyGf.invert(polynomial2, polynomial1));

        System.out.println(polynomial1 + " / " + polynomial2
                + " = " + polyGf.divide(polynomial1, polynomial2) + "\n");



        /*
         * Computation with matrices
         */
        MatrixGF2N matGf = new MatrixGF2N(galoisField);

        // random matrices of size 3x3
        Matrix matrix1 = new Matrix(3, 3, galoisField.getFieldSize());
        Matrix matrix2 = new Matrix(3, 3, galoisField.getFieldSize());

        System.out.println("Computation with matrices: ");
        System.out.println(matrix1 + " + \n" + matrix2 + " = \n"
                + matGf.add(matrix1, matrix2));

        System.out.println(matrix1 + " * \n" + matrix2 + " = \n"
                + matGf.multiply(matrix1, matrix2));

        System.out.println("Inverse of Matrix: \n" + matrix1 + " = \n"
                + matGf.inverse(matrix1));



        /*
         * AES
         */
        AES aes = new AES();

        String plainText = "fffc000000000000000000000cf00012";
        String key = "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4";

        byte[] cipherText = aes.encryptECB(hexaStringToByteArray(plainText), hexaStringToByteArray(key));
        
        System.out.println("AES: ");
        System.out.println("Plain text: ");
        System.out.println(toUpperWithSpaces(plainText));
        
        System.out.println("Key: ");
        System.out.println(toUpperWithSpaces(key));
        
        System.out.println("Cipher text: ");
        System.out.println(byteArrayToHexaString(cipherText));
        
        System.out.println("Decrypted cipher text: ");
        System.out.println(byteArrayToHexaString(aes.decryptECB(cipherText, hexaStringToByteArray(key))));
    }

    private static byte[] hexaStringToByteArray(String str) {
        byte[] result = new byte[(str.length() / 2)];

        for (int x = 0; x < result.length; x++) {
            String hexa = str.substring(x * 2, (x * 2) + 2);
            result[x] = (byte) Long.parseLong(hexa, 16);
        }

        return result;
    }

    private static String byteArrayToHexaString(byte[] input) {
        String result = "";
        StringBuilder sb = new StringBuilder();
        for (byte b : input) {
            sb.append(String.format("%02X ", b));
        }

        result = sb.toString();
        return result;
    }
    
    private static String toUpperWithSpaces(String input){
        String result = "";
        
        for(int x = 0; x < (input.length()/2); x++){
            result += input.substring(x*2, (x*2) + 2).toUpperCase();
            result += " ";
        }
        
        return result;
    }
}

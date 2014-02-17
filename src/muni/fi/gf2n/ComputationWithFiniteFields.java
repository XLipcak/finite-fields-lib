/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n;

import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.PolynomialGF2N;
import muni.fi.gf2n.classes.VectorGF2N;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class ComputationWithFiniteFields {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*long lStartTime = System.currentTimeMillis();
         long lEndTime = System.currentTimeMillis();
         long difference1 = lEndTime - lStartTime;*/
        long a = 9;
        long b = 64;
        GF2N gf = new GF2N(11l);//32771...4194307l
        PolynomialGF2N poly = new PolynomialGF2N(gf);
        Random rn = new Random();

        ArrayList<Long> polynomial1 = new ArrayList<>();
        ArrayList<Long> polynomial2 = new ArrayList<>();
        ArrayList<Long> remainder = new ArrayList<>();

        ArrayList<Long> inverse = new ArrayList<>();
        ArrayList<Long> gcd = new ArrayList<>();
        ArrayList<Long> result = new ArrayList<>();

        for (int n = 0; n < 421; n++) {
            polynomial1.add((long) (rn.nextInt(8)));
        }
        for (int n = 0; n < 123; n++) {
            polynomial2.add((long) (rn.nextInt(8)));
        }


        poly.clearZeroValuesFromPolynomial(polynomial1);
        poly.clearZeroValuesFromPolynomial(polynomial2);
        System.out.println("Poly1: " + polynomial1);
        System.out.println("Poly2: " + polynomial2);
        System.out.println("Poly1 + Poly2: " + poly.add(polynomial1, polynomial2));
        System.out.println("Poly1 * Poly2: " + poly.multiply(polynomial1, polynomial2));
        System.out.println("Poly1 / Poly2: " + poly.divide(polynomial1, polynomial2, remainder));
        System.out.println("Remainder: " + remainder);
        System.out.println("Gcd: " + poly.gcd(polynomial1, polynomial2));
        System.out.println("Poly2 / Gcd1 " + poly.divide(polynomial2, poly.gcd(polynomial1, polynomial2), remainder) + " + " + remainder);


        gcd = poly.gcd(polynomial1, polynomial2);
        inverse = poly.invert(polynomial2, polynomial1);
        inverse = poly.divide(inverse, gcd);

        result = poly.divide(poly.multiply(inverse, polynomial2), polynomial1);


        System.out.println("Inverse of poly2 % poly1" + inverse);
        System.out.println("poly2*inverse / poly1: " + poly.divide(poly.multiply(inverse, polynomial2),
                polynomial1, remainder) + " + remainder: " + remainder);
        System.out.println("Result: " + result);
        System.out.println("________________________");

        VectorGF2N vect = new VectorGF2N(gf);
        ArrayList<Long> vector1 = new ArrayList<>();
        ArrayList<Long> vector2 = new ArrayList<>();

        for (int n = 0; n < 9; n++) {
            vector1.add((long) (rn.nextInt(8)));
        }
        for (int n = 0; n < 9; n++) {
            vector2.add((long) (rn.nextInt(8)));
        }
        
        System.out.println("Vector1: " + vector1);
        System.out.println("Vector2: " + vector2);
        System.out.println("Vector1 + Vector2: " + vect.add(vector1, vector2));
        System.out.println("Vector1 - Vector2: " + vect.subtract(vector1, vector2));
        System.out.println("Vector1 * 3: " + vect.multiply(vector1, 3l));
        
       
        GF2N gal = new GF2N(10197);
        System.out.println(gal.multiply(53 , 47));

    }
}

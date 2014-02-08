/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields;

import computation.with.finite.fields.library.GF2N;
import computation.with.finite.fields.library.PolynomialGF2N;
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
        GF2N gf = new GF2N(4194307l);//32771...4194307l
        PolynomialGF2N poly = new PolynomialGF2N(gf);
        Random rn = new Random();
        ArrayList<Long> polynomial1 = new ArrayList<>();
        ArrayList<Long> polynomial2 = new ArrayList<>();
        ArrayList<Long> remainder = new ArrayList<>();
        
        
        for (int n = 0; n < 26; n++) {
            polynomial1.add((long) (rn.nextInt(999999)));
        }
        for (int n = 0; n < 5; n++) {
            polynomial2.add((long) (rn.nextInt(999999)));
        }


        poly.clearZeroValuesFromPolynomial(polynomial1);
        poly.clearZeroValuesFromPolynomial(polynomial2);
        System.out.println("Poly1: " + polynomial1);
        System.out.println("Poly2: " + polynomial2);
        System.out.println("Poly1 + Poly2: " + poly.add(polynomial1, polynomial2));
        System.out.println("Poly1 * Poly2: " + poly.multiply(polynomial1, polynomial2));
        System.out.println("Poly1 / Poly2: " + poly.divide(polynomial1, polynomial2, remainder));
        System.out.println("Remainder: " + remainder);
        System.out.println("Gcd1: " + poly.gcd(polynomial1, polynomial2));
        System.out.println("Poly2 / Gcd1 " + poly.divide(polynomial2, poly.gcd(polynomial1, polynomial2), remainder) + " + " + remainder);
        System.out.println("Poly1 * Poly1: " + poly.multiply(polynomial1, polynomial1));
        System.out.println("Gcd2:     " + poly.gcd(polynomial1, poly.multiply(polynomial1, polynomial1)));
    }
}

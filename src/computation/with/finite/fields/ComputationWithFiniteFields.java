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
        GF2N gf = new GF2N(11);//32771...4194307l
        PolynomialGF2N poly = new PolynomialGF2N(gf);
        Random rn = new Random();
        
        ArrayList<Long> polynomial1 = new ArrayList<>();
        ArrayList<Long> polynomial2 = new ArrayList<>();
        ArrayList<Long> remainder = new ArrayList<>();
        
        ArrayList<Long> inverse = new ArrayList<>();
        ArrayList<Long> gcd = new ArrayList<>();
        ArrayList<Long> result = new ArrayList<>();
        
        for (int n = 0; n < 9; n++) {
            polynomial1.add((long) (rn.nextInt(8)));
        }
        for (int n = 0; n < 3; n++) {
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
        
        result = poly.divide(poly.multiply(inverse, polynomial2),  polynomial1);

        
        System.out.println("Inverse of poly2 % poly1" + inverse);
        System.out.println("poly2*inverse / poly1: " + poly.divide(poly.multiply(inverse, polynomial2), 
                polynomial1, remainder) + " + remainder: " + remainder);
        System.out.println("Result: " + result);
       // poly.xgcd(polynomial2, polynomial1, gcd, gcd);
        
    }
}

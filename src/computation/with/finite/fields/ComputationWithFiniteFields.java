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


        long a = 27;
        long b = 64;
        GF2N gf = new GF2N(115);
        PolynomialGF2N poly = new PolynomialGF2N(gf);

        Random rn = new Random();
        ArrayList<Long> polynomial1 = new ArrayList<>();
        ArrayList<Long> polynomial2 = new ArrayList<>();
        ArrayList<Long> remainder = new ArrayList<>();

        /*
         for(int n = 0; n < 6; n++){
         polynomial1.add((long)(rn.nextInt(64)));
         }
         /* for(int n = 0; n < 3; n++){
         polynomial2.add((long)(rn.nextInt(64)));
         }*/
        polynomial1.add(11L);
        polynomial1.add(29L);
        polynomial1.add(6L);
        polynomial1.add(51L);
        polynomial1.add(31L);
        polynomial2.add(9L);
        polynomial2.add(15L);
        polynomial2.add(61L);

        System.out.println(polynomial1.toString());
        System.out.println(polynomial2.toString());

        System.out.println("Multiplied: " + poly.multiply(polynomial1, polynomial2));
        System.out.println("Divided: " + poly.divideWithRemainder(polynomial1, polynomial2, remainder));
        System.out.println("Remainder: " + remainder);


    }
}

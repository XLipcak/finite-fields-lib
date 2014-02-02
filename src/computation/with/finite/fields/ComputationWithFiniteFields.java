/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields;
import computation.with.finite.fields.computation.GaloisField;

/**
 *
 * @author Jakub
 */
public class ComputationWithFiniteFields {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GaloisField gf = new GaloisField();
        long result = gf.add(2, 9);
        
        System.out.println(result);
    }
}

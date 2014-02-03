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
        //long addResult = gf.subtract(47,64);
        long result = gf.multiply(15,22);
        
      //  long inverse = gf.invert(44);
        
        
       // System.out.println(gf.getFieldSize());
        System.out.println(result);
    }
}

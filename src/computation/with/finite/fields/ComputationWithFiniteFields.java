/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields;
import computation.with.finite.fields.library.GF2N;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Jakub
 */
public class ComputationWithFiniteFields {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*long lStartTime = System.currentTimeMillis();
        long lEndTime = System.currentTimeMillis();
        long difference1 = lEndTime - lStartTime;*/
        
        long poly = 115;
        long a = 27;
        long b = 64;
        GF2N gf = new GF2N(poly);
        
        
        for(int x = 0; x < 64; x++){
            System.out.println("Inverse" + gf.invert(x) + "   Power: " + gf.power((long)x, (long)Math.pow(2, 6)-2));
        }

    }
}

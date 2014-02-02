/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.computation;
import computation.with.finite.fields.interfaces.GaloisFieldArithmetic;
/**
 *
 * @author Jakub
 */
public class GaloisField implements GaloisFieldArithmetic{

    @Override
    public long add(long element1, long element2) {
        //prvky sem musia patrit, potom...
        return element1 ^ element2;
    }

    @Override
    public long subtract(long element1, long element2) {
        return add(element1, element2);
    }

    @Override
    public long multiply(long element1, long element2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long divide(long element1, long element2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long invert(long element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long power(long element, long exponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

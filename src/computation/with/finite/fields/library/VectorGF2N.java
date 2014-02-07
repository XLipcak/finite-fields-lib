/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.library;

import computation.with.finite.fields.library.interfaces.GaloisFieldVectorArithmetic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class VectorGF2N implements GaloisFieldVectorArithmetic{

    @Override
    public ArrayList<Long> add(List<Long> vector1, List<Long> vector2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Long> subtract(List<Long> vector1, List<Long> vector2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Long> multiply(List<Long> vector, long scalarValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Long> transpose(List<Long> vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compare(List<Long> vector1, List<Long> vector2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.library.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub
 */
public interface GaloisFieldVectorArithmetic {
    
    ArrayList<Long> add(List<Long> vector1, List<Long> vector2);
    ArrayList<Long> subtract(List<Long> vector1, List<Long> vector2);
    ArrayList<Long> multiply(List<Long> vector, long scalarValue);
    ArrayList<Long> transpose(List<Long> vector);
    int compare(List<Long> vector1, List<Long> vector2);
}

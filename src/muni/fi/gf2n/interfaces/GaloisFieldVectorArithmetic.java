/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.interfaces;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldVectorArithmetic {
    
    long[] add(long[] vector1, long[] vector2);
    long[] subtract(long[] vector1, long[] vector2);
    long[] multiply(long[] vector, long scalarValue);
}

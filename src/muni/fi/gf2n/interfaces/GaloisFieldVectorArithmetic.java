/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.interfaces;

import muni.fi.gf2n.classes.Vector;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldVectorArithmetic {
    
    Vector add(Vector vector1, Vector vector2);
    Vector subtract(Vector vector1, Vector vector2);
    Vector multiply(Vector vector, long scalarValue);
}

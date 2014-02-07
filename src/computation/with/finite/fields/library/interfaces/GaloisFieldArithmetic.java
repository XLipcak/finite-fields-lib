/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.library.interfaces;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldArithmetic {
    long add(long element1, long element2);
    long subtract(long element1, long element2);
    long multiply(long element1, long element2);
    long divide(long element1, long element2);
    long invert(long element);
    long power(long element, long exponent);
    int compare(long element1, long element2);
}

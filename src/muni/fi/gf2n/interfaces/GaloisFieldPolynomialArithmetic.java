/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.interfaces;



/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldPolynomialArithmetic {
    
    long[] add(long[] polynomial1, long[] polynomial2);
    long[] subtract(long[] polynomial1, long[] polynomial2);
    long[] multiply(long[] polynomial1, long[] polynomial2);
    long[] divide(long[] polynomial1, long[] polynomial2);
    long[] divide(long[] polynomial1, long[] polynomial2, long[] remainder);
    
    long[] gcd(long[] polynomial1, long[] polynomial2);
    
    //result = a^{-1} % f
    long[] invert(long[] polynomial, long[] moduloPolynomial);
    long[] power(long[] polynomial, long exponent);
    
}

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
    
     // result = gcd(polynomial1, polynomial2) = a*polynomial1 + b*polynomial2
    long[] xgcd(long[] polynomial1, long[] polynomial2, long[] a, long[] b);
    long[] gcd(long[] polynomial1, long[] polynomial2);
    
    //result = a^{-1} % f
    long[] invert(long[] polynomial, long[] moduloPolynomial);
    long[] power(long[] polynomial, long exponent);
    int compare(long[] polynomial1, long[] polynomial2);
    
}

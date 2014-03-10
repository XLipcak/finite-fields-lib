/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.interfaces;

import muni.fi.gf2n.classes.Polynomial;



/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldPolynomialArithmetic {
    
    Polynomial add(Polynomial polynomial1, Polynomial polynomial2);
    Polynomial subtract(Polynomial polynomial1, Polynomial polynomial2);
    Polynomial multiply(Polynomial polynomial1, Polynomial polynomial2);
    Polynomial divide(Polynomial polynomial1, Polynomial polynomial2);
    Polynomial divide(Polynomial polynomial1, Polynomial polynomial2, Polynomial remainder);
    
    Polynomial gcd(Polynomial polynomial1, Polynomial polynomial2);
    
    //result = a^{-1} % f
    Polynomial invert(Polynomial polynomial, Polynomial moduloPolynomial);
    Polynomial power(Polynomial polynomial, long exponent);
    
}

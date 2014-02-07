/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.library.interfaces;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldPolynomialArithmetic {
    
    ArrayList<Long> add(List<Long> polynomial1, List<Long> polynomial2);
    ArrayList<Long> subtract(List<Long> polynomial1, List<Long> polynomial2);
    ArrayList<Long> multiply(List<Long> polynomial1, List<Long> polynomial2);
    ArrayList<Long> divide(List<Long> polynomial1, List<Long> polynomial2);
    //result = a^{-1} % f
    ArrayList<Long> invert(List<Long> polynomial, List<Long> moduloPolynomial);
    ArrayList<Long> power(List<Long> polynomial, long exponent);
    ArrayList<Long> gcd(List<Long> polynomial1, List<Long> polynomial2);
    int compare(List<Long> polynomial1, List<Long> polynomial2);
    
}

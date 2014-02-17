/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.interfaces;

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
    ArrayList<Long> divide(List<Long> polynomial1, List<Long> polynomial2, List<Long> remainder);
    
     // result = gcd(polynomial1, polynomial2) = a*polynomial1 + b*polynomial2
    ArrayList<Long> xgcd(List<Long> polynomial1, List<Long> polynomial2, List<Long> a, List<Long> b);
    ArrayList<Long> gcd(List<Long> polynomial1, List<Long> polynomial2);
    
    //result = a^{-1} % f
    ArrayList<Long> invert(List<Long> polynomial, List<Long> moduloPolynomial);
    ArrayList<Long> power(List<Long> polynomial, long exponent);
    int compare(List<Long> polynomial1, List<Long> polynomial2);
    
}

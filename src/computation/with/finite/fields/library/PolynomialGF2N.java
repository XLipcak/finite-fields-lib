/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.library;

import computation.with.finite.fields.library.interfaces.GaloisFieldPolynomialArithmetic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class PolynomialGF2N implements GaloisFieldPolynomialArithmetic {
    
    GF2N galoisField;
    
    public PolynomialGF2N(GF2N galoisField) {
        this.galoisField = galoisField;
    }
    
    public PolynomialGF2N(long reducingPolynomial) {
        galoisField = new GF2N(reducingPolynomial);
    }
    
    @Override
    public ArrayList<Long> add(List<Long> polynomial1, List<Long> polynomial2) {
        
        int length = Math.max(polynomial1.size(), polynomial2.size());
        ArrayList<Long> result = new ArrayList<>();
        
        for (int x = 0; x < length; x++) {
            try {
                long value = galoisField.add(polynomial1.get(x), polynomial2.get(x));
                result.add(value);
            } catch (IndexOutOfBoundsException ex) {
                if (x >= polynomial1.size()) {
                    result.add(polynomial2.get(x));
                } else {
                    result.add(polynomial1.get(x));
                }
            }
        }
        
        clearZeroValuesFromPolynomial(result);
        return result;
    }
    
    @Override
    public ArrayList<Long> subtract(List<Long> polynomial1, List<Long> polynomial2) {
        return add(polynomial1, polynomial2);
    }

    //Karatsuba would be faster
    @Override
    public ArrayList<Long> multiply(List<Long> polynomial1, List<Long> polynomial2) {
        
        ArrayList<Long> result = new ArrayList<>();
        
        for (int x = 0; x < (polynomial1.size() + polynomial2.size() - 1); x++) {
            result.add(0L);
        }
        
        
        for (int x = 0; x < polynomial2.size(); x++) {
            for (int y = 0; y < polynomial1.size(); y++) {
                if (polynomial1.get(y) != 0) {
                    int index = y + x;
                    long multiplyResult = galoisField.multiply(polynomial1.get(y), polynomial2.get(x));
                    long originalValue = result.get(index);
                    result.set(index, galoisField.add(originalValue, multiplyResult));
                }
            }
        }
        
        return result;
    }
    
    @Override
    public ArrayList<Long> divide(List<Long> polynomial1, List<Long> polynomial2) {
        ArrayList<Long> remainder = new ArrayList<>();
        return divideWithRemainder(polynomial1, polynomial2, remainder);
    }
    
    public ArrayList<Long> divideWithRemainder(List<Long> polynomial1, List<Long> polynomial2, List<Long> remainder) {
        
        ArrayList<Long> result = new ArrayList<>();
        
        ArrayList<Long> rem = new ArrayList<>();
        ArrayList<Long> numerator = new ArrayList<>();
        ArrayList<Long> mulResult;
        
        numerator.addAll(polynomial1);
        
        for (int x = 0; x < (polynomial1.size() - polynomial2.size()) + 1; x++) {
            result.add(0L);
            rem.add(0L);
        }
        
        while (numerator.size() >= polynomial2.size()) {
            //divide
            long value = galoisField.divide(numerator.get(numerator.size() - 1), polynomial2.get(polynomial2.size() - 1));
            result.set((numerator.size() - polynomial2.size()), value);

            //subtract divided part from numerator
            rem.set(numerator.size() - polynomial2.size(), value);
            mulResult = multiply(polynomial2, rem);
            numerator = subtract(numerator, mulResult);
            
            if (numerator.size() - polynomial2.size() >= 0) {
                rem.remove(numerator.size() - polynomial2.size());
            }
        }

        //set remainder
        remainder.addAll(numerator);
        
        if (result.isEmpty()) {
            result.add(0L);
        }
        
        return result;
    }
    
    public boolean clearZeroValuesFromPolynomial(List<Long> polynomial) {
        for (int x = polynomial.size() - 1; x >= 0; x--) {
            if (polynomial.get(x) == 0) {
                polynomial.remove(x);
            } else {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArrayList<Long> invert(List<Long> polynomial, List<Long> moduloPolynomial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<Long> power(List<Long> polynomial, long exponent) {
        
        ArrayList<Long> result = new ArrayList<>();
        result.addAll(polynomial);
        
        for (int x = 1; x < exponent; x++) {
            result = multiply(result, polynomial);
        }
        return result;
    }
    
    @Override
    public ArrayList<Long> gcd(List<Long> polynomial1, List<Long> polynomial2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int compare(List<Long> polynomial1, List<Long> polynomial2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

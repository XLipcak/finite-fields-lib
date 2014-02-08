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

        isValid(polynomial1, polynomial2);

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
        isValid(polynomial1, polynomial2);
        return add(polynomial1, polynomial2);
    }

    //Karatsuba would be faster
    @Override
    public ArrayList<Long> multiply(List<Long> polynomial1, List<Long> polynomial2) {

        isValid(polynomial1, polynomial2);

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
        isValid(polynomial1, polynomial2);
        ArrayList<Long> remainder = new ArrayList<>();
        return divide(polynomial1, polynomial2, remainder);
    }

    @Override
    public ArrayList<Long> divide(List<Long> polynomial1, List<Long> polynomial2, List<Long> remainder) {

        isValid(polynomial1, polynomial2);

        ArrayList<Long> result = new ArrayList<>();
        ArrayList<Long> rem = new ArrayList<>();
        ArrayList<Long> numerator = new ArrayList<>();
        ArrayList<Long> mulResult;

        numerator.addAll(polynomial1);

        //prepare result and remainder
        for (int x = 0; x < (polynomial1.size() - polynomial2.size()) + 1; x++) {
            result.add(0L);
            rem.add(0L);
        }

        while ((numerator.size() >= polynomial2.size())) {

            //divide
            long value = galoisField.divide(numerator.get(numerator.size() - 1), polynomial2.get(polynomial2.size() - 1));
            result.set((numerator.size() - polynomial2.size()), value);

            //subtract divided part from numerator
            rem.set(numerator.size() - polynomial2.size(), value);
            mulResult = multiply(polynomial2, rem);
            numerator = subtract(numerator, mulResult);

            //set remainder to correct length necessary for next divison
            while ((numerator.size() - polynomial2.size() + 1) < rem.size()) {
                if (rem.isEmpty()) {
                    break;
                }
                if (rem.size() > 0) {
                    rem.remove(rem.size() - 1);
                }
            }
        }

        //set remainder
        remainder.clear();
        remainder.addAll(numerator);
        if (remainder.isEmpty()) {
            remainder.add(0L);
        }

        if (result.isEmpty()) {
            result.add(0L);
        }

        return result;
    }

    @Override
    public ArrayList<Long> invert(List<Long> polynomial, List<Long> moduloPolynomial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Long> power(List<Long> polynomial, long exponent) {

        isValid(polynomial);

        ArrayList<Long> result = new ArrayList<>();
        result.addAll(polynomial);

        for (int x = 1; x < exponent; x++) {
            result = multiply(result, polynomial);
        }
        return result;
    }

    //Euclidean algorithm
    @Override
    public ArrayList<Long> gcd(List<Long> polynomial1, List<Long> polynomial2) {

        isValid(polynomial1, polynomial2);

        ArrayList<Long> result = new ArrayList<>();
        ArrayList<Long> remainder = new ArrayList<>();
        ArrayList<Long> numerator = new ArrayList<>();
        ArrayList<Long> denumerator = new ArrayList<>();


        numerator.addAll(polynomial1);
        denumerator.addAll(polynomial2);
        remainder.addAll(polynomial2);

        while (!((remainder.size() == 1) && (remainder.get(0) == 0))) {
            result = remainder;
            remainder = new ArrayList<>();
            divide(numerator, denumerator, remainder);
            numerator = denumerator;
            denumerator = remainder;
        }

        return result;

    }

    @Override
    public int compare(List<Long> polynomial1, List<Long> polynomial2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void isValid(List<Long> polynomial1, List<Long> polynomial2) {

        if (polynomial1.isEmpty() || polynomial2.isEmpty()) {
            throw new IllegalArgumentException("Polynomial argument is empty.");
        }

    }

    private void isValid(List<Long> polynomial) {

        if (polynomial.isEmpty()) {
            throw new IllegalArgumentException("Polynomial argument is empty.");
        }

    }
}

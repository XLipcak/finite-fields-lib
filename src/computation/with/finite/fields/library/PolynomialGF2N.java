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
        
        isValid(polynomial, polynomial);

        if ((polynomial.size() >= moduloPolynomial.size()) || (moduloPolynomial.size() == 1)) {
            throw new IllegalArgumentException("Cannot compute inverse for this args.");
        }

        ArrayList<Long> gcd;
        ArrayList<Long> result = new ArrayList<>();
        ArrayList<Long> temp = new ArrayList<>();

        gcd = xgcd(moduloPolynomial, polynomial, temp, result);

        if (gcd.size() != 1) {
            throw new IllegalArgumentException("Cannot compute inverse for this args.");
        }

        return result;
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

        //prepare division
        numerator.addAll(polynomial1);
        denumerator.addAll(polynomial2);
        remainder.addAll(polynomial2);

        //find greatest greatest common divisor, last positive remainder
        while (!((remainder.size() == 1) && (remainder.get(0) == 0))) {
            result = remainder;
            remainder = new ArrayList<>();
            divide(numerator, denumerator, remainder);
            numerator = denumerator;
            denumerator = remainder;
        }

        return result;

    }

    // result = gcd(polynomial1, polynomial2) = a*polynomial1 + b*polynomial2
    public ArrayList<Long> xgcd(List<Long> polynomial1, List<Long> polynomial2, List<Long> a, List<Long> b) {

        isValid(polynomial1, polynomial2);

        ArrayList<Long> result = new ArrayList<>();
        ArrayList<Long> remainder = new ArrayList<>();
        ArrayList<Long> numerator = new ArrayList<>();
        ArrayList<Long> denumerator = new ArrayList<>();


        ArrayList<Long> temp = new ArrayList<>();
        ArrayList<ArrayList<Long>> resultList = new ArrayList<>();
        ArrayList<ArrayList<Long>> bezoutIdentity = new ArrayList<>();

        //prepare division
        numerator.addAll(polynomial1);
        denumerator.addAll(polynomial2);
        remainder.addAll(polynomial2);

        //find greatest greatest common divisor, last positive remainder
        while (!((remainder.size() == 1) && (remainder.get(0) == 0))) {
            result = remainder;
            remainder = new ArrayList<>();
            temp = divide(numerator, denumerator, remainder);

            //resultList is used to find Bezout's Identity
            resultList.add(numerator);
            resultList.add(denumerator);
            resultList.add(temp);

            numerator = denumerator;
            denumerator = remainder;
        }



        resultList.remove(resultList.size() - 1);
        resultList.remove(resultList.size() - 1);
        resultList.remove(resultList.size() - 1);

        temp.clear();
        temp.add(1l);
        bezoutIdentity.add(temp);
        bezoutIdentity.add(resultList.get(resultList.size() - 3));
        bezoutIdentity.add(resultList.get(resultList.size() - 2));
        bezoutIdentity.add(resultList.get(resultList.size() - 1));


        //find Bezout's identity from resultList data counted in Euclidean algorithm
        while (resultList.size() != 3) {
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);
            resultList.remove(resultList.size() - 1);

            temp = bezoutIdentity.get(0);
            bezoutIdentity.set(0, bezoutIdentity.get(3));
            bezoutIdentity.set(1, resultList.get(resultList.size() - 3));
            bezoutIdentity.set(2, resultList.get(resultList.size() - 2));
            bezoutIdentity.set(3, add(temp, multiply(bezoutIdentity.get(3), resultList.get(resultList.size() - 1))));
        }
        
        System.out.println("Test:  " + add(multiply(bezoutIdentity.get(0), bezoutIdentity.get(1)),
                multiply(bezoutIdentity.get(2), bezoutIdentity.get(3))));
        
        //save Bezout's coefficients and return gcd
        a.addAll(bezoutIdentity.get(0));
        b.addAll(bezoutIdentity.get(3));

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

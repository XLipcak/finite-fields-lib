package muni.fi.gf2n.classes;

import java.util.Arrays;
import java.util.Random;

/**
 * Class Polynomial is objective representation of polynomial. Data are stored
 * in array and each coefficient of polynomial is accessed by its numerical
 * index. Highest coefficients of polynomial are stored at highest positions of
 * array.
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class Polynomial {

    private long[] elements;
    private int size;

    /**
     * Constructs a Polynomial of size 0 with no elements.
     */
    public Polynomial() {
        elements = new long[0];
        size = 0;
    }

    /**
     * Constructs a Polynomial of specified size. All coefficients of this
     * polynomial are set to zero.
     *
     * @param size size of constructed polynomial
     */
    public Polynomial(int size) {
        elements = new long[size];
        this.size = size;
    }

    /**
     * Constructs a Polynomial. Constructed Polynomial is identical with
     * Polynomial given as attribute to this constructor.
     *
     * @param Polynomial Polynomial to clone
     */
    public Polynomial(Polynomial polynomial) {
        this(polynomial.getSize());
        for (int x = 0; x < size; x++) {
            elements[x] = polynomial.getElement(x);
        }
    }

    /**
     * Constructs a Polynomial. Constructed Polynomial is identical with
     * Polynomial represented by array given as attribute to this constructor.
     *
     * @param Polynomial Polynomial to clone
     */
    public Polynomial(long[] polynomial) {
        size = polynomial.length;
        for (int x = 0; x < size; x++) {
            elements[x] = polynomial[x];
        }
    }

    /**
     * Constructs a Polynomial. Constructed Polynomial has specified size.
     * Highest coefficient is a positive non-zero number and all the other
     * coefficients are generated randomly between 0 and 2^bitSize - 1 .
     *
     * @param size size of constructed polynomial
     * @param bitSize bit size of values generated randomly
     */
    public Polynomial(int size, int bitSize) {
        this(size);
        Random rn = new Random();

        for (int x = 0; x < size - 1; x++) {
            elements[x] = rn.nextInt((int) Math.pow(2, bitSize));
        }
        elements[size - 1] = rn.nextInt((int) Math.pow(2, bitSize) - 1) + 1;
    }

    /**
     * Set coefficient of polynomial at specified index to specified value.
     *
     * @param index index of coefficient
     * @param value value to be set
     */
    public void setElement(int index, long value) {
        elements[index] = value;
    }

    /**
     * Returns coefficient of polynomial at specified index.
     *
     * @param index index of coefficient
     * @return coefficient at specified index
     */
    public long getElement(int index) {
        return elements[index];
    }

    /**
     * Set size of polynomial. After this method is called, all coefficients are
     * set to zero.
     *
     * @param size new size of polynomial
     */
    public void setSize(int size) {
        this.size = size;
        elements = new long[size];
    }

    /**
     * Returns size of a polynomial. Size of polynomial equals to its degree - 1
     * for all polynomials with non-zero coefficient at highest index.
     *
     * @return size of a polynomial
     */
    public int getSize() {
        return size;
    }

    /**
     * Clear all zero values at highest coefficients of polynomial. This
     * coefficients are removed, degree of new polynomial is changed.
     *
     * @return polynomial without zero values at highest coefficient
     */
    public Polynomial clearZeroValues() {
        long[] newElements;
        for (int x = size - 1; x >= 0; x--) {
            if (elements[x] != 0) {
                newElements = new long[x + 1];
                for (int y = 0; y < x + 1; y++) {
                    newElements[y] = elements[y];
                }
                size = x + 1;
                return this;
            }
        }
        size = 0;
        elements = new long[0];
        return this;
    }

    /**
     * Returns a string representation of the polynomial. String returned by
     * this method is in form (a + bx^1 + cx^2 ... zx^n).
     *
     * @return string representation of the polynomial
     */
    public String toStringAsPoly() {
        String result = new String();

        result += "[";
        if (size > 0) {
            result += elements[0] + " + ";
        }
        for (int x = 1; x < size; x++) {
            if (x == size - 1) {
                result += elements[x] + "x^" + x;
            } else {
                result += elements[x] + "x^" + x + " + ";
            }
        }
        result += "]";
        return result;
    }

    @Override
    public boolean equals(Object polynomial) {
        if (!(polynomial instanceof Polynomial)) {
            return false;
        }

        if (((Polynomial) polynomial).getSize() != size) {
            return false;
        }

        for (int x = 0; x < size; x++) {
            if (elements[x] != ((Polynomial) polynomial).getElement(x)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Arrays.hashCode(this.elements);
        return hash;
    }

    @Override
    public String toString() {
        String result = new String();

        result += "[ ";
        for (int x = 0; x < size; x++) {
            if (x == size - 1) {
                result += elements[x];
            } else {
                result += elements[x] + ", ";
            }
        }
        result += " ]";

        return result;
    }
}

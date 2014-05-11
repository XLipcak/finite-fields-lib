package muni.fi.gf2n.classes;

import java.util.Arrays;
import java.util.Random;

/**
 * Class Vector is objective representation of vector. Data are stored in array
 * and each element of vector is accessed by its numerical index.
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class Vector {

    private long[] elements;
    private int size;

    /**
     * Constructs a Vector of size 0 with no elements.
     */
    public Vector() {
        elements = new long[0];
        size = 0;
    }

    /**
     * Constructs a Vector of specified size. All elements of this vector are
     * set to zero.
     *
     * @param size size of constructed polynomial
     */
    public Vector(int size) {
        
        elements = new long[size];
        this.size = size;
    }

    /**
     * Constructs a Vector. Constructed Vector is identical with Vector given as
     * attribute to this constructor.
     *
     * @param vector Vector to clone
     */
    public Vector(Vector vector) {
        this(vector.getSize());
        for (int x = 0; x < size; x++) {
            elements[x] = vector.getElement(x);
        }
    }

    /**
     * Constructs a Vector. Constructed Vector is identical with Vector
     * represented by array given as attribute to this constructor.
     *
     * @param vector Vector to clone
     */
    public Vector(long[] vector) {
        size = vector.length;
        for (int x = 0; x < size; x++) {
            elements[x] = vector[x];
        }
    }

    /**
     * Constructs a Vector. Constructed Vector has specified size and is filled
     * up with numbers generated randomly between 0 and 2^bitSize - 1.
     *
     * @param size size of vector
     * @param bitSize bit size of values generated randomly
     */
    public Vector(int size, int bitSize) {
        this(size);
        if (bitSize < 1) {
            return;
        }
        
        Random rn = new Random();

        for (int x = 0; x < size; x++) {
            elements[x] = Math.abs(rn.nextLong()) & generateBitMask(bitSize);
        }
    }

    /**
     * Set element of vector at specified index to specified value.
     *
     * @param index index of element
     * @param value value to be set
     */
    public void setElement(int index, long value) {
        elements[index] = value;
    }

    /**
     * Returns element of vector at specified index.
     *
     * @param index index of element
     * @return element at specified index
     */
    public long getElement(int index) {
        return elements[index];
    }

    /**
     * Returns size of a vector.
     *
     * @return size of a vector
     */
    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object vector) {
        if (!(vector instanceof Vector)) {
            return false;
        }

        if (((Vector) vector).getSize() != size) {
            return false;
        }

        for (int x = 0; x < size; x++) {
            if (elements[x] != ((Vector) vector).getElement(x)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Arrays.hashCode(this.elements);
        return hash;
    }

    @Override
    public String toString() {
        String result = new String();

        result += "( ";
        for (int x = 0; x < size; x++) {
            if (x == size - 1) {
                result += elements[x];
            } else {
                result += elements[x] + ", ";
            }
        }
        result += " )";

        return result;
    }

    private long generateBitMask(int length) {
        int result = 0;
        for (int x = 0; x < length; x++) {
            result ^= (1 << x);
        }
        return result;
    }
}

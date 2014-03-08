/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.classes;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 * 
 * Class Polynomial
 * 
 */
public class Polynomial {
    
    private long[] elements;
    private int size;

    public Polynomial() {
        elements = new long[0];
        size = 0;
    }

    public Polynomial(int size) {
        elements = new long[size];
        this.size = size;
    }

    public Polynomial(Polynomial polynomial) {
        this(polynomial.getSize());
        for (int x = 0; x < size; x++) {
            elements[x] = polynomial.getElement(x);
        }
    }
    
    public Polynomial(long[] polynomial){
        size = polynomial.length;
        for(int x = 0; x < size; x++){
            elements[x] = polynomial[x];
        }
    }

    /*
     * Generate random polynomial, each element of size 2^bitSize
     */
    public Polynomial(int size, int bitSize) {
        this(size);
        Random rn = new Random();

        for (int x = 0; x < size; x++) {
            elements[x] = rn.nextInt((int) Math.pow(2, bitSize));
        }
    }

    public void setElement(int index, long value) {
        elements[index] = value;
    }

    public long getElement(int index) {
        return elements[index];
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object polynomial) {
        if (!(polynomial instanceof Polynomial)) {
            return false;
        }
        
        if (((Polynomial)polynomial).getSize() != size) {
            return false;
        }

        for (int x = 0; x < size; x++) {
            if (elements[x] != ((Polynomial)polynomial).getElement(x)) {
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
    
    public String toStringAsPoly() {
        String result = new String();

        if(size > 0){
            result += elements[0] + " + ";
        }
        for (int x = 1; x < size; x++) {
            if (x == size - 1) {
                result += elements[x] + "x^" + x;
            } else {
                result += elements[x] + "x^" + x + " + ";
            }
        }

        return result;
    }
    
}

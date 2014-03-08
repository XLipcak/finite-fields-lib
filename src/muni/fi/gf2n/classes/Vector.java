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
 * Class Vector
 *
 */
public class Vector {

    private long[] elements;
    private int size;

    public Vector() {
        elements = new long[0];
        size = 0;
    }

    public Vector(int size) {
        elements = new long[size];
        this.size = size;
    }

    public Vector(Vector vector) {
        this(vector.getSize());
        for (int x = 0; x < size; x++) {
            elements[x] = vector.getElement(x);
        }
    }
    
    public Vector(long[] vector){
        size = vector.length;
        for(int x = 0; x < size; x++){
            elements[x] = vector[x];
        }
    }

    /*
     * Generate random vector, each element of size 2^bitSize
     */
    public Vector(int size, int bitSize) {
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
    public boolean equals(Object vector) {
        if (!(vector instanceof Vector)) {
            return false;
        }
        
        if( ((Vector)vector).getSize() != size ){
            return false;
        }

        for (int x = 0; x < size; x++) {
            if (elements[x] != ((Vector)vector).getElement(x)) {
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
}

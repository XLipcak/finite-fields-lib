/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GaloisFieldsValidityTesting;

import java.util.Random;
import muni.fi.gf2n.classes.GF2N;
import muni.fi.gf2n.classes.Vector;
import muni.fi.gf2n.classes.VectorGF2N;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 * 
 * Class VectorGF2N Testing
 * 
 */
public class VectorGF2NTest {

    private GF2N gf;
    private Random rn;

    public VectorGF2NTest() {
    }

    @Before
    public void setUp() {
        rn = new Random();
        gf = new GF2N(4194307l);
    }

    @Test
    public void testAdd() {
        VectorGF2N vecGF = new VectorGF2N(gf);
        for (int test = 0; test < 32; test++) {
            int size = rn.nextInt(128) + 1;
            Vector vector1 = new Vector(size, gf.getFieldSize());
            Vector vector2 = new Vector(size, 0);

            assertEquals("Vector cannot be changed after addition with zero vector.",
                    vector1, vecGF.add(vector1, vector2));
            assertEquals("Addidion of two identical vectors must be zero vector.",
                    vector2, vecGF.add(vector1, vector1));
            assertEquals("Addidion of two zero vectors must be zero vector.",
                    vector2, vecGF.add(vector2, vector2));
            try {
                vecGF.add(vector1, new Vector(size + 1));
                fail("Addition of vectors with different dimensions should throw exception.");
            } catch (IllegalArgumentException ex) {
                //OK
            }
        }
    }
    
    @Test
    public void testSubtract() {
        VectorGF2N vecGF = new VectorGF2N(gf);
        for (int test = 0; test < 32; test++) {
            int size = rn.nextInt(128) + 1;
            Vector vector1 = new Vector(size, gf.getFieldSize());
            Vector vector2 = new Vector(size, 0);

            assertEquals("Vector cannot be changed after subtraction of zero vector.",
                    vector1, vecGF.add(vector1, vector2));
            assertEquals("Subtraction of two identical vectors must be zero vector.",
                    vector2, vecGF.add(vector1, vector1));
            assertEquals("Subtraction of two zero vectors must be zero vector.",
                    vector2, vecGF.add(vector2, vector2));
            try {
                vecGF.add(vector1, new Vector(size + 1));
                fail("Subtraction of vectors with different dimensions should throw exception.");
            } catch (IllegalArgumentException ex) {
                //OK
            }
        }
    }
    
    @Test
    public void testMultiply() {
        VectorGF2N vecGF = new VectorGF2N(gf);
        for (int test = 0; test < 32; test++) {
            int size = rn.nextInt(128) + 1;
            Vector vector1 = new Vector(size, gf.getFieldSize());
            Vector vector2 = new Vector(size, 0);
            
            assertEquals("Vector cannot be changed after being multiplied by 1.",
                    vector1, vecGF.multiply(vector1, 1));
            assertEquals("Vector after being multiplied by 0 must be zero vector.",
                    vector2, vecGF.multiply(vector1, 0));
            try {
                vecGF.multiply(vector1, -1);
                fail("Multiplying by negative scalar value should throw exception.");
            } catch (IllegalArgumentException ex) {
                //OK
            }
        }
    }
}
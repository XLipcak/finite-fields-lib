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
 * Class VectorGF2NTest is used to test computation with vectors with
 * elements in Galois Field.
 * It includes testing with constants and testing with values generated randomly.
 * Results of all tests with constant values were computed by NTL library.
 *
 * @author Jakub Lipcak, Masaryk University
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
        
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        VectorGF2N vecGF = new VectorGF2N(gf);
        
        Vector vector1 = new Vector(3);
        vector1.setElement(0, 101);
        vector1.setElement(1, 26);
        vector1.setElement(2, 44);
        
        Vector vector2 = new Vector(3);
        vector2.setElement(0, 182);
        vector2.setElement(1, 173);
        vector2.setElement(2, 125);
        
        Vector resultVector = new Vector(3);
        resultVector.setElement(0, 211);
        resultVector.setElement(1, 183);
        resultVector.setElement(2, 81);
        assertEquals("Addition of vectors with constant values failed.", resultVector, vecGF.add(vector1, vector2));
        
        vector1.setElement(0, 121);
        vector1.setElement(1, 54);
        vector1.setElement(2, 141);
        
        vector2.setElement(0, 239);
        vector2.setElement(1, 198);
        vector2.setElement(2, 23);
        
        resultVector.setElement(0, 150);
        resultVector.setElement(1, 240);
        resultVector.setElement(2, 154);
        assertEquals("Addition of vectors with constant values failed.", resultVector, vecGF.add(vector1, vector2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        vecGF = new VectorGF2N(gf);
        for (int test = 0; test < 1000; test++) {
            int size = rn.nextInt(128) + 1;
            vector1 = new Vector(size, gf.getFieldSize());
            vector2 = new Vector(size, 0);

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
        
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        VectorGF2N vecGF = new VectorGF2N(gf);
        
        Vector vector1 = new Vector(3);
        vector1.setElement(0, 253);
        vector1.setElement(1, 112);
        vector1.setElement(2, 92);
        
        Vector vector2 = new Vector(3);
        vector2.setElement(0, 168);
        vector2.setElement(1, 237);
        vector2.setElement(2, 19);
        
        Vector resultVector = new Vector(3);
        resultVector.setElement(0, 85);
        resultVector.setElement(1, 157);
        resultVector.setElement(2, 79);
        assertEquals("Subtraction of vectors with constant values failed.", resultVector, vecGF.subtract(vector1, vector2));
        
        vector1.setElement(0, 120);
        vector1.setElement(1, 161);
        vector1.setElement(2, 252);
        
        vector2.setElement(0, 199);
        vector2.setElement(1, 185);
        vector2.setElement(2, 56);
        
        resultVector.setElement(0, 191);
        resultVector.setElement(1, 24);
        resultVector.setElement(2, 196);
        assertEquals("Subtraction of vectors with constant values failed.", resultVector, vecGF.subtract(vector1, vector2));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        vecGF = new VectorGF2N(gf);
        for (int test = 0; test < 1000; test++) {
            int size = rn.nextInt(128) + 1;
            vector1 = new Vector(size, gf.getFieldSize());
            vector2 = new Vector(size, 0);

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
        
        /*
         * Test with constant values.
         */
        gf = new GF2N(283);
        VectorGF2N vecGF = new VectorGF2N(gf);
        
        Vector vector1 = new Vector(3);
        vector1.setElement(0, 147);
        vector1.setElement(1, 57);
        vector1.setElement(2, 167);
        
        long scalarValue = 188;
        
        Vector resultVector = new Vector(3);
        resultVector.setElement(0, 89);
        resultVector.setElement(1, 116);
        resultVector.setElement(2, 192);
        assertEquals("Multiplication of vectors with constant values failed.", resultVector, vecGF.multiply(vector1, scalarValue));
        
        vector1.setElement(0, 17);
        vector1.setElement(1, 96);
        vector1.setElement(2, 81);
        
        scalarValue = 195;
        
        resultVector.setElement(0, 71);
        resultVector.setElement(1, 53);
        resultVector.setElement(2, 97);
        assertEquals("Multiplication of vectors with constant values failed.", resultVector, vecGF.multiply(vector1, scalarValue));


        /*
         * Basic test.
         */
        gf = new GF2N(4194307l);
        vecGF = new VectorGF2N(gf);
        for (int test = 0; test < 1000; test++) {
            int size = rn.nextInt(128) + 1;
            vector1 = new Vector(size, gf.getFieldSize());
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
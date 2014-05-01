package AESTesting;

import java.util.Random;
import muni.fi.aes.AES;
import muni.fi.aes.BlockCipherMode;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

/**
 * Class AESTestHelper provides methods, which are often used by other AES test
 * classes.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class AESTestHelper {

    /*
     * Create random text of random length, encrypt and decrypt it, check result.
     */
    public static void testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode mode) {
        AES aes = new AES(mode);
        Random rn = new Random();

        byte[] key = new byte[16];
        byte[] ciphertext = new byte[16];
        byte[] initVector = new byte[16];

        for (int x = 0; x < 100; x++) {
            int textLength = rn.nextInt(1024) + 1;
            byte[] plaintext = new byte[textLength];
            for (int y = 0; y < 16; y++) {
                key[y] = (byte) rn.nextInt(256);
                initVector[y] = (byte) rn.nextInt(256);
            }
            for (int y = 0; y < textLength; y++) {
                plaintext[y] = (byte) rn.nextInt(256);
            }
            ciphertext = aes.encrypt(plaintext, key, initVector);
            assertArrayEquals("Plaintext after encryption and decryption must be the same.",
                    plaintext, reduceArray(aes.decrypt(ciphertext, key, initVector), textLength));
        }
    }

    /*
     * Reduce input array to length. Only zeroes are expected after length index.
     */
    public static byte[] reduceArray(byte[] input, int length) {
        byte[] result = new byte[length];
        System.arraycopy(input, 0, result, 0, length);
        for (int x = length; x < input.length; x++) {
            if (input[x] != 0) {
                fail();
            }
        }
        return result;
    }

    /*
     * Use test vectors to encrypt data, check result.
     */
    public static void testVectorsEncryption(BlockCipherMode mode, String[] keys, String[] plainTexts,
            String[] expectedCipherTexts, String[] initializationVectors) {

        AES aes = new AES(mode);

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals("Encrypted plaintext differs from expected result vector.",
                    expectedCipherText, aes.encrypt(plainText, key, initializationVector));
        }
    }

    /*
     * Use test vectors to decrypt data, check result.
     */
    public static void testVectorsDecryption(BlockCipherMode mode, String[] keys, String[] expectedPlainTexts,
            String[] cipherTexts, String[] initializationVectors) {

        AES aes = new AES(mode);

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(expectedPlainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(cipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals("Decrypted plaintext differs from expected result vector.",
                    plainText, aes.decrypt(expectedCipherText, key, initializationVector));
        }
    }

    /*
     * Convert pairs of Hexadecimal chars to array of bytes.
     */
    public static byte[] hexaStringToByteArray(String str) {
        byte[] result = new byte[(str.length() / 2)];

        for (int x = 0; x < result.length; x++) {
            String hexa = str.substring(x * 2, (x * 2) + 2);
            result[x] = (byte) Long.parseLong(hexa, 16);
        }

        return result;
    }
}

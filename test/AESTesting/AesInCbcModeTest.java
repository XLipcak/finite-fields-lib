package AESTesting;

import java.util.Random;
import muni.fi.aes.AES;
import muni.fi.aes.BlockCipherMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * AesInCbcModeTest is class used to test encryption and decryption in CBC mode
 * with AES class.
 *
 * Sources of all AES Test-vectors used in this class:
 * http://www.inconteam.com/software-development/41-encryption/55-aes-test-vectors
 * http://csrc.nist.gov/groups/STM/cavp/documents/aes/KAT_AES.zip
 * http://csrc.nist.gov/publications/nistpubs/800-38a/sp800-38a.pdf
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class AesInCbcModeTest {

    public AesInCbcModeTest() {
    }

    @Test
    public void testEncryptDecryptCbcMode() {
        testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.CBC);
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInCbcMode() {

        String[] keys = {"00000000000000000000000000000000",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c"};
        String[] plainTexts = {"f34481ec3cc627bacd5dc3fb08f273e6",
            "6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"0336763e966d92595a567cc9ce537f5e",
            "7649abac8119b246cee98e9b12e9197d",
            "5086cb9b507219ee95db113a917678b2",
            "73bed6b8e3c1743b7116e69e22229516",
            "3ff1caa1681fac09120eca307586e1a7"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "000102030405060708090A0B0C0D0E0F",
            "7649ABAC8119B246CEE98E9B12E9197D",
            "5086CB9B507219EE95DB113A917678B2",
            "73BED6B8E3C1743B7116E69E22229516"};

        testVectorsEncryption(BlockCipherMode.CBC, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInCbcMode() {

        String[] keys = {"e9f065d7c13573587f7875357dfbb16c53489f6a4bd0f7cd",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b"};
        String[] plainTexts = {"00000000000000000000000000000000",
            "6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"0956259c9cd5cfd0181cca53380cde06",
            "4f021db243bc633d7178183a9fa071e8",
            "b4d9ada9ad7dedf4e5e738763f69145a",
            "571b242012fb7ae07fa9baac3df102e0",
            "08b0e27988598881d920a9e64f5615cd"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "000102030405060708090A0B0C0D0E0F",
            "4F021DB243BC633D7178183A9FA071E8",
            "B4D9ADA9AD7DEDF4E5E738763F69145A",
            "571B242012FB7AE07FA9BAAC3DF102E0"};

        testVectorsEncryption(BlockCipherMode.CBC, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInCbcMode() {

        String[] keys = {"0000000000000000000000000000000000000000000000000000000000000000",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4"};
        String[] plainTexts = {"80000000000000000000000000000000",
            "6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"ddc6bf790c15760d8d9aeb6f9a75fd4e",
            "f58c4c04d6e5f1ba779eabfb5f7bfbd6",
            "9cfc4e967edb808d679f777bc6702c7d",
            "39f23369a9d9bacfa530e26304231461",
            "b2eb05e2c39be9fcda6c19078c6a9d1b"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "000102030405060708090A0B0C0D0E0F",
            "F58C4C04D6E5F1BA779EABFB5F7BFBD6",
            "9CFC4E967EDB808D679F777BC6702C7D",
            "39F23369A9D9BACFA530E26304231461"};

        testVectorsEncryption(BlockCipherMode.CBC, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith128BitKeyInCbcMode() {

        String[] keys = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] expectedPlainTexts = {"f0000000000000000000000000000000",
            "f8000000000000000000000000000000"};
        String[] cipherTexts = {"96d9fd5cc4f07441727df0f33e401a36",
            "30ccdb044646d7e1f3ccea3dca08b8c0"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};

        testVectorsDecryption(BlockCipherMode.CBC, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith192BitKeyInCbcMode() {

        String[] keys = {"000000000000000000000000000000000000000000000000",
            "000000000000000000000000000000000000000000000000",
            "000000000000000000000000000000000000000000000000"};
        String[] expectedPlainTexts = {"fffffffffffffff80000000000000000",
            "fffffffffffffffc0000000000000000",
            "fffffffffffffffe0000000000000000"};
        String[] cipherTexts = {"3a3c15bfc11a9537c130687004e136ee",
            "22c0a7678dc6d8cf5c8a6d5a9960767c",
            "b46b09809d68b9a456432a79bdc2e38c"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "00000000000000000000000000000000",
            "00000000000000000000000000000000"};

        testVectorsDecryption(BlockCipherMode.CBC, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith256BitKeyInCbcMode() {

        String[] keys = {"ffffffffffffffffffffffffffffff8000000000000000000000000000000000",
            "ffffffffffffffffffffffffffffffc000000000000000000000000000000000",
            "ffffffffffffffffffffffffffffffe000000000000000000000000000000000"};
        String[] expectedPlainTexts = {"00000000000000000000000000000000",
            "00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] cipherTexts = {"a866bc65b6941d86e8420a7ffb0964db",
            "8193c6ff85225ced4255e92f6e078a14",
            "9661cb2424d7d4a380d547f9e7ec1cb9"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "00000000000000000000000000000000",
            "00000000000000000000000000000000"};

        testVectorsDecryption(BlockCipherMode.CBC, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    //create random text of random length, encrypt and decrypt it, check result
    private void testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode mode) {
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
                    plaintext, reduceArray(aes.decrypt(ciphertext, key, initVector), textLength, true));
        }
    }

    /*
     * Reduce input array to length. Only zeroes are expected after length index.
     */
    private byte[] reduceArray(byte[] input, int length, boolean zeroControl) {
        byte[] result = new byte[length];
        System.arraycopy(input, 0, result, 0, length);
        for (int x = length; x < input.length; x++) {
            if (input[x] != 0 && zeroControl) {
                fail();
            }
        }
        return result;
    }

    //use test vectors to encrypt data, check result
    private void testVectorsEncryption(BlockCipherMode mode, String[] keys, String[] plainTexts,
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

    //use test vectors to decrypt data, check result
    private void testVectorsDecryption(BlockCipherMode mode, String[] keys, String[] expectedPlainTexts,
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
    private byte[] hexaStringToByteArray(String str) {
        byte[] result = new byte[(str.length() / 2)];

        for (int x = 0; x < result.length; x++) {
            String hexa = str.substring(x * 2, (x * 2) + 2);
            result[x] = (byte) Long.parseLong(hexa, 16);
        }

        return result;
    }
}
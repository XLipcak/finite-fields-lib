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
 * AesInOfbModeTest is class used to test encryption and decryption in OFB mode
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
public class AesInOfbModeTest {

    public AesInOfbModeTest() {
    }

    @Test
    public void testEncryptDecryptOfbMode() {
        testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.OFB);
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInOfbMode() {

        String[] keys = {"2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"3b3fd92eb72dad20333449f8e83cfb4a",
            "7789508d16918f03f53c52dac54ed825",
            "9740051e9c5fecf64344f7a82260edcc",
            "304c6528f659c77866a510d9c1d6ae5e"};
        String[] initializationVectors = {"000102030405060708090A0B0C0D0E0F",
            "50FE67CC996D32B6DA0937E99BAFEC60",
            "D9A4DADA0892239F6B8B3D7680E15674",
            "A78819583F0308E7A6BF36B1386ABF23"};

        testVectorsEncryption(BlockCipherMode.OFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInOfbMode() {

        String[] keys = {"8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"cdc80d6fddf18cab34c25909c99a4174",
            "fcc28b8d4c63837c09e81700c1100401",
            "8d9a9aeac0f6596f559c6d4daf59a5f2",
            "6d9f200857ca6c3e9cac524bd9acc92a"};
        String[] initializationVectors = {"000102030405060708090A0B0C0D0E0F",
            "A609B38DF3B1133DDDFF2718BA09565E",
            "52EF01DA52602FE0975F78AC84BF8A50",
            "BD5286AC63AABD7EB067AC54B553F71D"};

        testVectorsEncryption(BlockCipherMode.OFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInOfbMode() {

        String[] keys = {"603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"dc7e84bfda79164b7ecd8486985d3860",
            "4febdc6740d20b3ac88f6ad82a4fb08d",
            "71ab47a086e86eedf39d1c5bba97c408",
            "0126141d67f37be8538f5a8be740e484"};
        String[] initializationVectors = {"000102030405060708090A0B0C0D0E0F",
            "B7BF3A5DF43989DD97F0FA97EBCE2F4A",
            "E1C656305ED1A7A6563805746FE03EDC",
            "41635BE625B48AFC1666DD42A09D96E7"};

        testVectorsEncryption(BlockCipherMode.OFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith128BitKeyInOfbMode() {

        String[] keys = {"ffff0000000000000000000000000000",
            "ffff8000000000000000000000000000"};
        String[] expectedPlainTexts = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] cipherTexts = {"97d0754fe68f11b9e375d070a608c884",
            "c6a0b3e998d05068a5399778405200b4"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};

        testVectorsDecryption(BlockCipherMode.OFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith192BitKeyInOfbMode() {

        String[] keys = {"ffffffffffffffffffffffe0000000000000000000000000",
            "fffffffffffffffffffffff0000000000000000000000000"};
        String[] expectedPlainTexts = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] cipherTexts = {"88330baa4f2b618fc9d9b021bf503d5a",
            "fc9e0ea22480b0bac935c8a8ebefcdcf"};
        String[] initializationVectors = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};

        testVectorsDecryption(BlockCipherMode.OFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith256BitKeyInOfbMode() {

        String[] keys = {"0000000000000000000000000000000000000000000000000000000000000000",
            "0000000000000000000000000000000000000000000000000000000000000000"};
        String[] expectedPlainTexts = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] cipherTexts = {"7bfe9d876c6d63c1d035da8fe21c409d",
            "acdace8078a32b1a182bfa4987ca1347"};
        String[] initializationVectors = {"fffffffffffffffffffffffffffffffe",
            "ffffffffffffffffffffffffffffffff"};

        testVectorsDecryption(BlockCipherMode.OFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
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
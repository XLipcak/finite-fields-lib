/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Jakub Lipcak, Masaryk University
 *
 * AES Test vectors sources:
 * http://www.inconteam.com/software-development/41-encryption/55-aes-test-vectors
 * http://csrc.nist.gov/groups/STM/cavp/documents/aes/KAT_AES.zip
 *
 */
public class AESTest {

    public AESTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testEncryptDecryptBlock() {
        AES aes = new AES();

        byte[] key = new byte[16];
        byte[] plaintext = new byte[16];
        byte[] ciphertext = new byte[16];
        Random rn = new Random();

        for (int x = 0; x < 1000; x++) {

            for (int y = 0; y < 16; y++) {
                key[y] = (byte) rn.nextInt(256);
                plaintext[y] = (byte) rn.nextInt(256);
            }
            ciphertext = aes.encryptBlock(plaintext, key);
            assertArrayEquals(plaintext, aes.decryptBlock(ciphertext, key));
        }
    }

    @Test
    public void testEncryptDecryptEcbMode() {
        testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.ECB);
    }

    @Test
    public void testEncryptDecryptCbcMode() {
        testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.CBC);
    }

    @Test
    public void testEncryptDecryptCfbMode() {
        testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.CFB);
    }

    @Test
    public void testEncryptDecryptOfbMode() {
        testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.OFB);
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInEcbMode() {
        AES aes = new AES(BlockCipherMode.ECB);

        String[] keys = {"2b7e151628aed2a6abf7158809cf4f3c"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};

        String[] expectedCipherTexts = {"3ad77bb40d7a3660a89ecaf32466ef97",
            "f5d3d58503b9699de785895a96fdbaaf",
            "43b1cd7f598ece23881b00e3ed030688",
            "7b0c785e27e8ad3f8223207104725dd4"};

        for (int x = 0; x < 4; x++) {

            byte[] key = hexaStringToByteArray(keys[0]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);

            assertArrayEquals(aes.encryptECB(plainText, key), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInEcbMode() {
        AES aes = new AES(BlockCipherMode.ECB);

        String[] keys = {"8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};

        String[] expectedCipherTexts = {"bd334f1d6e45f25ff712a214571fa5cc",
            "974104846d0ad3ad7734ecb3ecee4eef",
            "ef7afd2270e2e60adce0ba2face6444e",
            "9a4b41ba738d6c72fb16691603c18e0e"};

        for (int x = 0; x < 4; x++) {

            byte[] key = hexaStringToByteArray(keys[0]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);

            assertArrayEquals(aes.encryptECB(plainText, key), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInEcbMode() {
        AES aes = new AES(BlockCipherMode.ECB);

        String[] keys = {"603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};

        String[] expectedCipherTexts = {"f3eed1bdb5d2a03c064b5a7e3db181f8",
            "591ccb10d410ed26dc5ba74a31362870",
            "b6ed21b99ca6f4f9f153e7b1beafed1d",
            "23304b7a39f9f3ff067d8d8f9e24ecc7"};

        for (int x = 0; x < 4; x++) {

            byte[] key = hexaStringToByteArray(keys[0]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);

            assertArrayEquals(aes.encryptECB(plainText, key), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInCbcMode() {
        AES aes = new AES(BlockCipherMode.CBC);

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

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInCbcMode() {
        AES aes = new AES(BlockCipherMode.CBC);

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

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInCbcMode() {
        AES aes = new AES(BlockCipherMode.CBC);

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

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInCfbMode() {
        AES aes = new AES(BlockCipherMode.CFB);

        String[] keys = {"00000000000000000000000000000000",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c"};
        String[] plainTexts = {"0",
            "6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"0",
            "3b3fd92eb72dad20333449f8e83cfb4a",
            "c8a64537a0b3a93fcde3cdad9f1ce58b",
            "26751f67a3cbb140b1808cf187a4f4df",
            "c04b05357c5d1c0eeac4c66f9ff7f2e6"};
        String[] initializationVectors = {"80000000000000000000000000000000",
            "000102030405060708090a0b0c0d0e0f",
            "3B3FD92EB72DAD20333449F8E83CFB4A",
            "C8A64537A0B3A93FCDE3CDAD9F1CE58B",
            "26751F67A3CBB140B1808CF187A4F4DF"};

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInCfbMode() {
        AES aes = new AES(BlockCipherMode.CFB);

        String[] keys = {"8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"cdc80d6fddf18cab34c25909c99a4174",
            "67ce7f7f81173621961a2b70171d3d7a",
            "2e1e8a1dd59b88b1c8e60fed1efac4c9",
            "c05f9f9ca9834fa042ae8fba584b09ff"};
        String[] initializationVectors = {"000102030405060708090A0B0C0D0E0F",
            "CDC80D6FDDF18CAB34C25909C99A4174",
            "67CE7F7F81173621961A2B70171D3D7A",
            "2E1E8A1DD59B88B1C8E60FED1EFAC4C9"};

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInCfbMode() {
        AES aes = new AES(BlockCipherMode.CFB);

        String[] keys = {"603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"DC7E84BFDA79164B7ECD8486985D3860",
            "39ffed143b28b1c832113c6331e5407b",
            "df10132415e54b92a13ed0a8267ae2f9",
            "75a385741ab9cef82031623d55b1e471"};
        String[] initializationVectors = {"000102030405060708090A0B0C0D0E0F",
            "DC7E84BFDA79164B7ECD8486985D3860",
            "39FFED143B28B1C832113C6331E5407B",
            "DF10132415E54B92A13ED0A8267AE2F9"};

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInOfbMode() {
        AES aes = new AES(BlockCipherMode.OFB);

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

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInOfbMode() {
        AES aes = new AES(BlockCipherMode.OFB);

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

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInOfbMode() {
        AES aes = new AES(BlockCipherMode.OFB);

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

        for (int x = 0; x < keys.length; x++) {

            byte[] key = hexaStringToByteArray(keys[x]);
            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);
            byte[] initializationVector = hexaStringToByteArray(initializationVectors[x]);

            assertArrayEquals(aes.encrypt(plainText, key, initializationVector), expectedCipherText);
        }
    }

    private byte[] hexaStringToByteArray(String str) {
        byte[] result = new byte[(str.length() / 2)];

        for (int x = 0; x < result.length; x++) {
            String hexa = str.substring(x * 2, (x * 2) + 2);
            result[x] = (byte) Long.parseLong(hexa, 16);
        }

        return result;
    }

    private byte[] reduceArray(byte[] input, int length) {
        byte[] result = new byte[length];
        System.arraycopy(input, 0, result, 0, length);
        return result;
    }

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
            assertArrayEquals(plaintext, reduceArray(aes.decrypt(ciphertext, key, initVector), textLength));
        }
    }
}
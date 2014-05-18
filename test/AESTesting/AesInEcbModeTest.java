package AESTesting;

import muni.fi.aes.BlockCipherMode;
import org.junit.Test;

/**
 * AesInEcbModeTest is class used to test encryption and decryption in ECB mode
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
public class AesInEcbModeTest {

    public AesInEcbModeTest() {
    }

    @Test
    public void testEncryptDecryptEcbMode() {
        AESTestHelper.testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.ECB);
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInEcbMode() {

        String[] keys = {"2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c",
            "2b7e151628aed2a6abf7158809cf4f3c"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"3ad77bb40d7a3660a89ecaf32466ef97",
            "f5d3d58503b9699de785895a96fdbaaf",
            "43b1cd7f598ece23881b00e3ed030688",
            "7b0c785e27e8ad3f8223207104725dd4"};
        String[] initializationVectors = {"0", "0", "0", "0"};

        AESTestHelper.testVectorsEncryption(BlockCipherMode.ECB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInEcbMode() {

        String[] keys = {"8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"bd334f1d6e45f25ff712a214571fa5cc",
            "974104846d0ad3ad7734ecb3ecee4eef",
            "ef7afd2270e2e60adce0ba2face6444e",
            "9a4b41ba738d6c72fb16691603c18e0e"};
        String[] initializationVectors = {"0", "0", "0", "0"};

        AESTestHelper.testVectorsEncryption(BlockCipherMode.ECB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInEcbMode() {

        String[] keys = {"603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4"};
        String[] plainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51",
            "30c81c46a35ce411e5fbc1191a0a52ef",
            "f69f2445df4f9b17ad2b417be66c3710"};
        String[] expectedCipherTexts = {"f3eed1bdb5d2a03c064b5a7e3db181f8",
            "591ccb10d410ed26dc5ba74a31362870",
            "b6ed21b99ca6f4f9f153e7b1beafed1d",
            "23304b7a39f9f3ff067d8d8f9e24ecc7"};
        String[] initializationVectors = {"0", "0", "0", "0"};

        AESTestHelper.testVectorsEncryption(BlockCipherMode.ECB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith128BitKeyInEcbMode() {

        String[] keys = {"00000000000000000000000000000000",
            "ffe00000000000000000000000000000"};
        String[] expectedPlainTexts = {"f34481ec3cc627bacd5dc3fb08f273e6",
            "00000000000000000000000000000000"};
        String[] cipherTexts = {"0336763e966d92595a567cc9ce537f5e",
            "956d7798fac20f82a8823f984d06f7f5"};
        String[] initializationVectors = {"0", "0"};

        AESTestHelper.testVectorsDecryption(BlockCipherMode.ECB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith192BitKeyInEcbMode() {

        String[] keys = {"ffffffffffffffffffff8000000000000000000000000000",
            "ffffffffffffffffffffc000000000000000000000000000",
            "ffffffffffffffffffffe000000000000000000000000000"};
        String[] expectedPlainTexts = {"00000000000000000000000000000000",
            "00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] cipherTexts = {"054b3bf4998aeb05afd87ec536533a36",
            "3783f7bf44c97f065258a666cae03020",
            "aad4c8a63f80954104de7b92cede1be1"};
        String[] initializationVectors = {"0", "0", "0", "0"};

        AESTestHelper.testVectorsDecryption(BlockCipherMode.ECB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith256BitKeyInEcbMode() {

        String[] keys = {"0000000000000000000000000000000000000000000000000000000000000000",
            "0000000000000000000000000000000000000000000000000000000000000000",
            "0000000000000000000000000000000000000000000000000000000000000000"};
        String[] expectedPlainTexts = {"ffffffffffffffffffffffffffffff80",
            "ffffffffffffffffffffffffffffffc0",
            "ffffffffffffffffffffffffffffffe0"};
        String[] cipherTexts = {"0493370e054b09871130fe49af730a5a",
            "9b7b940f6c509f9e44a4ee140448ee46",
            "2915be4a1ecfdcbe3e023811a12bb6c7"};
        String[] initializationVectors = {"0", "0", "0", "0"};

        AESTestHelper.testVectorsDecryption(BlockCipherMode.ECB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }
}
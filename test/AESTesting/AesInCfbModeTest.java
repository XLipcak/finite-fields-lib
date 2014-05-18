package AESTesting;

import muni.fi.aes.BlockCipherMode;
import org.junit.Test;

/**
 * AesInCfbModeTest is class used to test encryption and decryption in CFB mode
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
public class AesInCfbModeTest {

    public AesInCfbModeTest() {
    }

    @Test
    public void testEncryptDecryptCfbMode() {
        AESTestHelper.testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.CFB);
    }

    @Test
    public void testVectorsEncryptionWith128BitKeyInCfbMode() {

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

        AESTestHelper.testVectorsEncryption(BlockCipherMode.CFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith192BitKeyInCfbMode() {

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

        AESTestHelper.testVectorsEncryption(BlockCipherMode.CFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsEncryptionWith256BitKeyInCfbMode() {

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

        AESTestHelper.testVectorsEncryption(BlockCipherMode.CFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith128BitKeyInCfbMode() {

        String[] keys = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] expectedPlainTexts = {"00000000000000000000000000000000",
            "00000000000000000000000000000000"};
        String[] cipherTexts = {"0336763e966d92595a567cc9ce537f5e",
            "a9a1631bf4996954ebc093957b234589"};
        String[] initializationVectors = {"f34481ec3cc627bacd5dc3fb08f273e6",
            "9798c4640bad75c7c3227db910174e72"};

        AESTestHelper.testVectorsDecryption(BlockCipherMode.CFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith192BitKeyInCfbMode() {

        String[] keys = {"8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b",
            "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b"};
        String[] expectedPlainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51"};
        String[] cipherTexts = {"cdc80d6fddf18cab34c25909c99a4174",
            "67ce7f7f81173621961a2b70171d3d7a"};
        String[] initializationVectors = {"000102030405060708090A0B0C0D0E0F",
            "CDC80D6FDDF18CAB34C25909C99A4174"};

        AESTestHelper.testVectorsDecryption(BlockCipherMode.CFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

    @Test
    public void testVectorsDecryptionWith256BitKeyInCfbMode() {

        String[] keys = {"603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4",
            "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4"};
        String[] expectedPlainTexts = {"6bc1bee22e409f96e93d7e117393172a",
            "ae2d8a571e03ac9c9eb76fac45af8e51"};
        String[] cipherTexts = {"DC7E84BFDA79164B7ECD8486985D3860",
            "39ffed143b28b1c832113c6331e5407b"};
        String[] initializationVectors = {"000102030405060708090A0B0C0D0E0F",
            "DC7E84BFDA79164B7ECD8486985D3860"};

        AESTestHelper.testVectorsDecryption(BlockCipherMode.CFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }
}
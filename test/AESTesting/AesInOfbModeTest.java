package AESTesting;

import muni.fi.aes.BlockCipherMode;
import org.junit.Test;

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
        AESTestHelper.testEncryptDecryptPlainTextOfDifferentLengths(BlockCipherMode.OFB);
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

        AESTestHelper.testVectorsEncryption(BlockCipherMode.OFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
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

        AESTestHelper.testVectorsEncryption(BlockCipherMode.OFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
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

        AESTestHelper.testVectorsEncryption(BlockCipherMode.OFB, keys, plainTexts, expectedCipherTexts, initializationVectors);
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

        AESTestHelper.testVectorsDecryption(BlockCipherMode.OFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
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

        AESTestHelper.testVectorsDecryption(BlockCipherMode.OFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
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

        AESTestHelper.testVectorsDecryption(BlockCipherMode.OFB, keys, expectedPlainTexts, cipherTexts, initializationVectors);
    }

}
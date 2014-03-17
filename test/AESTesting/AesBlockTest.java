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
 * AesBlockTest is class used to test encryption and decryption of blocks in AES
 * class.
 *
 * Sources of all AES Test-vectors used in this class:
 * http://www.inconteam.com/software-development/41-encryption/55-aes-test-vectors
 * http://csrc.nist.gov/groups/STM/cavp/documents/aes/KAT_AES.zip
 * http://csrc.nist.gov/publications/nistpubs/800-38a/sp800-38a.pdf
 * http://www.3amsystems.com/Crypto-Toolbox#rijndael-128,ecb,Encrypt
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class AesBlockTest {

    @Test
    public void testEncryptBlock256bitKey() {

        /*
         * ENCRYPTION
         */

        //KEY 1
        String keys = "0000000000000000000000000000000000000000000000000000000000000000";
        String[] plainTexts = {"80000000000000000000000000000000",
            "c0000000000000000000000000000000",
            "e0000000000000000000000000000000",
            "f0000000000000000000000000000000",
            "f8000000000000000000000000000000",
            "fc000000000000000000000000000000",
            "fe000000000000000000000000000000",
            "ff000000000000000000000000000000",
            "ff800000000000000000000000000000",
            "ffc00000000000000000000000000000",
            "ffe00000000000000000000000000000",
            "fff00000000000000000000000000000",
            "fff80000000000000000000000000000",
            "fffc0000000000000000000000000000"};
        String[] expectedCipherTexts = {"ddc6bf790c15760d8d9aeb6f9a75fd4e",
            "0a6bdc6d4c1e6280301fd8e97ddbe601",
            "9b80eefb7ebe2d2b16247aa0efc72f5d",
            "7f2c5ece07a98d8bee13c51177395ff7",
            "7818d800dcf6f4be1e0e94f403d1e4c2",
            "e74cd1c92f0919c35a0324123d6177d3",
            "8092a4dcf2da7e77e93bdd371dfed82e",
            "49af6b372135acef10132e548f217b17",
            "8bcd40f94ebb63b9f7909676e667f1e7",
            "fe1cffb83f45dcfb38b29be438dbd3ab",
            "0dc58a8d886623705aec15cb1e70dc0e",
            "c218faa16056bd0774c3e8d79c35a5e4",
            "047bba83f7aa841731504e012208fc9e",
            "dc8f0e4915fd81ba70a331310882f6da"};
        testVectorsEncryptBlock(keys, plainTexts, expectedCipherTexts);

        //KEY 2
        keys = "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4";
        plainTexts = new String[]{"6BC1BEE22E409F96E93D7E117393172A",
            "AE2D8A571E03AC9C9EB76FAC45AF8E51",
            "30C81C46A35CE411E5FBC1191A0A52EF",
            "F69F2445DF4F9B17AD2B417BE66C3710"};
        expectedCipherTexts = new String[]{"F3EED1BDB5D2A03C064B5A7E3DB181F8",
            "591CCB10D410ED26DC5BA74A31362870",
            "B6ED21B99CA6F4F9F153E7B1BEAFED1D",
            "23304B7A39F9F3FF067D8D8F9E24ECC7"};
        testVectorsEncryptBlock(keys, plainTexts, expectedCipherTexts);

        //KEY 3
        keys = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f";
        plainTexts = new String[]{"00112233445566778899aabbccddeeff",
            "aaaa2233bbbb6677ccccaabbddddeeff",
            "00001234bbbb5678cccc7890dddd0000",
            "AE2D8A571E03AC9C9EB76FAC45AF8E51",
            "30C81C46A35CE411E5FBC1191A0A52EF",
            "F69F2445DF4F9B17AD2B417BE66C3710",
            "fffc0000000000000000000000000000",
            "ffffffffffffffffffffffffffffffff"};
        expectedCipherTexts = new String[]{"8ea2b7ca516745bfeafc49904b496089",
            "8fea42df1e7d2526039374c04181dcc7",
            "6b5c56fc511912b9328423ad8188a500",
            "542ddea4d5faad623ef884cf4e198bdc",
            "1fd0f38e35614abf31ca51243550676b",
            "27e3ee8da6fb1f4c8431129d4e896a9d",
            "ce84ab63e6a3bdb7f081365d5b705b7e",
            "e999e41d4ca770da5387117b5d8f57ee"};
        testVectorsEncryptBlock(keys, plainTexts, expectedCipherTexts);

        /*
         * Texts compared after 1000 encryption cycles in MonteCarlo test mode.
         */

        testVectorsEncryptBlockMonteCarlo("f9e8389f5b80712e3886cc1fa2d28a3b8c9cd88a2d4a54c6aa86ce0fef944be0",//key
                new String[]{"b379777f9050e2a818f2940cbbd9aba4"}, //plainText
                new String[]{"6893ebaf0a1fccc704326529fdfb60db"});//expectedText
        testVectorsEncryptBlockMonteCarlo("fac93b561a9b6a0e809d71ecdb980afab575e96989795fbcaf82d2caa9b0027f",//key
                new String[]{"307d50c18a0b6a08402ff131d72cb7ec"}, //plainText
                new String[]{"92c34165a2963e77e05e2d6fc2d931d5"});//expectedText
        testVectorsEncryptBlockMonteCarlo("83caed5a49579b3a55a71e5ece5966e5008b3e4f4d8e5cc1d92dd967a8cd51d6",//key
                new String[]{"6c73381147de97961cc26ad26602a45a"}, //plainText
                new String[]{"e76c08fd29bf015352003c636fee5ff9"});//expectedText
        testVectorsEncryptBlockMonteCarlo("72900f0ecbf90fa058805deb430815072f493488017a2c66dc74ace1d4cd2532",//key
                new String[]{"d3f309c0039b15d14eb8b739a94b94fa"}, //plainText
                new String[]{"4e441a3fb277d6fbe0ed7c6e080d9a9f"});//expectedText
        testVectorsEncryptBlockMonteCarlo("405bc450a19e6dae76847b8eac858c8bec4f9847f429e156ab7b13d3a882a7af",//key
                new String[]{"a0c514e1e6a9c659f605eff4cf4951a0"}, //plainText
                new String[]{"1dcc38307c6b3c31e25f868b279b3711"});//expectedText

    }

    @Test
    public void testDecryptBlock256bitKey() {

        /*
         * DECRYPTION
         */

        //KEY 1
        String keys = "0000000000000000000000000000000000000000000000000000000000000000";
        String[] cipherTexts = {"4b00c27e8b26da7eab9d3a88dec8b031",
            "5f397bf03084820cc8810d52e5b666e9",
            "63fafabb72c07bfbd3ddc9b1203104b8",
            "683e2140585b18452dd4ffbb93c95df9",
            "286894e48e537f8763b56707d7d155c8",
            "7818d800dcf6f4be1e0e94f403d1e4c2",
            "e74cd1c92f0919c35a0324123d6177d3",
            "8092a4dcf2da7e77e93bdd371dfed82e",
            "49af6b372135acef10132e548f217b17",
            "8bcd40f94ebb63b9f7909676e667f1e7",
            "fe1cffb83f45dcfb38b29be438dbd3ab",
            "0dc58a8d886623705aec15cb1e70dc0e"};
        String[] expectedPlainTexts = {"fffffffffffffffffffffffffffe0000",
            "ffffffffffffffffffffffffffff0000",
            "ffffffffffffffffffffffffffff8000",
            "ffffffffffffffffffffffffffffc000",
            "ffffffffffffffffffffffffffffe000",
            "f8000000000000000000000000000000",
            "fc000000000000000000000000000000",
            "fe000000000000000000000000000000",
            "ff000000000000000000000000000000",
            "ff800000000000000000000000000000",
            "ffc00000000000000000000000000000",
            "ffe00000000000000000000000000000"};
        testVectorsDecryptBlock(keys, cipherTexts, expectedPlainTexts);

        //KEY 2
        keys = "603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4";
        cipherTexts = new String[]{"F3EED1BDB5D2A03C064B5A7E3DB181F8",
            "591CCB10D410ED26DC5BA74A31362870",
            "B6ED21B99CA6F4F9F153E7B1BEAFED1D",
            "23304B7A39F9F3FF067D8D8F9E24ECC7"};
        expectedPlainTexts = new String[]{"6BC1BEE22E409F96E93D7E117393172A",
            "AE2D8A571E03AC9C9EB76FAC45AF8E51",
            "30C81C46A35CE411E5FBC1191A0A52EF",
            "F69F2445DF4F9B17AD2B417BE66C3710"};
        testVectorsDecryptBlock(keys, cipherTexts, expectedPlainTexts);

        //KEY 3
        keys = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f";
        cipherTexts = new String[]{"8ea2b7ca516745bfeafc49904b496089",
            "8fea42df1e7d2526039374c04181dcc7",
            "6b5c56fc511912b9328423ad8188a500",
            "542ddea4d5faad623ef884cf4e198bdc",
            "1fd0f38e35614abf31ca51243550676b",
            "27e3ee8da6fb1f4c8431129d4e896a9d",
            "ce84ab63e6a3bdb7f081365d5b705b7e",
            "e999e41d4ca770da5387117b5d8f57ee"};
        expectedPlainTexts = new String[]{"00112233445566778899aabbccddeeff",
            "aaaa2233bbbb6677ccccaabbddddeeff",
            "00001234bbbb5678cccc7890dddd0000",
            "AE2D8A571E03AC9C9EB76FAC45AF8E51",
            "30C81C46A35CE411E5FBC1191A0A52EF",
            "F69F2445DF4F9B17AD2B417BE66C3710",
            "fffc0000000000000000000000000000",
            "ffffffffffffffffffffffffffffffff"};
        testVectorsDecryptBlock(keys, cipherTexts, expectedPlainTexts);

        /*
         * Texts compared after 1000 decryption cycles in MonteCarlo test mode.
         */

        testVectorsDecryptBlockMonteCarlo("2b09ba39b834062b9e93f48373b8dd018dedf1e5ba1b8af831ebbacbc92a2643",//key
                new String[]{"89649bd0115f30bd878567610223a59d"}, //plainText
                new String[]{"1f9b9b213f1884fa98b62dd6639fd33b"});//expectedText
        testVectorsDecryptBlockMonteCarlo("f53e21e46a0ec97c980d49d6f4b81ec423d53c8ff966e0c22aef61431c5e713e",//key
                new String[]{"eab144d6f80ccfd2fae95d16784718ac"}, //plainText
                new String[]{"e04b91c3f084d733d3d0c1c7c152695a"});//expectedText
        testVectorsDecryptBlockMonteCarlo("81a73bfcacc994f47aeec0d655e0485055bdaf2bef16a9df922ee0cc61af1477",//key
                new String[]{"c715bd2f23b98c66bf56e5680c6037f7"}, //plainText
                new String[]{"e00984f9d93551e95ae85cfe4a829b82"});//expectedText
        testVectorsDecryptBlockMonteCarlo("4c7c62be3f5a9d09934940284565e9c92e55b903a8be22e39f909f31c0e409f3",//key
                new String[]{"9a54dfd56bff41ea17bcf08d3761e95b"}, //plainText
                new String[]{"76e074c587f112f3ffd17b390fa8cffc"});//expectedText
        testVectorsDecryptBlockMonteCarlo("20c7e6e45c08ba0756c379b6cd4d6886022c94a6878207b188e3428b18c057e8",//key
                new String[]{"942c057914f3b88fa0a40bb41d02b051"}, //plainText
                new String[]{"6f4e5c40e4135abfdf294b4d8c9304b3"});//expectedText

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
            assertArrayEquals("Block of plaintext after encryption and decryption must be the same.", plaintext, aes.decryptBlock(ciphertext, key));
        }
    }

    //use test vectors to encrypt data, check result
    private void testVectorsEncryptBlock(String inputKey, String[] plainTexts,
            String[] expectedCipherTexts) {

        AES aes = new AES();

        byte[] key = hexaStringToByteArray(inputKey);
        for (int x = 0; x < plainTexts.length; x++) {

            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);

            assertArrayEquals("Encrypted plaintext differs from expected result vector.",
                    expectedCipherText, aes.encryptBlock(plainText, key));
        }
    }

    //use test vectors to decrypt data, check result
    private void testVectorsDecryptBlock(String inputKey, String[] cipherTexts,
            String[] expectedPlainTexts) {

        AES aes = new AES();

        byte[] key = hexaStringToByteArray(inputKey);
        for (int x = 0; x < cipherTexts.length; x++) {

            byte[] plainText = hexaStringToByteArray(expectedPlainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(cipherTexts[x]);

            assertArrayEquals("Decrypted plaintext differs from expected result vector.",
                    plainText, aes.decryptBlock(expectedCipherText, key));
        }
    }

    //use test vectors to encrypt data, check result
    private void testVectorsEncryptBlockMonteCarlo(String inputKey, String[] plainTexts,
            String[] expectedCipherTexts) {

        AES aes = new AES();

        byte[] key = hexaStringToByteArray(inputKey);
        for (int x = 0; x < plainTexts.length; x++) {

            byte[] plainText = hexaStringToByteArray(plainTexts[x]);
            byte[] expectedCipherText = hexaStringToByteArray(expectedCipherTexts[x]);


            for (int monteCarlo = 0; monteCarlo < 1000; monteCarlo++) {
                plainText = aes.encryptBlock(plainText, key);
            }
            assertArrayEquals("Encrypted plaintext differs from expected result vector.",
                    expectedCipherText, plainText);
        }
    }

    //use test vectors to encrypt data, check result
    private void testVectorsDecryptBlockMonteCarlo(String inputKey, String[] cipherTexts,
            String[] expectedPlainTexts) {

        AES aes = new AES();

        byte[] key = hexaStringToByteArray(inputKey);
        for (int x = 0; x < cipherTexts.length; x++) {

            byte[] cipherText = hexaStringToByteArray(cipherTexts[x]);
            byte[] expectedPlainText = hexaStringToByteArray(expectedPlainTexts[x]);


            for (int monteCarlo = 0; monteCarlo < 1000; monteCarlo++) {
                cipherText = aes.decryptBlock(cipherText, key);
            }
            assertArrayEquals("Encrypted plaintext differs from expected result vector.",
                    expectedPlainText, cipherText);
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
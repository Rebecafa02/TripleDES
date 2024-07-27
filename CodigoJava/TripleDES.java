import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TripleDES {

    private static Random random = new Random();

    private static List<int[]> splitKey(String keyHex) {
        int[] key1 = getBitsFromHexStr(keyHex.substring(0, 16));
        int[] key2 = getBitsFromHexStr(keyHex.substring(16, 32));
        int[] key3 = getBitsFromHexStr(keyHex.substring(32, 48));

        List<int[]> keys = new ArrayList<>();
        keys.add(key1);
        keys.add(key2);
        keys.add(key3);

        return keys;
    }

    private static int[] getBitsFromHexStr(String hexStr) {
        int[] bits = new int[64];
        String inputBinary = ConversorHexa.hexToBinary(hexStr);
        for (int i = 0; i < inputBinary.length(); i++) {
            bits[i] = Character.getNumericValue(inputBinary.charAt(i));
        }
        return bits;
    }

    private static List<int[]> splitToBitBlocks(String strHex) {
        List<int[]> blocks = new ArrayList<>();
        String strBinary = ConversorHexa.hexToBinary(strHex);
        String[] binaryBlocks = strBinary.split("(?<=\\G.{64})");

        for (String block : binaryBlocks) {
            int[] bits = new int[64];
            for (int i = 0; i < block.length(); i++) {
                bits[i] = Character.getNumericValue(block.charAt(i));
            }
            blocks.add(bits);
        }

        return blocks;
    }

    public static List<int[]> splitToBitBlocksUsingTBC(String strHex) {
        List<int[]> blocks = new ArrayList<>();
        String strBinary = ConversorHexa.hexToBinary(strHex);
        String[] binaryBlocks = strBinary.split("(?<=\\G.{64})");
        int paddingValue = strBinary.charAt(strBinary.length() - 1) == '0' ? 1 : 0;

        for (String block : binaryBlocks) {
            if (block.length() < 64) {
                block = tbc(block);
            }
            int[] bits = new int[64];
            for (int i = 0; i < block.length(); i++) {
                bits[i] = Character.getNumericValue(block.charAt(i));
            }
            blocks.add(bits);
        }

        // Add last block
        blocks.add(new int[64]); // placeholder for padding block
        blocks.get(blocks.size() - 1)[0] = paddingValue;

        return blocks;
    }

    private static String tbc(String strBinary) {
        char paddingValue = strBinary.charAt(strBinary.length() - 1) == '0' ? '1' : '0';
        while (strBinary.length() < 64) {
            strBinary += paddingValue;
        }
        return strBinary;
    }

    public static String encrypt(String keyHex, String strHexToEncrypt) {
        //List<int[]> subKeys = splitKey(keyHex);
        //List<int[]> bitBlocks = splitToBitBlocksUsingTBC(strHexToEncrypt);
        //List<int[]> encryptedBits = new ArrayList<>();

        //for (int[] block : bitBlocks) {
          //  int[] cipher1 = DES.Encrypt(subKeys.get(0), block);
          //  int[] cipher2 = DES.decrypt(subKeys.get(1), cipher1);
          //  int[] cipher3 = DES.Encrypt(subKeys.get(2), cipher2);
           // encryptedBits.add(cipher3);
        //}
        int[] inputBits = GetBitsFromHexStr(strHexToEncrypt);

        return ConversorHexa.bitsToHex(encryptedBits);
    }

    public static String decrypt(String keyHex, String cipher) {
        List<int[]> subKeys = splitKey(keyHex);
        List<int[]> bitBlocks = splitToBitBlocks(cipher);
        List<int[]> decryptedBits = new ArrayList<>();

        for (int[] block : bitBlocks) {
            int[] decrypted3 = DES.decrypt(subKeys.get(2), block);
            int[] decrypted2 = DES.Encrypt(subKeys.get(1), decrypted3);
            int[] decrypted1 = DES.decrypt(subKeys.get(0), decrypted2);
            decryptedBits.add(decrypted1);
        }

        // Remove padding
        int paddingValue = decryptedBits.get(decryptedBits.size() - 1)[0];
        decryptedBits.remove(decryptedBits.size() - 1); // Remove last additional block containing only padding value
        decryptedBits.get(decryptedBits.size() - 1)[63] &= ~(paddingValue & 1); // Trim padding

        String decryptedHex = ConversorHexa.bitsToHex(decryptedBits);
        return ConversorHexa.hexStrToStr(decryptedHex);
    }

    public static String getRandomKeyHex() {
        StringBuilder keyHex = new StringBuilder();
        for (int i = 0; i < 48; i++) {
            keyHex.append(Integer.toHexString(random.nextInt(16)));
        }
        return keyHex.toString().toUpperCase();
    }
}

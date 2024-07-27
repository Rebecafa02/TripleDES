import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;

public class ConversorHexa {

    public static String hexToBinary(String hexString) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : hexString.toCharArray()) {
            binaryString.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt(String.valueOf(c), 16))).replace(' ', '0'));
        }
        return binaryString.toString();
    }

    public static String intToHex(int value) {
        return Integer.toHexString(value).toUpperCase();
    }

    public static String intToBinary(int value) {
        return Integer.toBinaryString(value);
    }

    public static int hexToInt(String hexString) {
        return Integer.parseInt(hexString, 16);
    }

    public static String binaryToHex(String binary) {
        int mod4Len = binary.length() % 4;
        if (mod4Len != 0) {
            binary = String.format("%" + (binary.length() + (4 - mod4Len)) + "s", binary).replace(' ', '0');
        }

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 4) {
            int decimal = Integer.parseInt(binary.substring(i, i + 4), 2);
            hexString.append(Integer.toHexString(decimal).toUpperCase());
        }
        return hexString.toString();
    }

    public static String strToHex(String str) {
        StringBuilder hexString = new StringBuilder();
        byte[] bytes = str.getBytes(StandardCharsets.UTF_16);
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    public static String hexStrToStr(String hexStr) {
        int len = hexStr.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexStr.charAt(i), 16) << 4)
                    + Character.digit(hexStr.charAt(i + 1), 16));
        }
        return new String(bytes, StandardCharsets.UTF_16);
    }

    public static String bitsToHex(int[] bits) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < bits.length; i += 4) {
            StringBuilder binarySegment = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                binarySegment.append(bits[i + j]);
            }
            int decimal = Integer.parseInt(binarySegment.toString(), 2);
            hexString.append(Integer.toHexString(decimal).toUpperCase());
        }
        return hexString.toString();
    }

    public static String listBitsToHex(List<int[]> bitList) {
        StringJoiner hexString = new StringJoiner("");
        for (int[] bits : bitList) {
            hexString.add(bitsToHex(bits));
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        // Exemplo de uso:

        // Hex to binary
        System.out.println(hexToBinary("1A3F"));

        // Int to hex
        System.out.println(intToHex(1234));

        // Int to binary
        System.out.println(intToBinary(1234));

        // Hex to int
        System.out.println(hexToInt("4D2"));

        // Binary to hex
        System.out.println(binaryToHex("110100010"));

        // String to hex
        System.out.println(strToHex("Hello"));

        // Hex string to string
        System.out.println(hexStrToStr("00480065006C006C006F"));

        // Bits to hex
        int[] bits = {1, 1, 0, 1, 0, 0, 0, 1};
        System.out.println(bitsToHex(bits));

        // List of bits to hex
        List<int[]> bitList = List.of(
            new int[]{1, 1, 0, 1, 0, 0, 0, 1},
            new int[]{1, 0, 0, 1, 0, 0, 1, 0}
        );
        System.out.println(listBitsToHex(bitList));
    }
}

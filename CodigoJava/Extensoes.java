import java.util.ArrayList;
import java.util.List;

public class Extensoes {
    public static Iterable<String> splitInParts(String s, int partLength) {
        if (s == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        if (partLength <= 0) {
            throw new IllegalArgumentException("Part length has to be positive.");
        }

        List<String> parts = new ArrayList<>();
        for (int i = 0; i < s.length(); i += partLength) {
            parts.add(s.substring(i, Math.min(i + partLength, s.length())));
        }

        return parts;
    }

    public static int[] trimEnd(int[] array, int value) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) == value) {
                list.remove(i);
            } else {
                break;
            }
        }

        int[] trimmedArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            trimmedArray[i] = list.get(i);
        }

        return trimmedArray;
    }
}

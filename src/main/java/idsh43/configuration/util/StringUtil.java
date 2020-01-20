package idsh43.configuration.util;

public class StringUtil {

    public static String concatenate(String[] strings, String separator, int size) {
        if (strings == null)
            throw new IllegalArgumentException("null strings");
        if (size < 0 || size > strings.length)
            throw new IllegalArgumentException("size should be in {0.." + strings.length + "}, " + size + " given");
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < size; i++) {
            if (!first) sb.append(separator);
            else first = false;
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    public static String concatenate(String[] strings, String separator) {
        if (strings == null)
            throw new IllegalArgumentException("null strings");
        return concatenate(strings, separator, strings.length);
    }

    public static String repeat(char target, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++)
            result.append(target);
        return result.toString();
    }


}

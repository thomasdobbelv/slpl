package slpl.util;

public class StringConcatenator {

    public static String concatenate(Object ... objects) {
        return concatenate(" ", objects);
    }

    public static String concatenate(String separator, Object ... objects) {
        StringBuilder sb = new StringBuilder();
        for(Object object : objects) {
            sb.append(object + separator);
        }
        return sb.substring(0, sb.length() - separator.length());
    }

}

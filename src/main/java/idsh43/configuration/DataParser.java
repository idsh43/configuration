package idsh43.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DataParser {

    boolean parseBoolean(String value) throws ConfigurationException {
        if ("true".equals(value)) return true;
        if ("false".equals(value)) return false;
        throw new ConfigurationException("\"" + value + "\" is not a boolean");
    }

    int parseInteger(String value) throws ConfigurationException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("\"" + value + "\" is not an integer");
        }
    }

    float parseFloat(String value) throws ConfigurationException {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("\"" + value + "\" is not a float");
        }
    }

    List<String> parseList(String values) throws ConfigurationException {
        if (values.length() < 2 || values.charAt(0) != '[' || values.charAt(values.length() - 1) != ']')
            throw new ConfigurationException("\"" + values + "\" is not a list");
        values = values.substring(1, values.length() - 1);
        List<String> result = new ArrayList<>();
        for (String value: values.split(" "))
            result.add(value);
        return result;
    }

    <T extends Enum> List<T> parseEnumList(Class<T> enumClass, List<String> values) throws ConfigurationException {
        List<T> result = new ArrayList<>();
        for (String value: values) {
            try {
                result.add((T) Enum.valueOf(enumClass, value.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ConfigurationException("unknown value \"" + value + "\" of enum " + enumClass.getName());
            }
        }
        return result;
    }

    <T extends Enum> Set<T> parseEnumSet(Class<T> enumClass, List<String> values) throws ConfigurationException {
        Set<T> result = new HashSet<>();
        for (T value: parseEnumList(enumClass, values))
            result.add(value);
        return result;
    }

}

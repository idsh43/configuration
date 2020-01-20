package idsh43.configuration;

public class ConfigurationException extends Exception {

    ConfigurationException(String message) {
        super(message);
    }

    ConfigurationException(int lineIndex, String message) {
        this("line " + lineIndex + ": " + message);
    }

}

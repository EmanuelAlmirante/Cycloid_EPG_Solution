package epg.exception;

import java.util.Arrays;
import java.util.Objects;

public class ResourceNotFoundException extends RuntimeException {

    private String messageKey;
    private String[] arguments;

    private ResourceNotFoundException() {}

    public ResourceNotFoundException(String messageKey, String... arguments) {
        this.messageKey = messageKey;
        this.arguments = arguments == null ? new String[0] : arguments;
    }

    public static ResourceNotFoundException forMessageKey(String messageKey) {
        return new ResourceNotFoundException(messageKey);
    }

    public static ResourceNotFoundException forMessageKey(String messageKey, String... arguments) {
        return new ResourceNotFoundException(messageKey, arguments);
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getArguments() {
        return arguments;
    }

    public boolean hasMessageKey(String messageKey) {
        return Objects.equals(this.messageKey, messageKey);
    }

    @Override
    public String toString() {
        return super.toString() + ", arguments=" + Arrays.toString(arguments);
    }
}

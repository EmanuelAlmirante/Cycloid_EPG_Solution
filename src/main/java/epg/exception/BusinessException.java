package epg.exception;

import java.util.Arrays;
import java.util.Objects;

public class BusinessException extends RuntimeException {

    private String messageKey;
    private String[] arguments;

    private BusinessException() {}

    public BusinessException(String messageKey, Throwable throwable, String... arguments) {
        super(messageKey, throwable);
        this.messageKey = messageKey;
        this.arguments = arguments == null ? new String[0] : arguments;
    }

    public BusinessException(String messageKey, String... arguments) {
        super(messageKey);
        this.messageKey = messageKey;
        this.arguments = arguments == null ? new String[0] : arguments;
    }

    public static BusinessException forMessageKey(String messageKey) {
        return new BusinessException(messageKey);
    }

    public static BusinessException forMessageKey(String messageKey, Throwable throwable) {
        return new BusinessException(messageKey, throwable);
    }

    public static BusinessException forMessageKey(String messageKey, String... arguments) {
        return new BusinessException(messageKey, arguments);
    }

    public static BusinessException forMessageKey(
            String messageKey, Throwable throwable, String... arguments) {
        return new BusinessException(messageKey, throwable, arguments);
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

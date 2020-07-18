package epg.error;

import epg.exception.ResourceNotFoundException;

public class ResourceNotFoundError extends RestError {

    private String messageKey;
    private String[] arguments;

    private ResourceNotFoundError() {
    }

    public ResourceNotFoundError(String messageKey, String... arguments) {
        this.messageKey = messageKey;
        this.arguments = arguments;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getArguments() {
        return arguments;
    }

    @Override
    public ResourceNotFoundException getException() {
        return new ResourceNotFoundException(messageKey, arguments);
    }
}

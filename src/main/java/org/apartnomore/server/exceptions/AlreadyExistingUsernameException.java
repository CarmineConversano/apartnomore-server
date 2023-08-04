package org.apartnomore.server.exceptions;

public class AlreadyExistingUsernameException extends Exception {
    public AlreadyExistingUsernameException(String message) {
        super(message);
    }
}

package org.apartnomore.server.exceptions;

public class AlreadyExistingEmailException extends Exception {
    public AlreadyExistingEmailException(String message) {
        super(message);
    }
}

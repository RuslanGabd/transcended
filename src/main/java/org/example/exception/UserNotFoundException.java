package org.example.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}
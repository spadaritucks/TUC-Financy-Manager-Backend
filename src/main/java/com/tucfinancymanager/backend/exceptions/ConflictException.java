package com.tucfinancymanager.backend.exceptions;



public class ConflictException extends RuntimeException {

    public ConflictException (String message) {
        super(message);
    }
}

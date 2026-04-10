package com.stockms.domain.exception;

/** Thrown when a business rule conflict is detected (e.g. duplicate EAN). Maps to HTTP 409. */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}

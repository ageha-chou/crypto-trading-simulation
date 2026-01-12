package com.diepnn.cryptotradingsimulation.exception;

public class PriceStaleException extends RuntimeException {
    public PriceStaleException(String message) {
        super(message);
    }
}

package com.olyakorolko.exception;

public class NotInStockException extends RuntimeException {
    public NotInStockException(String message) {
        super(message);
    }

    public NotInStockException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.pavilion.libraryui.exception;

public class BookServiceException extends RuntimeException {
    public BookServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

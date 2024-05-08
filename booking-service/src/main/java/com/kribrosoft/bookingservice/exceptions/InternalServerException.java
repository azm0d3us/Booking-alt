package com.kribrosoft.bookingservice.exceptions;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message) {
        super(message);
    }
}

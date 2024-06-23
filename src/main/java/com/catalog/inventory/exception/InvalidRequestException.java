package com.catalog.inventory.exception;

public class InvalidRequestException extends RuntimeException{

    public InvalidRequestException(String error) {
        super(error);
    }
}

package com.catalog.inventory.exception;

public class EntryNotFoundException extends RuntimeException{

    public EntryNotFoundException(String error) {
        super(error);
    }
}

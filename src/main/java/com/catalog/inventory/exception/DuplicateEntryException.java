package com.catalog.inventory.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String error) {
        super(error);
    }
}

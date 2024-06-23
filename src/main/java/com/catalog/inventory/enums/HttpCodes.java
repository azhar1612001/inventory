package com.catalog.inventory.enums;

import lombok.Getter;

@Getter
public enum HttpCodes {
    BAD_REQUEST(400),
    CONFLICT(409),
    OK(200),
    CREATED(201),
    NOT_FOUND(404),
    NO_CONTENT(204);

    private final int code;

    HttpCodes(int code) {
        this.code = code;
    }
}

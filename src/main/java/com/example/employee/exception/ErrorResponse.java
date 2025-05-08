package com.example.employee.exception;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    ENTITY_NOT_FOUND(1001, "Entity not found"),
    ENTITY_ALREADY_EXISTS(1002, "Entity already exists"),
    INVALID_REQUEST_PARAMETERS(1018, "Invalid request parameters"),
    NOTIFICATION_NOT_EXISTED(1020, "notification not exist"),


    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),

    ;
    ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;

}

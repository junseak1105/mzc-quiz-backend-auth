package com.mzc.Auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATED_HOST_EMAIL(HttpStatus.CONFLICT, "Duplicated host email"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    //INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    HOST_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "host email not founded"),

    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "host email has invalid permission"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "host email password"),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
    NOTIFICATION_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connect to notification occurs error")
    ;

    private final HttpStatus status;
    private final String message;

}

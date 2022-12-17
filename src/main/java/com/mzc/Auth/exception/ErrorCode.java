package com.mzc.Auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token");

    private final HttpStatus status;
    private final String message;

}

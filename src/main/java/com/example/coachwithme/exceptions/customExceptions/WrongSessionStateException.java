package com.example.coachwithme.exceptions.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongSessionStateException extends ResponseStatusException {
    public WrongSessionStateException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

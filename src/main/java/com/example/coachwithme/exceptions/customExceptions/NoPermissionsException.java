package com.example.coachwithme.exceptions.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoPermissionsException extends ResponseStatusException {
    public NoPermissionsException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

package com.example.coachwithme.exceptions.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserIsNotInCoachSessionException extends ResponseStatusException {
    public UserIsNotInCoachSessionException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

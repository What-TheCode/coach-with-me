package com.example.coachwithme.exceptions.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserIsNotACoachException extends ResponseStatusException {
    public UserIsNotACoachException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

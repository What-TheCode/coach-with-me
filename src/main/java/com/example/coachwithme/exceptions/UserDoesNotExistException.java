package com.example.coachwithme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserDoesNotExistException extends ResponseStatusException {
    public UserDoesNotExistException(String message){super(HttpStatus.BAD_REQUEST, message);}
}

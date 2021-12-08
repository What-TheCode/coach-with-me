package com.example.coachwithme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotUniqueEmailException extends ResponseStatusException {
    public NotUniqueEmailException(String message){super(HttpStatus.BAD_REQUEST, message);}
}

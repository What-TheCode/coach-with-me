package com.example.coachwithme.exceptions.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TopicAlreadyExistsException extends ResponseStatusException {
    public TopicAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

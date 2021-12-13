package com.example.coachwithme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CoachCanNotTeachTopicException extends ResponseStatusException {
    public CoachCanNotTeachTopicException(String message){super(HttpStatus.BAD_REQUEST, message);}
}

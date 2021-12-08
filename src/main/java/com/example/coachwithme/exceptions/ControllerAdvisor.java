package com.example.coachwithme.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ControllerAdvisor {
    private final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler
    public void handleInvalidEmailException(NotUniqueEmailException exception, HttpServletResponse response) throws IOException {
        logger.error("Not Unique Email given: " + exception.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}

package com.noideaindustry.feather_project.feather_logger.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionUtils {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noHandlerFound(final NoHandlerFoundException ex) {
        return ResponseUtils.badRequest("Requested path does not exist.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String httpMessageNotReadable(final HttpMessageNotReadableException ex) {
        return ResponseUtils.badRequest("Request body was unreadable or missing.");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Exception ex) {
        return ResponseUtils.internalServerError("An unexpected error occurred.");
    }
}
package org.example.statify.controller;

import org.example.statify.api.error.ApiError;
import org.example.statify.entity.exceptions.ClientErrorException;
import org.example.statify.entity.exceptions.ForbiddenException;
import org.example.statify.entity.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleValidationException(ValidationException ex){
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler({ChangeSetPersister.NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleNotFoundException(ChangeSetPersister.NotFoundException ex){
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiError handleDataIntegrityForbiddenException(ForbiddenException ex){
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler({ClientErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleClientErrorException(ClientErrorException ex) {
        return new ApiError(ex.getMessage());
    }
}

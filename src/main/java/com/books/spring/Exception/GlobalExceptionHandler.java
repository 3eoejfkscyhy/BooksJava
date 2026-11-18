package com.books.spring.Exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice 
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler (ConflictException.class)
    public ProblemDetail handleConflict(ConflictException ex)
    {
    return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,ex.getMessage());
    }
    @ExceptionHandler (NotFoundException.class)
    public ProblemDetail handleConflict(NotFoundException ex)
    {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
    }
    @ExceptionHandler (BadRequestException.class)
    public ProblemDetail handleConflict(BadRequestException ex)
    {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }
    @Override 
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers,HttpStatusCode status, WebRequest request)
        {
            Map<String,String> errors = new LinkedHashMap<>();
            for(FieldError error:ex.getBindingResult().getFieldErrors())
            {
             errors.merge(error.getField(),error.getDefaultMessage(),(first,second)->first+"; "+second);
            }
            ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, "nah");
            problem.setProperty("errors", errors);
            return  handleExceptionInternal(ex, problem, headers, status, request);
        }
}

package com.mcformation.exception;

import com.mcformation.model.api.MessageApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.validation.ObjectError;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        MessageApi messageApi = new MessageApi();
        StringBuilder message = new StringBuilder("Erreur de validation :");
        for (ObjectError error: ex.getAllErrors()) {
            message.append("\n- ");
            message.append(((FieldError) error).getField());
            message.append(" : ");
            message.append(error.getDefaultMessage());
        }
        messageApi.setCode(HttpStatus.BAD_REQUEST.value());
        messageApi.setMessage(message.toString());
        return new ResponseEntity<>(messageApi, HttpStatus.BAD_REQUEST);
    }

}

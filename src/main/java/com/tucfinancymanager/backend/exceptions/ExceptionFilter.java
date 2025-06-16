package com.tucfinancymanager.backend.exceptions;

import com.tucfinancymanager.backend.DTOs.errors.ErrorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ExceptionFilter  {

    @Autowired
    private MessageSource messageSource;

    private ResponseEntity<ErrorResponseDTO> errorResponseHandler (String message, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponseDTO(message), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDTO>> argumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ErrorResponseDTO> errorResponse = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErrorResponseDTO error = new ErrorResponseDTO(message);
            errorResponse.add(error);
        });
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> conflictExceptionHandler(ConflictException e) {
        return errorResponseHandler(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundExceptionHandler(NotFoundException e){
        return errorResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponseDTO> authorizationExceptionHandler(AuthorizationException e){
        return errorResponseHandler(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO>  genericExceptionHandler(Exception e){
        return errorResponseHandler("Erro Desconhecido", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

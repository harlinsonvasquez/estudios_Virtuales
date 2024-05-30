package com.estudios.virtuales.estudios.virtuales.api.error_handler;

import com.estudios.virtuales.estudios.virtuales.api.dto.errors.BaseErrorResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.errors.ErrorsResp;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {//este es un observador que se va disparar cuando ocurra un error y dependiendo el error ejecuta un metodo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResp handleBadRequest(MethodArgumentNotValidException exception){
        List<Map<String,String>> errors = new ArrayList<>();

        /*
         * getBindingResult obtiene los resultados con el fiel y el error
         * getFieldErrors obtiene la lista de los nombres del campo del error
         */
        exception.getBindingResult().getFieldErrors().forEach(e -> {
            Map<String,String> error = new HashMap<>();
            error.put("error", e.getDefaultMessage()); //agregar al map el error
            error.put("field", e.getField()); //agregar al map en donde ocurrió el error
            errors.add(error);
        });


        return ErrorsResp.builder()
                .code(HttpStatus.BAD_REQUEST.value()) //400
                .status(HttpStatus.BAD_REQUEST.name()) //BAD_REQUEST
                .errors(errors) // [ { "field": "mal", "error": "mal"} ]
                .build();
    }


    @ExceptionHandler(BadRequestException.class)
    public BaseErrorResp handleError(BadRequestException exception){
        List<Map<String,String>> errors = new ArrayList<>();

        Map<String,String> error = new HashMap<>();

        error.put("id", exception.getMessage());

        errors.add(error);


        return ErrorsResp.builder()
                .code(HttpStatus.BAD_REQUEST.value()) //400
                .status(HttpStatus.BAD_REQUEST.name()) //BAD_REQUEST
                .errors(errors) // [ { "field": "mal", "error": "mal"} ]
                .build();

    }
}

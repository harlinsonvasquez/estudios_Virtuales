package com.estudios.virtuales.estudios.virtuales.utils.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}

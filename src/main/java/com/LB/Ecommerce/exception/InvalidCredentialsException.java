package com.LB.Ecommerce.exception;

public class InvalidCredentialsException extends  RuntimeException{

    public InvalidCredentialsException(String message){
        super(message);
    }
}

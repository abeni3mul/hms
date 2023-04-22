package com.hms.exceptions;

public class UnexpectedErrorException extends Exception{
    public UnexpectedErrorException(){
        super("An unexpected error occurred.");
    }

    public UnexpectedErrorException(String msg){
        super(msg);
    }
}

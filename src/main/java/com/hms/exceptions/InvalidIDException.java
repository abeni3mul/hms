package com.hms.exceptions;

public class InvalidIDException extends Exception{
    public InvalidIDException(){
        super("The requested record does not exist!");
    }

    public InvalidIDException(String msg){
        super(msg);
    }
}

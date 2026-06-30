package com.nak.demo.exception.model;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message){
        super(message);
    }
}

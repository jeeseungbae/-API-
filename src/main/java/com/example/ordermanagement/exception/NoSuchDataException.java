package com.example.ordermanagement.exception;

import java.util.NoSuchElementException;


public class NoSuchDataException extends NoSuchElementException {

    public NoSuchDataException(long id){
        super("Could not find Data" + id);
    }
}

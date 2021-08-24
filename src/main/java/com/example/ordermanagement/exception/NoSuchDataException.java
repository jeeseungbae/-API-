package com.example.ordermanagement.exception;

import lombok.Getter;

import java.util.NoSuchElementException;

public class NoSuchDataException extends NoSuchElementException {

    public NoSuchDataException(long id){
        super("Could not find Data" + id);
    }
}

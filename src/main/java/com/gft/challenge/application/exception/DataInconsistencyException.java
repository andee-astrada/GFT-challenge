package com.gft.challenge.exception;

public class DataInconsistencyException extends Exception {

    public DataInconsistencyException() {
        super("Data consistency error, contact support");
    }
}
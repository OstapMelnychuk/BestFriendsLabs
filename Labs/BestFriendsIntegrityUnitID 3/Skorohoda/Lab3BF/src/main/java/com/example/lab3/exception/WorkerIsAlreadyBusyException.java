package com.example.lab3.exception;

public class WorkerIsAlreadyBusyException extends RuntimeException {
    public WorkerIsAlreadyBusyException(String message) {
        super(message);
    }
}

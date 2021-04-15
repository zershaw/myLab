package com.cbsys.saleexplore.exception;

public class FileStorageEn extends RuntimeException {
    public FileStorageEn(String message) {
        super(message);
    }

    public FileStorageEn(String message, Throwable cause) {
        super(message, cause);
    }
}

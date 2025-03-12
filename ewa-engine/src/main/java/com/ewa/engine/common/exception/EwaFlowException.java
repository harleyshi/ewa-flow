package com.ewa.engine.common.exception;

public class EwaFlowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EwaFlowException(String message) {
        super(message);
    }

    public EwaFlowException(String message, Throwable cause) {
        super(message, cause);
    }

    public EwaFlowException(Throwable cause) {
        super(cause);
    }
}

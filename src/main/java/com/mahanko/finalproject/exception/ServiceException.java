package com.mahanko.finalproject.exception;

/**
 * The type ServiceException exception.
 */
public class ServiceException extends Exception {
    /**
     * Instantiates a new ServiceException exception.
     */
    public ServiceException() {
    }

    /**
     * Instantiates a new ServiceException exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new ServiceException exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new ServiceException exception.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}

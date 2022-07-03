package com.mahanko.finalproject.exception;

/**
 * The type DaoException exception.
 */
public class DaoException extends Exception {
    /**
     * Instantiates a new DaoException exception.
     */
    public DaoException() {
    }

    /**
     * Instantiates a new DaoException exception.
     *
     * @param message the message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Instantiates a new DaoException exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new DaoException exception.
     *
     * @param cause the cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}

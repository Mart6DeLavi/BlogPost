package org.martix.blogpost.user.logging.exceptions;

import lombok.Data;

import java.util.Date;

/**
 * The AppError class is a data class that represents an application error.
 * It contains information about the error such as status, message, and the time it occurred.
 */

@Data
public class AppError {

    /**
     * The HTTP status code of the error.
     */
    private int status;

    /**
     * The detailed error message.
     */
    private String message;

    /**
     * The timestamp when the error occurred.
     */
    private Date timestamp;

    /**
     * Constructs a new AppError object with the specified status and message.
     * The timestamp is automatically set to the current date and time.
     *
     * @param status  the HTTP status code of the error
     * @param message the detailed error message
     */
    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}

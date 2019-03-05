package com.bookstore;

/**
 * Represents duplicate entry in a collection etc.
 *
 * @author Christoffer A Træen
 * @version  1
 */
public class DuplicateEntryException extends RuntimeException {

    /**
     * Create exception and bind message and call super to bind
     * message.
     * @param message The message of the exception
     */
    public DuplicateEntryException(String message) {
        super(message);
    }

}

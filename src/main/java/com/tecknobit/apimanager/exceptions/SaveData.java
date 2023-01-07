package com.tecknobit.apimanager.exceptions;

/**
 * The {@code SaveData} is an {@link Exception} thrown when the workflow need to be stopped to save any data.
 * This, usually, is at the first run of that workflow
 *
 * @author Tecknobit N7ghtm4r3
 **/
public class SaveData extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @apiNote the {@code "message"} by default starts with: {@code "Note: is not an error, but is an alert!\nPlease you should safely save: "}
     */
    public <T> SaveData(T message) {
        super("Note: is not an error, but is an alert!\nPlease you should safely save: " + message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @apiNote the {@code "message"} by default starts with: {@code "Note: is not an error, but is an alert!\nPlease you should safely save: "}
     * @since 1.4
     */
    public <T> SaveData(T message, Throwable cause) {
        super("Note: is not an error, but is an alert!\nPlease you should safely save: " + message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (A {@code null} value is permitted,
     *                           and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression  whether suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether the stack trace should
     *                           be writable
     * @apiNote the {@code "message"} by default starts with: {@code "Note: is not an error, but is an alert!\nPlease you should safely save: "}
     * @since 1.7
     */
    public <T> SaveData(T message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super("Note: is not an error, but is an alert!\nPlease you should safely save: " + message, cause, enableSuppression,
                writableStackTrace);
    }

}

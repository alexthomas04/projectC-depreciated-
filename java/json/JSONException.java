package json;

/**
 * The JSONException is thrown by the JSON.org classes when things are amiss.
 *
 * @author JSON.org
 * @version 2013-02-10
 */
public class JSONException extends RuntimeException {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 0;
    
    /** The cause. */
    private Throwable cause;

    /**
     * Constructs a JSONException with an explanatory message.
     *
     * @param message
     *            Detail about the reason for the exception.
     */
    public JSONException(String message) {
        super(message);
    }

    /**
     * Constructs a new JSONException with the specified cause.
     *
     * @param cause the cause
     */
    public JSONException(Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    /**
     * Returns the cause of this exception or null if the cause is nonexistent
     * or unknown.
     *
     * @return the cause
     * @returns the cause of this exception or null if the cause is nonexistent
     *          or unknown.
     */
    public Throwable getCause() {
        return this.cause;
    }
}

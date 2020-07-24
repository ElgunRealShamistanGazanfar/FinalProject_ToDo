package app.exception;

public class TaskException extends Exception {

    Integer errorCode;

    public TaskException (Integer ec, String message) {
        super(message);
        errorCode = ec;
    }

    @Override
    public String getMessage() {
        return "Entity error ("+errorCode+") : " + super.getMessage();
    }
}

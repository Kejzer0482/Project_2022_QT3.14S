package presentation;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super("Username already in use");
    }
}

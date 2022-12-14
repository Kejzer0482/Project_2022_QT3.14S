package presentation;

public class DeletingActiveAccountException extends Exception{

    public DeletingActiveAccountException() {
        super("Can't delete active account");
    }
}

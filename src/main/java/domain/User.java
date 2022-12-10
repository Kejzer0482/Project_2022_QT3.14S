package domain;
public class User extends Account{

    public User(String username){
        super(username);
    }

    public String getUsername() {
        return username;
    }
}
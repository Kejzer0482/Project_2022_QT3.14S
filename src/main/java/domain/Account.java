package domain;
import java.util.*;

public abstract class Account{
    protected String username;
    protected List<Media> favorites;

    public Account(String username){
        this.username = username;
        favorites = new ArrayList<>();
    }
    public List<Media> getFavorites(){
        return favorites;
    }

    public String getUsername(){
        return username;
    }

}

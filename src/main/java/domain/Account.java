package domain;
import java.util.*;
abstract class Account{
    protected String username;
    protected List<Media> favorites;

    public Account(String username){
        this.username = username;
    }

    public void addMediaToFavorites(){}

    public void removeFromFavorites(){}

    public void showFavorites(){}

    public String getUserName(){
        return username;
    }
}
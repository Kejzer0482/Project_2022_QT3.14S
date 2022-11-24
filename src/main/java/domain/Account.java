package domain;
import java.util.*;

abstract class Account{
    protected String username;
    protected List<Media> favorites;

    public Account(String username){
        this.username = username;
        favorites = new ArrayList<>();
    }

    public void addMediaToFavorites(Media media){
        favorites.add(media);
    }

    public void removeFromFavorites(Media media){
        favorites.remove(media);
    }

    public List<Media> getFavorites(){
        return favorites;
    }

    public String getUserName(){
        return username;
    }

}

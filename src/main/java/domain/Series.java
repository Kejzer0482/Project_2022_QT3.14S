package domain;
import java.util.*;

public class Series extends Media{
    /*we use index+1 to determine which season it is, so the ArrayList only
    needs to hold the amount of episodes pr season.*/

    List<String> episodes;

    public Series(String title , String releaseYear,List<String> genres, double rating, List<String> episodes, String picture){
        super(title , releaseYear, genres, rating, picture);

        this.episodes = episodes;
    }

    public List<String> display() {
        return episodes;
    }
}
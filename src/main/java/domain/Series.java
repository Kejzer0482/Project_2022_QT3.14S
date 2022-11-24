package domain;
import java.util.*;

class Series extends Media{
    List<String> seasons;
    List<Integer> episodes;

    public Series(String title , String releaseYear, double rating, List<String> genres){
        super(title , releaseYear, rating, genres);
    }
}
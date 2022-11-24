package domain;
import java.util.*;

class Movie extends Media{

    public Movie(String title , String releaseYear, double rating, List<String> genres){
        super(title , releaseYear, rating, genres);
    }

}
package domain;
import java.util.*;

public class Movie extends Media{

    public Movie(String title , String releaseYear, List<String> genres, double rating , String picture){
        super(title , releaseYear, genres, rating, picture);
    }

}
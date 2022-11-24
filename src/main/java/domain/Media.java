package domain;
import java.util.*;

abstract class Media{
    protected String title;
    protected String releaseYear;
    protected List<String> genres;
    protected double rating;
    protected String picture;

    public Media(String title , String releaseYear,List<String> genres, double rating ){
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        genres = new ArrayList<>();
    }

    public String getTitle(){
        return title;
    }

    public String getReleaseYear(){
        return releaseYear;
    }

    public double rating(){
        return rating;
    }
}

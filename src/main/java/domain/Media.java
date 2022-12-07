package domain;
import java.util.*;

public abstract class Media{
    protected String title;
    protected String releaseYear;
    protected List<String> genres;
    protected double rating;
    protected String picture;

    public Media(String title , String releaseYear,List<String> genres, double rating, String picture ){
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.genres = genres;
        this.picture = picture;
    }

    public String getTitle(){
        return title;
    }

    public String getReleaseYear(){
        return releaseYear;
    }

    public double getRating(){
        return rating;
    }

    public List<String> getGenres(){
        return genres;
    }

    public String getPicture() {
        return picture;
    }


}

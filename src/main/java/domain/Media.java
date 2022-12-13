package domain;
import javafx.scene.image.Image;

import java.io.File;
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

    public Image getPicture() {
        File file = new File(picture);
        Image image = new Image(file.toURI().toString());
        return image;
    }

    public List<String> display() {
        return null;
    }


}

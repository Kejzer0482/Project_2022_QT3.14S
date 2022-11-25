package domain;
import java.util.*;

class Series extends Media{
    /*we use index+1 to determine which season it is, so the ArrayList only
    needs to hold the amount of episodes pr season.*/

    List<String> Episodes;

    public Series(String title , String releaseYear,List<String> genres, double rating,  String picture){
        super(title , releaseYear, genres, rating, picture);
    }
}
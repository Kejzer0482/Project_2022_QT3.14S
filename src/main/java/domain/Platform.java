package domain;

import data.DataAccess;
import java.util.*;

public class Platform{
    protected ArrayList<Media> movies;
    protected ArrayList<Media> series;
    public Platform(){

        movies = new ArrayList<>();
        series = new ArrayList<>();

    }

    public List<Media> createMediaList(String path){
       //Retrieving raw data and putting it in an arraylist.
        DataAccess retrieveData = new DataAccess(path);
        List<String> rawDataList = retrieveData.loadFile();

        //Arraylist of media from given data
        List<Media> completeMediaList = new ArrayList<>();

        //Retrieving individual lines of data
        for(String mediaRawDataLine : rawDataList){
            /*
            Retrieving individual datapoints{title, releaseYear, genres, rating}
            */
            ArrayList<String> mediaData = new ArrayList<>();

            for( String dataPoint : mediaRawDataLine.split(";")){
                mediaData.add(dataPoint.trim());
            }
            //Sorting the data, and giving them the correct datatypes//

            //String Title
            String title = mediaData.get(0);

            //String releaseYear
            String releaseYear = mediaData.get(1);

            //ArrayList<String> genres
            List<String> genres = new ArrayList<>();
            String[] genreArray= mediaData.get(2).split(",");
            //adding elements from genreArray to arrayList genre.
            for(int i = 0; i < genreArray.length; i++) {
                genres.add(genreArray[i]);
                }

            //Double rating, replacing comma with dot because of double datatype
            double rating = Double.parseDouble(mediaData.get(3).replaceAll("," , "."));

            //if the mediatype is a series it has an extra datapoint(Seasons-episode)
            List<String> seasons_Episodes = new ArrayList<>();
            if(mediaRawDataLine.length() == 5){
                String[] seasonArray= mediaData.get(4).split(",");
                //adding elements from seasonArray to arrayList seasons.
                for(int i = 0; i < seasonArray.length; i++) {
                    //Modifying data, removing the season part
                    String[] tempArray = seasonArray[i].split("-");
                    seasons_Episodes.add(tempArray[1]);
                }
            }
            /*
                Last datapoint, which isn't a part of the raw data set is the picture
                which we will save as a directory for now
            */

            if (mediaData.size() < 5){
                String picturePath = "src/main/java/data/filmplakater/" + title + ".jpg";
                //Instantiate the object with the sorted data.
                Movie movie = new Movie(title, releaseYear, genres, rating, picturePath);

                //add to list of media which is to be returned.
                completeMediaList.add(movie);



            } else if(mediaData.size() == 5){

                //Instantiate the object with sorted data for series
                String picturePath = "src/main/java/data/serieforsider/" + title +".jpg";
                Series series = new Series(title, releaseYear, genres, rating, seasons_Episodes,  picturePath);
                completeMediaList.add(series);

            }

        }
        return completeMediaList;
    }
    public List<Media> specificGenre(List<Media> MediaList, String genre){
        //Instantiating the list of results
        List<Media> genresList = new ArrayList<>();
        //iterating over each element of media list of a specific type of media
        for(int i = 0;  i < MediaList.size(); i++){
            //fetching object
            Media currentMediaFile = MediaList.get(i);
            //iterating over each genres if multiple genres
            for(String genreTemp : currentMediaFile.getGenres() ){
                //if the genres is equal to that of the relevant one, then we add it to the resulting list
                if(genreTemp.toLowerCase().trim().equals(genre.toLowerCase().trim())){
                    genresList.add(currentMediaFile);
                }

            }
        }
        //Maybe add an unchecked Exception a la "The genre does not exist".
        return genresList;
    }

    public Media search_function (String name){
        List<Media> searchSerie = createMediaList("src/main/java/data/serie.txt");
        List<Media> searchMovie = createMediaList("src/main/java/data/film.txt");
        List<Media> completeList = new ArrayList<>();
        completeList.addAll(searchSerie);
        completeList.addAll(searchMovie);
        
        for(Media current : completeList){
            if(name.toLowerCase().equals(current.getTitle().toLowerCase())){
               return current;}
        }
        return null;
    }

    public static void main(String[] args) {
        Platform platform = new Platform();
        List<Media> movieList = new ArrayList<>();

        movieList = platform.createMediaList("src/main/java/data/serie.txt");

        for(Media series : movieList){
            System.out.println(series.getTitle());
        }
    }
}
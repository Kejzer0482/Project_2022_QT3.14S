package domain;

import data.DataAccess;
import java.util.*;

class Platform{
    ArrayList<Media> movies;
    ArrayList<Media> series;
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
            if(mediaRawDataLine.length() == 5){

                List<String> seasons_Episodes = new ArrayList<>();
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
            if (mediaRawDataLine.length() < 5){
                String picturePath = "/Project_2022_QT3.14S/src/main/java/data/filmplakater/" + title +".jpg";
                //Instantiate the object with the sorted data.
                Movie movie = new Movie(title, releaseYear, genres, rating, picturePath);
                //add to list of media which is to be returned.
                completeMediaList.add(movie);

            } else if(mediaRawDataLine.length() == 5){
                //Instantiate the object with sorted data for series
                String picturePath = "/Project_2022_QT3.14S/src/main/java/data/serieforsider/" + title +".jpg";
                Series series = new Series(title, releaseYear, genres, rating, picturePath);
            }
        }
        return completeMediaList;
    }
}
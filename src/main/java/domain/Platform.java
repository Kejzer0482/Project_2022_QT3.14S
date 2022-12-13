package domain;

import data.DataAccess;
import javafx.scene.chart.PieChart;
import presentation.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

public class Platform{
    private List<Media> movies;
    private List<Media> series;
    private List<Media> complete;
    private List<Account> accounts;
    private Account activeAccount;
    public Platform(){

        movies = new ArrayList<>();
        series = new ArrayList<>();
        complete = new ArrayList<>();
        createMediaLists();
        accounts = new ArrayList<>();
    }

    public void createMediaLists(){
        //Retrieving raw data and putting it in an arraylist.
        DataAccess retrieveMovieData = new DataAccess("src/main/java/data/movies.txt");
        DataAccess retrieveSeriesData = new DataAccess("src/main/java/data/series.txt");
        List<String> rawMovieDataList = retrieveMovieData.loadFile();
        List<String> rawSeriesDataList = retrieveSeriesData.loadFile();
        List<String> rawDataList = new ArrayList<>();
        for (String data : rawMovieDataList) {
            rawDataList.add(data);
        }
        for (String data : rawSeriesDataList) {
            rawDataList.add(data);
        }

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
            if(mediaData.size() == 5){
                String[] seasonArray= mediaData.get(4).split(", ");
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
                String picturePath = "src/main/java/data/movieposters/" + title + ".jpg";
                //Instantiate the object with the sorted data.
                Movie movie = new Movie(title, releaseYear, genres, rating, picturePath);

                //add to list of media which is to be returned.
                movies.add(movie);



            } else if(mediaData.size() == 5){

                //Instantiate the object with sorted data for series
                String picturePath = "src/main/java/data/seriesposters/" + title +".jpg";
                Series series = new Series(title, releaseYear, genres, rating, seasons_Episodes,  picturePath);
                this.series.add(series);

            }
            for (Media media : movies) {
                complete.add(media);
            }
            for (Media media : series) {
                complete.add(media);
            }
        }
    }

    public List<Media> getMovieList() {
        return movies;
    }

    public List<Media> getSeriesList() {
        return series;
    }

    public List<Media> getCompleteList() {
        return complete;
    }

    public List<Media> specificGenre(String type, String genre){
        //Instantiating the list of results
        List<Media> genresList = new ArrayList<>();
        List<Media> list;
        if (type.equals("movies")) {
            list = movies;
        } else {
            list = series;
        }
        //iterating over each element of media list of a specific type of media
        for(int i = 0;  i < list.size(); i++){
            //fetching object
            Media currentMediaFile = list.get(i);
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
    public Account getAccount(String name) {
        for (Account account : accounts) {
            if (account.getUserName().equals(name)) {
                return account;
            }
        }
        return null;
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public void setActiveAccount(String name) {
        activeAccount = getAccount(name);
    }
    public Account getActiveAccount() {
        return activeAccount;
    }

    public void addUser(String name) throws UserAlreadyExistsException {
        for (Account account : accounts) {
            if (account.getUserName().equals(name)) {
                throw new UserAlreadyExistsException();
            }
        }
        User user = new User(name);
        accounts.add(user);
    }

    public void deleteAccount(String name) {
        for (Account account : accounts) {
            if (account.getUserName().equals(name)) {
                accounts.remove(account);
                break;
            }
        }
    }

    public Media search_function (String text){
        for(Media media : complete){
            if(text.toLowerCase().equals(media.getTitle().toLowerCase())){
                return media;}
        }
        return null;
    }
}
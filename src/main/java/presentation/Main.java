package presentation;

import domain.Platform;
import domain.Media;
import domain.Account;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    private Platform streaming = new Platform();
    private BorderPane root = new BorderPane();
    private StackPane menuPane = new StackPane();
    private StackPane contentPane = new StackPane();
    private FlowPane movieFlow = new FlowPane();
    private FlowPane seriesFlow = new FlowPane();
    private ScrollPane movieScroll = new ScrollPane(movieFlow);
    private ScrollPane seriesScroll = new ScrollPane(seriesFlow);
    private VBox menuList = new VBox();
    private VBox contentList = new VBox();
    private HBox movieGenres = new HBox();
    private HBox seriesGenres = new HBox();
    private Label movieLabel = createLabel("Movies");
    private Label seriesLabel = createLabel("Series");
    private Label myListLabel = createLabel("My List");
    private HBox accountInfo = new HBox();
    private Label accountLabel = new Label();
    private Button homeButton = createMenuButton("Home");        //Calling homemade createButton method
    private Button moviesButton = createMenuButton("Movies");
    private Button seriesButton = createMenuButton("Series");
    private Button myListButton = createMenuButton("My List");
    private Button playButton = new Button();
    private Button saveButton = new Button();

    public void start(Stage primaryStage) {
        //Layers
        menuPane.setStyle("-fx-background-color: grey");
        menuPane.setMinWidth(300);

        contentPane.setStyle("-fx-background-color: black");
        contentPane.getChildren().add(contentList);
        contentPane.setPadding(new Insets(0, 10, 10, 10));

        contentList.setAlignment(Pos.CENTER);

        movieFlow.setHgap(8);
        movieFlow.setVgap(8);
        movieFlow.setAlignment(Pos.CENTER);
        movieFlow.setStyle("-fx-background-color: purple");
        seriesFlow.setHgap(8);
        seriesFlow.setVgap(8);
        seriesFlow.setAlignment(Pos.CENTER);
        seriesFlow.setStyle("-fx-background-color: purple");

        //Genres
        String[] movieGenre = createGenres("movie");
        for (String genre : movieGenre) {
            Button button = createGenreButton(genre);
            button.setOnMouseClicked((event) -> {
                getMovies(genre);
            });
            movieGenres.getChildren().add(button);
        }
        String[] seriesGenre = createGenres("series");
        for (String genre : seriesGenre) {
            Button button = createGenreButton(genre);
            button.setOnMouseClicked((event) -> {
                getSeries(genre);
            });
            seriesGenres.getChildren().add(button);
        }

        //SearchBar
        HBox searchBox = new HBox();
        searchBox.setSpacing(8);
        searchBox.setAlignment(Pos.CENTER);
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                search(searchBar.getText());
                searchBar.clear();
            }
        });
        Button searchButton = new Button("Search");
        searchButton.setOnMouseClicked((event) -> {
            search(searchBar.getText());
            searchBar.clear();
        });
        searchBox.getChildren().add(searchBar);
        searchBox.getChildren().add(searchButton);
        menuList.getChildren().add(searchBox);

        //AccountInfo
        Button switchAccount = new Button("Switch");
        switchAccount.setOnMouseClicked((event) -> {
            Stage switchPopup = new Stage();
            HBox accountList = new HBox();
            accountList.setSpacing(10);
            accountList.setAlignment(Pos.CENTER);
            for (Account account : streaming.getAccounts()) {
                VBox accountInfo = new VBox();
                accountInfo.setAlignment(Pos.CENTER);
                accountInfo.setSpacing(10);
                Label accountName = new Label(account.getUserName());
                Button button = new Button("Switch");
                button.setOnMouseClicked((event2) -> {
                    streaming.setActiveAccount(account.getUserName());
                    accountLabel.setText("Logged in as: " + streaming.getActiveAccount().getUserName());
                    homeButton();
                    switchPopup.close();
                });
                accountInfo.getChildren().add(accountName);
                accountInfo.getChildren().add(button);
                accountList.getChildren().add(accountInfo);
            }
            Scene switchScene = new Scene(accountList, 200, 70);
            switchPopup.setTitle("Accounts");
            switchPopup.setScene(switchScene);
            switchPopup.show();
        });
        accountInfo.setSpacing(5);
        accountInfo.setAlignment(Pos.CENTER);
        accountLabel.setText("Logged in as: " + streaming.getActiveAccount().getUserName());
        accountLabel.setFont(new Font("Calibri", 20));
        accountLabel.setStyle("-fx-text-fill: black");
        accountInfo.getChildren().add(accountLabel);
        accountInfo.getChildren().add(switchAccount);

        //Menu
        menuList.setSpacing(30);
        menuList.setAlignment(Pos.CENTER);                       //Places button in the middle instead of top left
        menuPane.getChildren().add(menuList);                    //Adds menu list to the stackPane
        menuList.getChildren().add(accountInfo);
        homeButton.setOnMouseClicked((event) -> {
            homeButton();
        });
        moviesButton.setOnMouseClicked((event) -> {
            moviesButton();
        });
        seriesButton.setOnMouseClicked((event) -> {
            seriesButton();
        });
        myListButton.setOnMouseClicked((event) -> {
            myListButton();
        });

        //Scene
        Scene scene = new Scene(root, 1600, 900);       //Creates scene and sets dimensions of window
        root.setCenter(contentPane);
        root.setLeft(menuPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QT3.14s&Chill");
        primaryStage.show();
        homeButton();
    }

    private void homeButton() {
        contentList.getChildren().clear();
        contentList.getChildren().add(movieLabel);
        contentList.getChildren().add(movieScroll);
        contentList.getChildren().add(seriesLabel);
        contentList.getChildren().add(seriesScroll);
        movieFlow.setPrefSize(1265, 440);
        seriesFlow.setPrefSize(1265, 440);
        getMovies("all");
        getSeries("all");
    }

    private void moviesButton() {
        contentList.getChildren().clear();
        contentList.getChildren().add(movieGenres);
        contentList.getChildren().add(movieScroll);
        movieFlow.setPrefSize(1265, 840);
        getMovies("all");
    }

    private void seriesButton() {
        contentList.getChildren().clear();
        contentList.getChildren().add(seriesGenres);
        contentList.getChildren().add(seriesScroll);
        seriesFlow.setPrefSize(1265, 840);
        getSeries("all");
    }

    private void myListButton() {
        contentList.getChildren().clear();
        contentList.getChildren().add(myListLabel);
        contentList.getChildren().add(movieScroll);
        movieFlow.setPrefSize(1265, 840);
        getFavourites();

    }
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(200, 70);
        button.setStyle("-fx-background-color: white");
        button.setFont(new Font("Calibri", 40));
        menuList.getChildren().add(button);
        return button;
    }

    private Button createGenreButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(100, 40);
        button.setStyle("-fx-background-color: black; -fx-text-fill: white");
        return button;
    }

    private String[] createGenres(String type) {
        String input;
        if (type.equals("movie")) {
            input = "Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure, Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy , Musical, Western, Music";
        } else {
            input = "Talk-show, Documentary, Crime, Drama, Action, Adventure, Drama, Comedy, Fantasy, Animation, Horror, Sci-fi, War, Thriller, Mystery, Biography, History, Family, Western, Romance, Sport";
        }
        String[] genres = input.split(", ");
        return genres;
    }
    private Label createLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font("Calibri", 30));
        label.setStyle("-fx-text-fill: white");
        return label;
    }

    private void getMovies(String genre) {
        List<Media> list;
        movieFlow.getChildren().clear();
        if (genre.equals("all")) {
            list = streaming.getMovieList();
        } else {
            list = streaming.specificGenre("movies", genre);
        }
        for (Media media : list) {
            addMoviePoster(media);
        }
    }

    private void getSeries(String genre) {
        List<Media> list;
        seriesFlow.getChildren().clear();
        if (genre.equals("all")) {
            list = streaming.getSeriesList();
        } else {
            list = streaming.specificGenre("series", genre);
        }
        for (Media media : list) {
            addSeriesPoster(media);
        }
    }

    private void getFavourites() {
        movieFlow.getChildren().clear();
        for (Media media : streaming.getActiveAccount().getFavorites()) {
            addMoviePoster(media);
        }
    }
    private void addMoviePoster(Media media) {
        ImageView poster = new ImageView(media.getPicture());
        poster.setOnMouseClicked((event) -> {
            popupWindow(media);
        });
        movieFlow.getChildren().add(poster);
    }
    private void addSeriesPoster(Media media) {
        ImageView poster = new ImageView(media.getPicture());
        poster.setOnMouseClicked((event) -> {
            popupWindow(media);
        });
        seriesFlow.getChildren().add(poster);
    }
    private void popupWindow(Media media) {
        Stage popup = new Stage();
        StackPane popupPane = new StackPane();
        popupPane.setStyle("-fx-background-color: black");
        HBox popupContent = new HBox();
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setSpacing(20);
        VBox popupInfo = new VBox();
        popupInfo.setSpacing(10);
        popupInfo.setAlignment(Pos.CENTER);
        HBox playInfo = new HBox();
        playInfo.setAlignment(Pos.CENTER);
        playInfo.setSpacing(15);
        Label title = createLabel(media.getTitle() + "");
        Label rating = createLabel("Rating: " + media.getRating());
        Label releaseYear = createLabel("" + media.getReleaseYear());
        Label genres = createLabel(media.getGenres() + "");
        playButton();
        if (streaming.getActiveAccount().getFavorites().contains(media)) {
            savedButton(media);
        } else {
            saveButton(media);
        }
        ImageView poster = new ImageView(media.getPicture());
        popupContent.getChildren().add(poster);
        popupContent.getChildren().add(popupInfo);
        popupInfo.getChildren().add(title);
        popupInfo.getChildren().add(rating);
        popupInfo.getChildren().add(releaseYear);
        popupInfo.getChildren().add(genres);
        popupInfo.getChildren().add(playInfo);
        playInfo.getChildren().add(playButton);
        playInfo.getChildren().add(saveButton);
        popupPane.getChildren().add(popupContent);
        Scene popupScene = new Scene(popupPane, 800, 350);
        popup.setScene(popupScene);
        popup.setTitle(media.getTitle());
        popup.show();
    }

    public void playButton() {
        playButton.setText("PLAY");
        playButton.setStyle("-fx-background-color: green; -fx-text-fill: white");
        playButton.setOnMouseClicked((event) -> {
            stopButton();
        });
    }

    public void stopButton() {
        playButton.setText("STOP");
        playButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
        playButton.setOnMouseClicked((event) -> {
            playButton();
        });
    }

    public void saveButton(Media media) {
        saveButton.setText("Add to My List");
        saveButton.setStyle("-fx-background-color: white; -fx-text-fill: black");
        saveButton.setOnMouseClicked((event) -> {
            streaming.getActiveAccount().getFavorites().add(media);
            savedButton(media);
        });
    }

    public void savedButton(Media media) {
        saveButton.setText("Added to list");
        saveButton.setStyle("-fx-background-color: black; -fx-text-fill: white");
        saveButton.setOnMouseClicked((event) -> {
            streaming.getActiveAccount().getFavorites().remove(media);
            saveButton(media);
        });
    }
    public void search(String search) {
        Media result = streaming.search_function(search);
        Label searchResult = createLabel("Search result of: " + search);
        contentList.getChildren().clear();
        contentList.getChildren().add(searchResult);
        contentList.getChildren().add(movieScroll);
        movieFlow.getChildren().clear();
        movieFlow.setPrefSize(1265, 840);
        try {
            addMoviePoster(result);
        } catch (NullPointerException e) {
            searchResult.setText("No results: Browsing all movies");
            getMovies("all");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

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
    private ScrollPane movieScroll = new ScrollPane(movieFlow);             //Creates new ScrollPane and attaches movieFlow to it
    private ScrollPane seriesScroll = new ScrollPane(seriesFlow);           //Creates new ScrollPane and attaches seriesFlow to it
    private VBox menuList = new VBox();
    private VBox contentList = new VBox();
    private HBox movieGenres1 = new HBox();
    private HBox movieGenres2 = new HBox();
    private HBox seriesGenres1 = new HBox();
    private HBox seriesGenres2 = new HBox();
    private Label movieLabel = createLabel("Movies");           //Calling homemade createLabel method
    private Label seriesLabel = createLabel("Series");          //Calling homemade createLabel method
    private Label myListLabel = createLabel("My List");         //Calling homemade createLabel method
    private HBox accountInfo = new HBox();
    private Label accountLabel = new Label();
    private Button homeButton = createMenuButton("Home");       //Calling homemade createButton method
    private Button moviesButton = createMenuButton("Movies");   //Calling homemade createButton method
    private Button seriesButton = createMenuButton("Series");   //Calling homemade createButton method
    private Button myListButton = createMenuButton("My List");  //Calling homemade createButton method
    private Button playButton = new Button();
    private Button saveButton = new Button();

    public void start(Stage primaryStage) {
        /* Layout
         * @desc Setting layout on different panes and elements
         */
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

        /* Genres
        * @desc Creating genre buttons for movies and series pages.
                Splitting both into two HBoxes for better visual overview.
        * @next Code duplication. Might be nice to make a method for this
         */
        movieGenres1.setAlignment(Pos.CENTER);
        movieGenres2.setAlignment(Pos.CENTER);
        String[] movieGenre = createGenres("movie");
        int amount1 = movieGenre.length;
        for (int i = 0; i < amount1; i++) {
            Button button = createGenreButton(movieGenre[i]);
            button.setStyle("-fx-border-color: white; -fx-background-color: black; -fx-text-fill: white");
            button.setPrefWidth(130);
            String genre = movieGenre[i];
            button.setOnMouseClicked((event) -> {                   //Returns all movies from a specific genre on click
                getMovies(genre);
            });
            if (i % 2 == 0) {
                movieGenres1.getChildren().add(button);
            } else {
                movieGenres2.getChildren().add(button);
            }
        }
        seriesGenres1.setAlignment(Pos.CENTER);
        seriesGenres2.setAlignment(Pos.CENTER);
        String[] seriesGenre = createGenres("series");
        int amount2 = seriesGenre.length;
        for (int i = 0; i < amount2; i++) {
            Button button = createGenreButton(seriesGenre[i]);
            button.setStyle("-fx-border-color: white; -fx-background-color: black; -fx-text-fill: white");
            button.setPrefWidth(130);
            String genre = seriesGenre[i];
            button.setOnMouseClicked((event) -> {
                getSeries(genre);
            });
            if (i % 2 == 0) {
                seriesGenres1.getChildren().add(button);
            } else {
                seriesGenres2.getChildren().add(button);
            }
        }

        /* Search Bar
         * @desc Adding a search bar to the menu list
         */
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

        /* Account Info
         * @desc Adding account information and switch button to the menu list
         */
        Button switchAccount = new Button("Switch");
        switchAccount.setOnMouseClicked((event) -> {
            switchAccount();
        });
        accountInfo.setSpacing(5);
        accountInfo.setAlignment(Pos.CENTER);
        accountLabel.setText("Logged in as: " + streaming.getActiveAccount().getUserName());
        accountLabel.setFont(new Font("Calibri", 20));
        accountLabel.setStyle("-fx-text-fill: black");
        accountInfo.getChildren().add(accountLabel);
        accountInfo.getChildren().add(switchAccount);

        /* Menu List
         * @desc Setting up the menu list and adding function to menu buttons (They call their respective methods)
         */
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

        /* Initialize
         * @desc Finalizes the scene and makes the program show
         */
        Scene scene = new Scene(root, 1600, 900);       //Creates scene and sets dimensions of window
        root.setCenter(contentPane);
        root.setLeft(menuPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QT3.14s&Chill");
        primaryStage.show();
        homeButton();                                         //Starts the program at the homepage
    }

    private void homeButton() { //Clears content list and builds the homepage
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

    private void moviesButton() { //Clears content list and builds the moviespage
        contentList.getChildren().clear();
        contentList.getChildren().add(movieGenres1);
        contentList.getChildren().add(movieGenres2);
        contentList.getChildren().add(movieScroll);
        movieFlow.setPrefSize(1265, 840);
        getMovies("all");
    }

    private void seriesButton() { //Clears content list and builds the seriespage
        contentList.getChildren().clear();
        contentList.getChildren().add(seriesGenres1);
        contentList.getChildren().add(seriesGenres2);
        contentList.getChildren().add(seriesScroll);
        seriesFlow.setPrefSize(1265, 840);
        getSeries("all");
    }

    private void myListButton() { //Clears content list and builds the user-specific my list
        contentList.getChildren().clear();
        contentList.getChildren().add(myListLabel);
        contentList.getChildren().add(movieScroll);
        movieFlow.setPrefSize(1265, 840);
        getFavourites();

    }
    private Button createMenuButton(String text) { //Creates buttons for the menu list. Meld #1 (Can be melded with other of same meld value)
        Button button = new Button(text);
        button.setPrefSize(200, 70);
        button.setStyle("-fx-background-color: white");
        button.setFont(new Font("Calibri", 40));
        menuList.getChildren().add(button);
        return button;
    }

    private Button createGenreButton(String text) { //Creates buttons for genres in movie and series tab. Meld #1
        Button button = new Button(text);
        button.setPrefSize(100, 40);
        button.setStyle("-fx-background-color: black; -fx-text-fill: white");
        return button;
    }

    private String[] createGenres(String type) { //Creates an array of genre strings
        String input;
        if (type.equals("movie")) {
            input = "Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure, Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy , Musical, Western, Music";
        } else {
            input = "Talk-show, Documentary, Crime, Drama, Action, Adventure, Drama, Comedy, Fantasy, Animation, Horror, Sci-fi, War, Thriller, Mystery, Biography, History, Family, Western, Romance, Sport";
        }
        String[] genres = input.split(", ");
        return genres;
    }
    private Label createLabel(String text) { //Creates a label with text, color, size and font
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font("Calibri", 30));
        label.setStyle("-fx-text-fill: white");
        return label;
    }

    private void getMovies(String genre) { //Adds movies to the movie flowpane
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

    private void getSeries(String genre) { //Adds series to the series flowpane
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

    private void getFavourites() { //Adds all user-specific favourites to the movie flowpane (Also adds series)
        movieFlow.getChildren().clear();
        for (Media media : streaming.getActiveAccount().getFavorites()) {
            addMoviePoster(media);
        }
    }
    private void addMoviePoster(Media media) { //Adding posters to moviepane. Meld #2
        ImageView poster = new ImageView(media.getPicture());
        poster.setOnMouseClicked((event) -> {
            popupWindow(media);
        });
        movieFlow.getChildren().add(poster);
    }
    private void addSeriesPoster(Media media) { //Adding posters to seriespane. Meld #2
        ImageView poster = new ImageView(media.getPicture());
        poster.setOnMouseClicked((event) -> {
            popupWindow(media);
        });
        seriesFlow.getChildren().add(poster);
    }
    private void popupWindow(Media media) { //Creates a new window for the popup window when clicking a movie or series
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

    public void playButton() { //Defines the play button before being pressed
        playButton.setText("PLAY");
        playButton.setStyle("-fx-background-color: green; -fx-text-fill: white");
        playButton.setOnMouseClicked((event) -> {
            stopButton();
        });
    }

    public void stopButton() { //Defines the play button after being pressed
        playButton.setText("STOP");
        playButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
        playButton.setOnMouseClicked((event) -> {
            playButton();
        });
    }

    public void saveButton(Media media) { //Saves media to user-specific 'my list'
        saveButton.setText("Add to My List");
        saveButton.setStyle("-fx-background-color: white; -fx-text-fill: black");
        saveButton.setOnMouseClicked((event) -> {
            streaming.getActiveAccount().getFavorites().add(media);
            savedButton(media);
        });
    }

    public void savedButton(Media media) { //Removes media from user-specific 'my list'
        saveButton.setText("Added to list");
        saveButton.setStyle("-fx-background-color: black; -fx-text-fill: white");
        saveButton.setOnMouseClicked((event) -> {
            streaming.getActiveAccount().getFavorites().remove(media);
            saveButton(media);
        });
    }
    public void search(String search) { //Searches for movie or series through the search bar
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
            Stage searchError = new Stage();
            StackPane errorPane = new StackPane();
            errorPane.setStyle("-fx-background-color: #ff5959");
            Scene errorScene = new Scene(errorPane, 450, 150);
            Label errorMessage = new Label("No results found. Returning to Homepage");
            errorMessage.setStyle("-fx-text-fill: white");
            errorMessage.setFont(new Font("Calibri", 25));
            errorPane.setAlignment(Pos.CENTER);
            errorPane.getChildren().add(errorMessage);
            searchError.setScene(errorScene);
            searchError.setTitle("Error 404");
            searchError.show();
            homeButton();
        }
    }

    public void switchAccount() { //MISSING A LOT. Vi tager det på mandag fysisk?
        Stage switchPopup = new Stage();
        HBox switchContent = new HBox();
        VBox accountMenu = new VBox();
        VBox accountView = new VBox();
        ScrollPane accountScroll = new ScrollPane(accountView);
        for (Account account : streaming.getAccounts()) {
            VBox accountInfo = new VBox();
            Label accountName = new Label(account.getUserName());
            Button button = new Button("Switch");
            /*
             * @desc Switch button which switches the active account and closes the switch account window
             */
            button.setOnMouseClicked((event2) -> {
                streaming.setActiveAccount(account.getUserName());
                accountLabel.setText("Logged in as: " + streaming.getActiveAccount().getUserName());
                homeButton();
                switchPopup.close();
            });
            /*
             * @desc Delete button which deletes account and reopens the switch account window
             */
            Button deleteButton = new Button("Delete");
            deleteButton.setOnMouseClicked((event) -> {
                streaming.deleteAccount(account.getUserName()); //Exception????
                switchAccount();
                switchPopup.close();
            });
        }
        Button addUserButton = new Button("Add User");
        addUserButton.setOnMouseClicked((event) -> {
            Stage addPopup = new Stage();
            /*
             * @desc Textfield for creating new user. Closes itself and reopens the switch account window
             */
            TextField nameField = new TextField();
            nameField.setOnKeyPressed((event2) -> {
                if (event2.getCode().equals(KeyCode.ENTER)) {
                    streaming.addUser(nameField.getText());
                    switchPopup.close();
                    addPopup.close();
                }
            });
            addPopup.show();
        });
        switchContent.getChildren().add(accountMenu);
        switchContent.getChildren().add(accountScroll);
        Scene switchScene = new Scene(switchContent, 500, 600);
        switchPopup.setTitle("Accounts");
        switchPopup.setScene(switchScene);
        switchPopup.show();
    }

    public static void main(String[] args) { //Running the application
        launch(args);
    }
}

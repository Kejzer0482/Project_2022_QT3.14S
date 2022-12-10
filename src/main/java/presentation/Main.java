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
    private HBox genreButtons1 = new HBox();
    private HBox genreButtons2 = new HBox();
    private HBox searchBox = new HBox();
    private Label movieLabel = createLabel("Movies");           //Calling homemade createLabel method
    private Label seriesLabel = createLabel("Series");          //Calling homemade createLabel method
    private Label myListLabel = createLabel("My List");         //Calling homemade createLabel method
    private HBox accountInfo = new HBox();
    private Label accountLabel = new Label();
    private Button playButton = new Button();
    private Button saveButton = new Button();

    public void start(Stage primaryStage) {
        /* Layout
         * @desc Setting layout on different panes and elements
         */
        // Menu StackPane
        menuPane.setStyle("-fx-background-color: grey");
        menuPane.setMinWidth(300);

        // Content StackPane
        contentPane.setStyle("-fx-background-color: black");
        contentPane.getChildren().add(contentList);
        contentPane.setPadding(new Insets(0, 10, 10, 10));

        // Content list (right side content)
        contentList.setAlignment(Pos.CENTER);

        // Movies FlowPane
        movieFlow.setHgap(8);
        movieFlow.setVgap(8);
        movieFlow.setAlignment(Pos.CENTER);
        movieFlow.setStyle("-fx-background-color: purple");

        // Series FlowPane
        seriesFlow.setHgap(8);
        seriesFlow.setVgap(8);
        seriesFlow.setAlignment(Pos.CENTER);
        seriesFlow.setStyle("-fx-background-color: purple");

        // Active account info line
        accountInfo.setSpacing(5);
        accountInfo.setAlignment(Pos.CENTER);

        // Active account Label
        accountLabel.setText("Logged in as: " + streaming.getActiveAccount().getUserName());
        accountLabel.setFont(new Font("Calibri", 20));
        accountLabel.setStyle("-fx-text-fill: black");

        // Menu list
        menuList.setSpacing(30);
        menuList.setAlignment(Pos.CENTER);                       //Places button in the middle instead of top left

        // Search Box
        searchBox.setSpacing(8);
        searchBox.setAlignment(Pos.CENTER);

        // Genre button lists
        genreButtons1.setAlignment(Pos.CENTER);
        genreButtons2.setAlignment(Pos.CENTER);

        /* Search Bar
         * @desc Adding a search bar to the menu list
         */
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                search(searchBar.getText());
                searchBar.clear();
            }
        });

        /* Menu Buttons
         * @desc Adding menu buttons
         */
        Button homeButton = createMenuButton("Home");
        homeButton.setOnMouseClicked((event) -> {
            homeButton();
        });
        Button moviesButton = createMenuButton("Movies");
        moviesButton.setOnMouseClicked((event) -> {
            moviesButton();
        });
        Button seriesButton = createMenuButton("Series");
        seriesButton.setOnMouseClicked((event) -> {
            seriesButton();
        });
        Button myListButton = createMenuButton("My List");
        myListButton.setOnMouseClicked((event) -> {
            myListButton();
        });
        Button switchAccount = new Button("Switch");
        switchAccount.setOnMouseClicked((event) -> {
            switchAccount();
        });
        Button searchButton = new Button("Search");
        searchButton.setOnMouseClicked((event) -> {
            search(searchBar.getText());
            searchBar.clear();
        });

        /* Puzzle
        * @desc Putting everything together
         */
        root.setCenter(contentPane);
        root.setLeft(menuPane);
        menuPane.getChildren().add(menuList);                    //Adds menu list to the stackPane
        menuList.getChildren().add(accountInfo);
        menuList.getChildren().add(searchBox);
        accountInfo.getChildren().add(accountLabel);
        accountInfo.getChildren().add(switchAccount);
        searchBox.getChildren().add(searchBar);
        searchBox.getChildren().add(searchButton);

        /* Initialize
         * @desc Finalizes the scene and makes the program show
         */
        Scene scene = new Scene(root, 1600, 900);       //Creates scene and sets dimensions of window
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
        contentList.getChildren().add(genreButtons1);
        contentList.getChildren().add(genreButtons2);
        contentList.getChildren().add(movieScroll);
        movieFlow.setPrefSize(1265, 840);
        genreButtons1.getChildren().clear();
        genreButtons2.getChildren().clear();
        createGenres("movie");
        getMovies("all");
    }

    private void seriesButton() { //Clears content list and builds the seriespage
        contentList.getChildren().clear();
        contentList.getChildren().add(genreButtons1);
        contentList.getChildren().add(genreButtons2);
        contentList.getChildren().add(seriesScroll);
        seriesFlow.setPrefSize(1265, 840);
        genreButtons1.getChildren().clear();
        genreButtons2.getChildren().clear();
        createGenres("series");
        getSeries("all");
    }

    private void myListButton() { //Clears content list and builds the user-specific my list
        contentList.getChildren().clear();
        contentList.getChildren().add(myListLabel);
        contentList.getChildren().add(movieScroll);
        movieFlow.setPrefSize(1265, 840);
        getFavourites();

    }
    private Button createMenuButton(String text) { //Creates buttons for the menu list.
        Button button = new Button(text);
        button.setPrefSize(200, 70);
        button.setStyle("-fx-background-color: white");
        button.setFont(new Font("Calibri", 40));
        menuList.getChildren().add(button);
        return button;
    }

    private void createGenres(String type) { //Creates an array of genre strings and then creates buttons
        String input;
        if (type.equals("movie")) {
            input = "Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure, Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy , Musical, Western, Music";
        } else {
            input = "Talk-show, Documentary, Crime, Drama, Action, Adventure, Drama, Comedy, Fantasy, Animation, Horror, Sci-fi, War, Thriller, Mystery, Biography, History, Family, Western, Romance, Sport";
        }
        String[] genres = input.split(", ");
        for (int i = 0; i < genres.length; i++) {
            if (i % 2 == 0) {
                genreButtons1.getChildren().add(createGenreButton(genres[i]));
            } else {
                genreButtons2.getChildren().add(createGenreButton(genres[i]));
            }
        }
    }

    private Button createGenreButton(String text) { //Creates buttons for genres in movie and series tab.
        Button button = new Button(text);
        button.setPrefSize(130, 40);
        button.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: white");
        return button;
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
            addPoster(media, "movies");
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
            addPoster(media, "series");
        }
    }

    private void getFavourites() { //Adds all user-specific favourites to the movie flowpane (Also adds series)
        movieFlow.getChildren().clear();
        for (Media media : streaming.getActiveAccount().getFavorites()) {
            addPoster(media, "movie");
        }
    }

    private void addPoster(Media media, String type) { //Adds posters to the flowpanes with media and "movies"/"series" as arguments
        ImageView poster = new ImageView(media.getPicture());
        poster.setOnMouseClicked((event) -> {
            popupWindow(media);
        });
        if (type.equals("movies")) {
            movieFlow.getChildren().add(poster);
        } else {
            seriesFlow.getChildren().add(poster);
        }
    }

    private void popupWindow(Media media) { //Creates a new window for the popup window when clicking a movie or series
        Stage popup = new Stage();
        // Main pane
        StackPane popupPane = new StackPane();
        popupPane.setStyle("-fx-background-color: black");
        // HBox overall
        HBox popupContent = new HBox();
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setSpacing(20);
        // VBox media info
        VBox popupInfo = new VBox();
        popupInfo.setSpacing(10);
        popupInfo.setAlignment(Pos.CENTER);
        // HBox buttons
        HBox playInfo = new HBox();
        playInfo.setAlignment(Pos.CENTER);
        playInfo.setSpacing(15);
        // Labels media info
        Label title = createLabel(media.getTitle() + "");
        Label rating = createLabel("Rating: " + media.getRating());
        Label releaseYear = createLabel("" + media.getReleaseYear());
        Label genres = createLabel(media.getGenres() + "");
        // Buttons
        playButton();
        if (streaming.getActiveAccount().getFavorites().contains(media)) {
            savedButton(media);
        } else {
            saveButton(media);
        }
        // Image
        ImageView poster = new ImageView(media.getPicture());
        // Puzzle solving
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
        // Finalize
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
            addPoster(result, "movies");
        } catch (NullPointerException e) { //Error message if input does not match any media title
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

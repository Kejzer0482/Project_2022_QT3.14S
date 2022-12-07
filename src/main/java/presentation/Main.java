package presentation;

import domain.Platform;
import domain.Media;
import domain.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.List;

public class Main extends Application {
    private Platform streaming = new Platform();
    private BorderPane root = new BorderPane();
    private StackPane moviesPane = new StackPane();
    private StackPane seriesPane = new StackPane();
    private HBox moviesContent = new HBox();
    private HBox seriesContent = new HBox();
    private VBox menuList = new VBox();
    private StackPane stackPane = new StackPane();
    private GridPane gridPane = new GridPane();
    private FlowPane flowMovies = new FlowPane();
    private FlowPane flowSeries = new FlowPane();
    private ScrollPane scrollMovies = new ScrollPane(flowMovies);
    private ScrollPane scrollSeries = new ScrollPane(flowSeries);
    private Button homeButton = createMenuButton("Home");        //Calling homemade createButton method
    private Button moviesButton = createMenuButton("Movies");
    private Button seriesButton = createMenuButton("Series");
    private Button myListButton = createMenuButton("My List");
    private Button actionButton = new Button("Action");

    public void start(Stage primaryStage) {
        //Layers
        stackPane.setStyle("-fx-background-color: grey");
        stackPane.setMinWidth(300);

        moviesPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        moviesPane.setStyle("-fx-background-color: red");
        moviesPane.setPadding(new Insets(10, 10, 10, 10));

        seriesPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);                                          //Adds vertical spacing between grid elements
        gridPane.setHgap(10);                                          //Adds Horizontal spacing between grid elements
        gridPane.setStyle("-fx-background-color: black");
        gridPane.setGridLinesVisible(true);
        //gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);       //Makes the gridpane take out as much space as it can.

        scrollMovies.setStyle("-fx-background-color: black");
        scrollMovies.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        scrollSeries.setStyle("-fx-background-color: black");
        scrollSeries.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        flowMovies.setHgap(10);
        flowMovies.setVgap(10);
        flowMovies.setAlignment(Pos.CENTER);
        flowMovies.setStyle("-fx-background-color: purple");
        flowMovies.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        flowMovies.setPrefWrapLength(1260);

        flowSeries.setHgap(10);
        flowSeries.setVgap(10);
        flowSeries.setAlignment(Pos.CENTER);
        flowSeries.setStyle("-fx-background-color: purple");
        flowSeries.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        flowSeries.setPrefWrapLength(1260);

        moviesContent.getChildren().add(actionButton);
        actionButton.setOnMouseClicked((event) -> {
            getMovies("action");
        });

        //Menu
        menuList.setSpacing(30);
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
        menuList.getChildren().add(homeButton);
        menuList.getChildren().add(moviesButton);
        menuList.getChildren().add(seriesButton);
        menuList.getChildren().add(myListButton);
        menuList.setAlignment(Pos.CENTER);                       //Places button in the middle instead of top left
        stackPane.getChildren().add(menuList);                   //Adds menu list to the stackPane

        //Scene
        Scene scene = new Scene(root, 1600, 900);       //Creates scene and sets dimensions of window
        root.setLeft(stackPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QT3.14s&Chill");
        primaryStage.show();
        homeButton();
    }

    private void homeButton() {
        root.setCenter(gridPane);
        gridPane.getChildren().clear();
        gridPane.add(scrollMovies, 0, 0);         //Adds the scrollpane for movies to column 0, row 0
        gridPane.add(scrollSeries, 0, 1);         //Adds the scrollpane for series to column 0, row 1
        scrollMovies.setPrefSize(1260, 440);
        scrollSeries.setPrefSize(1260, 440);
        getMovies("all");
        getSeries("all");
        homeButton.setDisable(true);
        moviesButton.setDisable(false);
        seriesButton.setDisable(false);
        myListButton.setDisable(false);
    }

    private void moviesButton() {
        root.setCenter(moviesPane);
        moviesPane.getChildren().clear();
        moviesPane.getChildren().add(scrollMovies);
        moviesPane.getChildren().add(moviesContent);
        getMovies("all");
        homeButton.setDisable(false);
        moviesButton.setDisable(true);
        seriesButton.setDisable(false);
        myListButton.setDisable(false);
    }

    private void seriesButton() {
        root.setCenter(scrollSeries);
        getSeries("all");
        homeButton.setDisable(false);
        moviesButton.setDisable(false);
        seriesButton.setDisable(true);
        myListButton.setDisable(false);
    }

    private void myListButton() {
        root.setCenter(gridPane);
        getMovies("myList");
        getSeries("myList");
        homeButton.setDisable(false);
        moviesButton.setDisable(false);
        seriesButton.setDisable(false);
        myListButton.setDisable(true);
    }
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(200, 70);
        button.setStyle("-fx-background-color: white");
        return button;
    }

    private void getMovies(String genre) {
        List<Media> movies = streaming.createMediaList("src/main/java/data/film.txt");
        flowMovies.getChildren().clear();
        if (genre.equals("all")) {
            for (Media media : movies) {
                String path = media.getPicture();
                File file = new File(path);
                Image image = new Image(file.toURI().toString());
                ImageView poster = new ImageView(image);
                flowMovies.getChildren().add(poster);
            }
        } else if (genre.equals("myList")) {

        } else {
            List<Media> list = streaming.specificGenre(movies, genre);
            for (Media media : list) {
                String path = media.getPicture();
                File file = new File(path);
                Image image = new Image(file.toURI().toString());
                ImageView poster = new ImageView(image);
                flowMovies.getChildren().add(poster);
            }
        }
    }

    private void getSeries(String genre) {
        List<Media> series = streaming.createMediaList("src/main/java/data/serie.txt");
        flowSeries.getChildren().clear();
        if (genre.equals("all")) {
            for (Media media : series) {
                String path = media.getPicture();
                File file = new File(path);
                Image image = new Image(file.toURI().toString());
                ImageView poster = new ImageView(image);
                flowSeries.getChildren().add(poster);
            }
        } else if (genre.equals("myList")) {

        } else {

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    /*

        //User bruger = new User("Oliver");
        //List<Media> krimiList = streaming.specificGenre(complete, "crime");

        for(Media krimiMedia : krimiList){
            System.out.println(krimiMedia.getTitle() + " " + krimiMedia.getGenres() + krimiMedia.getClass() );
        }

        //Files
        File directory = new File("src/main/java/data/filmplakater");    //Adds the directory
        File[] files = directory.listFiles();                                     //Retrieves files
        Image[] fileName = new Image[files.length];                               //Reads images
        ImageView[] images = new ImageView[files.length];                         //The images

        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            fileName[i] = new Image(files[i].toURI().toString());
            images[i] = new ImageView(fileName[i]);
            String titleName = files[i].getAbsoluteFile().getName();

            images[i].setUserData(titleName);
            images[i].setId(i + "");
            images[i].setOnMouseClicked((event) -> {
                Node tagetPictureSource = (Node) event.getSource();
                String targetImage = images[Integer.parseInt(tagetPictureSource.getId())].getUserData().toString();
                System.out.println(targetImage);
            });
            flow.getChildren().add(images[i]);
        }

        */
}

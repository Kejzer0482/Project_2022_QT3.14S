package presentation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        //Layers
        BorderPane root = new BorderPane();
        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        FlowPane flowMovies = new FlowPane();
        FlowPane flowSeries = new FlowPane();
        ScrollPane scrollMovies = new ScrollPane(flowMovies);
        ScrollPane scrollSeries = new ScrollPane(flowSeries);
        gridPane.add(scrollMovies, 0, 0);         //Adds the scrollpane for movies to column 0, row 0
        gridPane.add(scrollSeries, 0, 1);         //Adds the scrollpane for series to column 0, row 1

        //Styling
        stackPane.setStyle("-fx-background-color: grey");
        stackPane.setMinWidth(300);
        gridPane.setPadding(new Insets(10, 10, 10, 10)); //Creates space between outer edge and gridpane
        gridPane.setVgap(10);                                          //Adds vertical spacing between grid elements
        gridPane.setHgap(10);                                          //Adds Horizontal spacing between grid elements
        gridPane.setStyle("-fx-background-color: black");
        gridPane.setGridLinesVisible(true);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);       //Makes the gridpane take out as much space as it can.
        scrollMovies.setPrefSize(1280, 440);
        scrollSeries.setPrefSize(1280, 440);

        //Menu
        VBox menuList = new VBox();                               //Creating the menu button list
        menuList.setSpacing(30);
        Button homeButton = createMenuButton("Home");        //Calling homemade createButton method
        Button moviesButton = createMenuButton("Movies");
        Button seriesButton = createMenuButton("Series");
        Button favButton = createMenuButton("My List");
        menuList.getChildren().add(homeButton);
        menuList.getChildren().add(moviesButton);
        menuList.getChildren().add(seriesButton);
        menuList.getChildren().add(favButton);
        menuList.setAlignment(Pos.CENTER);                       //Places button in the middle instead of top left
        stackPane.getChildren().add(menuList);                   //Adds menu list to the stackPane

        //FlagLabels (Not part of the actual program, just visual feedback)
        Label movieLabel = new Label("Movies");
        flowMovies.getChildren().add(movieLabel);
        movieLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Label seriesLabel = new Label("Series");
        flowSeries.getChildren().add(seriesLabel);
        seriesLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //Lav flow ud fra platforms movies og series lister. Træk billede fra hvert element og skab ID etc. Ligesom Emil gjorde.

        //Scene
        Scene scene = new Scene(root, 1600, 900);       //Creates scene and sets dimensions of window
        root.setLeft(stackPane);
        root.setCenter(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QT3.14s&Chill");
        primaryStage.show();

        /*
        //Layers
        StackPane root = new StackPane();
        FlowPane flow = new FlowPane();
        ScrollPane scroll = new ScrollPane(flow);
        root.getChildren().add(scroll);

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

        //Styling
        flow.setPrefWrapLength(1200);
        flow.setHgap(10);
        flow.setVgap(10);
        flow.setAlignment(Pos.CENTER);
        flow.setStyle("-fx-background-color: purple");

        //Scene
        Scene scene = new Scene(root, 1220, 900);
        primaryStage.setTitle("QT3.14s");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
    }

    private static Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(200, 70);
        button.setStyle("-fx-background-color: white");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

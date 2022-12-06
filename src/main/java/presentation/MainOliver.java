package presentation;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import data.DataAccess;

import javax.print.attribute.standard.Media;

public class MainOliver extends Application {
    //List<Media> Movies =
    public void start(Stage stage) throws IOException {
        //layers:
        BorderPane root = new BorderPane();
        StackPane stackPane = new StackPane();
        FlowPane flowPane = new FlowPane();
        ScrollPane scrollPane = new ScrollPane(flowPane);
        stackPane.getChildren().add(scrollPane);

        //Styling
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        flowPane.setPrefWrapLength(1200);
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setAlignment(Pos.CENTER);



        //Files:
        File directory = new File("src/main/java/data/filmplakater");  // This is the file directory
        File[] files = directory.listFiles();                              // This retrieves all the files
        Image[] fileName = new Image[files.length];                        // This reads the images
        ImageView[] images = new ImageView[files.length];                  // The actual images

        for(int i = 0; i< files.length ; i++){
            System.out.println(files[i].getName());
            fileName[i] = new Image(files[i].toURI().toString());
            images[i] = new ImageView(fileName[i]);

            flowPane.getChildren().add(images[i]);
        }

        //scene:
        Scene scene = new Scene(root);
        stage.setTitle("QT3.14S NetPlay+");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
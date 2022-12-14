package data;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataAccess {
    private String path;
    //Konstruktør til dataAcces, appointer input til path.
    public DataAccess(String path){
        this.path = path;
    }
    public List<String> loadFile() throws FileNotFoundException {

        List<String> result = new ArrayList<>();

            /*
            Opretter fil via path, og bruger scanner til at gennemløbe filen
            og kopiere den over i en ArrayListe.
             */
            File dataFile = new File(path);
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNextLine()) {
                result.add(fileScanner.nextLine());
            }
            fileScanner.close();


        return result;
    }




}

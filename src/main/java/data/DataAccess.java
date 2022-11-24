package data;

import java.io.*;
import java.util.*;
public class DataAccess {
    private String path;
    //Konstruktør til dataAcces, appointer input til path.
    public DataAccess(String path){
        this.path = path;
    }
    public List<String>  loadFile(){

        List<String> result = new ArrayList<>();
        try {
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
        } catch(FileNotFoundException e){
            //Exception håndtering skal muligvis ske højere oppe.
            System.out.println("Nothing found. File might be empty.");
        }
        return result;
    }

    /*public static void main(String[] args) {
        DataAccess DA = new DataAccess("/Users/ol/Documents/GitHub/GRPRO_Project_QT3.14S/src/Data/film.txt");
        List<String> dataList = DA.loadFile();

        for(String word : dataList){
            System.out.println(word);
        }
    }*/
}

package data;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTest {



    //Tester af datafilen ikke er tom
    @Test
    void ReturnShouldBeNoneEmpty() throws Exception{
        DataAccess da = new DataAccess("src/main/java/data/movies.txt");
        assertNotNull(da.loadFile(), "The file is empty");
        da = null;
    }
    //Tester af der bliver kastet en exception hvis datafilen er tom
    @Test
    void shouldThrowFileNotFoundException(){
        final DataAccess fa = new DataAccess("src/main/java/data/failure.txt");
        Exception thrown = Assertions.assertThrows(FileNotFoundException.class, ()->{
            fa.loadFile();
        });
        Assertions.assertEquals("src/main/java/data/failure.txt (No such file or directory)", thrown.getMessage());

    }
    //Tester af alle 100 linjer af txtfilen er med
    @Test
    void shouldBe100LinesLong() throws Exception{
        DataAccess da = new DataAccess("src/main/java/data/movies.txt");
        List<String> loadlist = da.loadFile();
        assertEquals(100,loadlist.size());

    }
}
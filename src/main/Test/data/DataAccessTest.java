package data;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTest {



    @Test
    void ReturnShouldBeNoneEmpty(){
        DataAccess da = new DataAccess("src/main/java/data/movies.txt");
        assertNotNull(da.loadFile(), "The file is empty");
        da = null;
    }
}
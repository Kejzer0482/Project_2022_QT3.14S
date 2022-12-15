package domain;

import org.junit.Assert;
import org.junit.Before;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.DeletingActiveAccountException;
import presentation.UserAlreadyExistsException;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class PlatformTest {
    Platform p = new Platform();
    User o = new User("Oliver");




    @Test
    void createMediaListsCreatesListsWithAll100MoviesAndSeries() throws FileNotFoundException{
        assertEquals(100, p.getSeriesList().size());
        assertEquals(100, p.getMovieList().size());
    }


    @Test
    void CompleteListShouldContainTitanicandTheWalkingDead() {
        List<Media> testList = p.getCompleteList();
        Media titanic = p.search_function("titanic");
        Media walking = p.search_function("The walking dead");
        assertTrue(testList.contains(titanic));
        assertTrue(testList.contains(walking));
    }
    @Test
    void CompleteListShouldNotContainEmptyMedia() {
        List<Media> testList = p.getCompleteList();
        Media emptySeries = new Series("", "", new ArrayList<String>(), 0.0, new ArrayList<String>(), "");
        Media emptyMovie = new Movie("", "", new ArrayList<String>(), 0.0, "");
        assertFalse(testList.contains(emptySeries));
        assertFalse(testList.contains(emptyMovie));
    }



    @Test
    void specificGenreShouldReturnAListOfMediaThatContainsTheWalkingDead() {
        Media walking = p.search_function("The Walking Dead");
        List<String> genre = walking.getGenres();
        //Checks if The walking dead is present in any genre list that is relevant.
        Boolean contained = null;
        for(int i = 0; i < genre.size() ; i++){
            List<Media> horror = p.specificGenre("series", genre.get(i));
            if(horror.contains(walking)){
                contained = true;
            } else{
                contained = false;
                break;
            }
        }
        assertTrue(contained);

    }

    @Test
    void specificGenreShouldReturnEmptyList(){
        List<Media> danskKrimi = p.specificGenre("series", "Dansk krimi");
        assertEquals(0, danskKrimi.size());
    }
    @Test
    void shouldReturnOliver() throws Exception{
        //Adds user to accountlist of platform
        p.addUser(o.getUsername());

        assertEquals("Oliver", p.getAccount("Oliver").getUsername());
    }
    @Test
    void shouldThrowExceptionUserAlreadyExistsException() throws UserAlreadyExistsException{
        p.addUser(o.getUsername());

        Exception thrown = Assertions.assertThrows(UserAlreadyExistsException.class, ()->{
            p.addUser(o.getUsername());
        });
        Assertions.assertEquals("Username already in use", thrown.getMessage());

    }

    @Test
    void SizeOfAccountListShouldBe1() throws DeletingActiveAccountException, UserAlreadyExistsException {
        p.addUser("Oliver");
        p.setActiveAccount("Oliver");
        p.addUser("Sebastian");
        p.deleteAccount("Sebastian");
        assertEquals(1, p.getAccounts().size());
    }
    @Test
    void shouldThrowDeletingActiveAccountException() throws DeletingActiveAccountException, UserAlreadyExistsException{
        p.addUser("Oliver");
        p.setActiveAccount("Oliver");
        Exception thrown = Assertions.assertThrows(DeletingActiveAccountException.class, ()-> {p.deleteAccount("Oliver");});
        Assertions.assertEquals("Can't delete active account", thrown.getMessage());
    }

    @Test
    void SearchShouldReturnNull() {
        assertNull(p.search_function("The Movie that doesn't exist"));
    }

}
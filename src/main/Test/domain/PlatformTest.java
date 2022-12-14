package domain;

import org.junit.Before;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatformTest {
    Platform p;
    User o = new User("Oliver");
    @Before
    void init(){
        p = new Platform();
        p.createMediaLists();
        p.createUser(o.getUserName());

    }



    @Test
    void createMediaListswithAll400MediaUnits() {
        assertEquals(200, p.getCompleteList().size()+p.getMovieList().size()+p.getSeriesList().size());
    }

    @Test
    void getMovieList() {
    }

    @Test
    void getSeriesList() {
    }

    @Test
    void getCompleteList() {
    }

    @Test
    void specificGenre() {
    }


    @Test
    void getAccount() {
    }

    @Test
    void getAccounts() {
    }

    @Test
    void setActiveAccount() {
    }

    @Test
    void getActiveAccount() {
    }

    @Test
    void addUser() {
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void search_function() {
    }
}
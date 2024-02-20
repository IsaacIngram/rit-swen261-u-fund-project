package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test the Need Controller class
 */
@Tag("Controller-tier")
@SpringBootTest
public class NeedControllerTest {

    private NeedController needController;
    private NeedDAO mockNeedDao;

    /**
     * Before each test, create a new NeedController object and inject a mock
     * Need DAO.
     */
    @BeforeEach
    public void setupNeedController() {
        mockNeedDao = mock(NeedDAO.class);
        needController = new NeedController(mockNeedDao);
    }
    /**
    * 
    * @throws IOException
    */
    @Test
    public void testGetNeedOk() throws IOException{
        Need okNeed = new Need(1, "Darius", "PERSON", 11.0f, 10);
        //Returns the need from that was created instead of reading from DAO file
        when(mockNeedDao.getNeed(1)).thenReturn(okNeed);
        //Creates the response that will be handled through the controler that matches the need in the controler.
        //Doesn't look at the fileDAO that matches the ID, instead matches the id of the need that was created in the test file.
        ResponseEntity<Need> respone = needController.getNeed(1);
        //checks if the response got the need that was grabbed from the controler need created
        assertEquals(okNeed, respone.getBody());
        assertEquals(HttpStatus.OK, respone.getStatusCode());
    }

    /**
     * Test get need when the requested need does not exist in the cupboard
     * @throws IOException
     */
    @Test
    public void testGetNeedNotFound() throws IOException{
        Need notFoundNeed = new Need(2, "TUTU", "THING", 1.3f, 200);
        when(mockNeedDao.getNeed(2)).thenReturn(notFoundNeed);
        ResponseEntity<Need> respone = needController.getNeed(1);
        assertEquals(null, respone.getBody());
        assertEquals(HttpStatus.NOT_FOUND, respone.getStatusCode());

    }

    /**
    * Test get need when the Need DAO throws an IO Exception
    * @throws IOException
    */
    @Test
    public void testGetNeedServerError() throws IOException{
        when(mockNeedDao.getNeed(4)).thenThrow(new IOException());
        ResponseEntity<Need> response = needController.getNeed(4);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Test get needs
     * @throws IOException if the DAO was unable to read the data
     */
    @Test
    public void testGetNeeds() throws IOException {
        // Setup
        Need[] needs = new Need[3];
        needs[0] = new Need(72, "Water", "BEVERAGE", 1.00f, 72);
        needs[1] = new Need(73, "Food", "FOOD", 12.34f, 3);
        needs[2] = new Need(74, "Shelter", "HOME", 1234.56f, 1);
        // Tell the mockDAO to return the needs we created above
        when(mockNeedDao.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    /**
     * Test get needs if the DAO throws an IOException
     * @throws IOException
     */
    @Test
    public void testGetNeedsInternalError() throws IOException {
        // Setup
        // Tell the mockDAO to throw an IOException when getting needs
        when(mockNeedDao.getNeeds()).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Test updating a need when the DAO returns an internal server error
     * @throws IOException
     */
    @Test
    public void testUpdateNeedInternalError() throws IOException {
        // Setup
        Need need = new Need(1, "Water", "BEVERAGE", 1.23f, 10);
        when(mockNeedDao.updateNeed(need)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Need> response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Test updating a need
     */
    @Test
    public void testUpdateNeed() throws IOException {
        // Setup
        Need need = new Need(1, "Water", "BEVERAGE", 1.23f, 10);
        when(mockNeedDao.updateNeed(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * Test updating a need when the need to update does not exist
     */
    @Test
    public void testUpdateNeedNotFound() throws IOException {
        // Setup
        Need need = new Need(1, "Water", "BEVERAGE", 1.23f, 10);
        when(mockNeedDao.updateNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchNeed() throws IOException {
        // Setup
        String search_string = "e";
        Need[] needs = {
                new Need(1, "Water", "BEVERAGE", 3.21f, 10),
                new Need(3, "Shelter", "HOUSE", 1234.56f, 1)
        };
        when(mockNeedDao.findNeeds(search_string)).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(search_string);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(needs[0], response.getBody()[0]);
        assertEquals(needs[1], response.getBody()[1]);
    }

    /**
     * Test searching for a need when the search result will contain no needs
     */
    @Test
    public void testSearchNeedNoResults() throws IOException {
        // Setup
        Need[] needs = {};
        String search_string = "specific string";
        when(mockNeedDao.findNeeds(search_string)).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(search_string);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }

    /**
     * Test searching for a need when there is an internal error with the need DAO
     */
    @Test
    public void testSearchNeedsInternalError() throws IOException {
        // Setup
        String search_string = "specific string";
        when(mockNeedDao.findNeeds(search_string)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(search_string);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}

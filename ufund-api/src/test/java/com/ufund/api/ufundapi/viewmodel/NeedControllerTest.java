package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

/**
 * Test the Need Controller class
 */
@Tag("Controller-tier")
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
    public void testCreateNeedOk() throws IOException{
        Need okNeed = new Need(1, "Food", 13.0f, 10);

        //Creates the response that will be handled through the controler that matches the need in the controler.
        //Doesn't look at the fileDAO that matches the ID, instead matches the id of the need that was created in the test file.
        ResponseEntity<Need> response = needController.createNeed(okNeed);
        //checks if the response got the need that was grabbed from the controler need created
        assertEquals(okNeed, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * 
     * @throws IOException
     */
    @Test
    public void testCreateNeedNull() throws IOException{

        ResponseEntity<Need> respone = needController.createNeed(null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respone.getStatusCode());
    }
    /**
    * 
    * @throws IOException
    */
    @Test
    public void testCreateNeedServerError() throws IOException{
        Need serverError = new Need(3, "New_Need", 2.78f, 13);
        
        when(mockNeedDao.createNeed(serverError)).thenThrow(new IOException());

        ResponseEntity<Need> response = needController.createNeed(serverError);
        assertEquals(serverError, response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

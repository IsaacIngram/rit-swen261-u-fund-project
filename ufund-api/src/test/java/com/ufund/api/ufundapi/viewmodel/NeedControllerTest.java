package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    public void testDeleteNeedOk() throws IOException{
        Need okNeed = new Need(1, "Food", 13.0f, 10);

        when(mockNeedDao.deleteNeed(1)).thenReturn(true);
        //Creates the response that will be handled through the controler that matches the need in the controler.
        //Doesn't look at the fileDAO that matches the ID, instead matches the id of the need that was created in the test file.
        ResponseEntity<Need> response = needController.deleteNeed(1);
        //checks if the response got the need that was grabbed from the controler need created
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * 
     * @throws IOException
     */
    @Test
    public void testDeleteNeedNotFound() throws IOException{
        Need notFoundNeed = new Need(2, "Stuff", 1.5f, 100);

        when(mockNeedDao.deleteNeed(1)).thenReturn(false);
        ResponseEntity<Need> response = needController.deleteNeed(1);
        assertNotEquals(false, response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    /**
    * 
    * @throws IOException
    */
    @Test
    public void testDeleteNeedServerError() throws IOException{
        Need serverError = new Need(3, "New_Need", 2.78f, 13);
        
        when(mockNeedDao.deleteNeed(4)).thenThrow(new IOException());

        ResponseEntity<Need> response = needController.deleteNeed(4);
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}

package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.persistence.NeedDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import com.ufund.api.ufundapi.model.Need;

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
    public void testGetNeedOk() throws IOException{
        Need okNeed = new Need(1, "Darius", 11.0f, 10);
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
     * 
     * @throws IOException
     */
    @Test
    public void testGetNeedNotFound() throws IOException{
        Need notFoundNeed = new Need(2, "TUTU", 1.3f, 200);
        when(mockNeedDao.getNeed(1)).thenReturn(notFoundNeed);
        ResponseEntity<Need> respone = needController.getNeed(1);
        assertEquals(notFoundNeed, null);
        assertEquals(HttpStatus.NOT_FOUND, respone.getStatusCode());

    }
    /**
    * 
    * @throws IOException
    */
    @Test
    public void testGetNeedServerError() throws IOException{
        Need serverError = new Need(3, "Mihou", 2.78f, 13);
        when(mockNeedDao.getNeed(4)).thenReturn(serverError);
        ResponseEntity<Need> response = needController.getNeed(4);
        assertEquals(serverError, response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

}

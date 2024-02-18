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
        needs[0] = new Need(72, "Water", 1.00f, 72);
        needs[1] = new Need(73, "Food", 12.34f, 3);
        needs[2] = new Need(74, "Shelter", 1234.56f, 1);
        // Tell the mockDAO to return the needs we created above
        when(mockNeedDao.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

}

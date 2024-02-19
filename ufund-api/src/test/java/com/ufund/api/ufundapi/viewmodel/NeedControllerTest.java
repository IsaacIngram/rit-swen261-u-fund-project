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

    @Test
    public void testFindNeedOk() throws IOException{
        Need need1 = new Need(1, "Darius", 2.6f, 1);
        Need need2 = new Need(2, "Mimi", 0.8f, 1);
        Need need3 = new Need(3, "Darius", 0.8f, 1);
        Need[] arr = new Need[5];
        arr[0] = need1;
        arr[1] = need2;
        arr[2] = need3;
        when(mockNeedDao.findNeeds("Darius")).thenReturn(arr);
        ResponseEntity<Need[]> response = needController.searchNeeds("Darius");
        assertEquals(arr, response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

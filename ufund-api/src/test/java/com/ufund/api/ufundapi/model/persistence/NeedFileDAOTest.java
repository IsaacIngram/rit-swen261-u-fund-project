package com.ufund.api.ufundapi.model.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the Need File DAO class.
 */
@Tag("Persistence-tier")
@SpringBootTest
public class NeedFileDAOTest {

    NeedFileDAO needFileDao;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, create and inject a mock ObjectMapper to isolate the
     * test from the actual filesystem.
     */
    @BeforeEach
    public void setupNeedFileDao() throws IOException {
        // Random data that makes up the mock datafile
        testNeeds = new Need[3];
        testNeeds[0] = new Need(66, "Water", 1.00f, 72);
        testNeeds[1] = new Need(67, "Food", 12.34f, 3);
        testNeeds[2] = new Need(68, "Shelter", 1234.56f, 1);

        // Create a mock ObjectMapper that reads from the mock data above
        mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.readValue(new File("anything.txt"), Need[].class))
                .thenReturn(testNeeds);
        needFileDao = new NeedFileDAO("anything.txt", mockObjectMapper);
    }

    @Test
    public void testCreateNeed(){
        try {
            Need need = needFileDao.createNeed(new Need(69, "Sixty-Nine", 69.69f, 69));
            assertEquals(new Need(69, "Sixty-Nine", 69.69f, 69), need);
        } catch (Exception e) {
            assertEquals(0, 1);
        }
        
        
        
    }
}

package com.ufund.api.ufundapi.model.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the Need File DAO class.
 */
@Tag("Persistence-tier")
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
        testNeeds[0] = new Need(72, "Water", 1.00f, 72);
        testNeeds[1] = new Need(73, "Food", 12.34f, 3);
        testNeeds[2] = new Need(74, "Shelter", 1234.56f, 1);

        // Create a mock ObjectMapper that reads from the mock data above
        mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.readValue(new File("anything.txt"), Need[].class))
                .thenReturn(testNeeds);
        needFileDao = new NeedFileDAO("anything.txt", mockObjectMapper);
    }

}

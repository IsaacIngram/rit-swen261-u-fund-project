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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the Need File DAO class.
 */
@Tag("Persistence-tier")
@SpringBootTest
public class NeedFileDAOTest {

    NeedFileDAO needFileDao;
    NeedFileDAO emptyNeedFileDao;
    Need[] testNeeds;
    Need[] emptyNeeds;
    ObjectMapper mockObjectMapper;
    ObjectMapper mockEmptyObjectMapper;

    /**
     * Before each test, create and inject a mock ObjectMapper to isolate the
     * test from the actual filesystem.
     */
    @BeforeEach
    public void setupNeedFileDao() throws IOException {
        // Random data that makes up the mock datafile
        testNeeds = new Need[3];
        testNeeds[0] = new Need(72, "Water", "BEVERAGE", 1.00f, 72);
        testNeeds[1] = new Need(73, "Food", "FOOD", 12.34f, 3);
        testNeeds[2] = new Need(74, "Shelter", "HOME", 1234.56f, 1);

        // Create a mock ObjectMapper that reads from the mock data above
        mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.readValue(new File("anything.txt"), Need[].class))
                .thenReturn(testNeeds);
        needFileDao = new NeedFileDAO("anything.txt", mockObjectMapper);

        // Create an empty mock DAO and mock datafile
        emptyNeeds = new Need[0];
        mockEmptyObjectMapper = mock(ObjectMapper.class);
        when(mockEmptyObjectMapper.readValue(new File("anything.txt"), Need[].class))
                .thenReturn(emptyNeeds);
        emptyNeedFileDao = new NeedFileDAO("anything.txt", mockEmptyObjectMapper);
    }

    @Test
    public void testDeleteNeed() {
        try {
            assertEquals(true, needFileDao.deleteNeed(74));
            assertEquals(false, needFileDao.deleteNeed(74));
        } catch (Exception e) {
            assertEquals(0, 1);
        }
    }
        
        
    public void testCreateNeed() {
        try {
            Need need = needFileDao.createNeed(new Need(69, "Water", "BEVERAGE", 69.69f, 69));
            assertEquals(new Need(69, "Water", "BEVERAGE", 69.69f, 69), need);
        } catch (Exception e) {
            assertEquals(0, 1);
        }
    }

    /**
     * Test searching needs
     */
    @Test
    public void testSearchNeeds(){
        // Setup
        String search_string = "e";

        // Invoke
        Need[] result = needFileDao.findNeeds(search_string);

        // Analyze
        assertEquals(2, result.length);
        assertEquals(testNeeds[0], result[0]);
        assertEquals(testNeeds[2], result[1]);
    }

    /**
     * Test searching needs when the search query matches no Need
     */
    @Test
    public void testSearchNeedsNoneExist() throws IOException {
        // Setup
        String search_string = "specific string";

        // Invoke
        Need[] result = needFileDao.findNeeds(search_string);

        // Analyze
        assertEquals(0, result.length);
    }

    /**
     * Test getting a Need
     */
    @Test
    public void testGetNeed(){
        for (Need testNeed : testNeeds) {
            // Invoke
            Need response = needFileDao.getNeed(testNeed.getId());

            // Analyze
            assertEquals(testNeed, response);
        }
    }

    /**
     * Test getting a Need that does not exist
     */
    @Test
    public void testGetNeedNotFound() {
        // Setup
        int id = 1;

        // Invoke
        Need response = needFileDao.getNeed(id);

        // Analyze
        assertNull(response);
    }

    /**
     * Test getting multiple Needs
     */
    @Test
    public void testGetNeeds() throws IOException {
        // Invoke
        Need[] needs = needFileDao.getNeeds();

        // Analyze
        assertEquals(testNeeds.length, needs.length);
        for(int i = 0; i < testNeeds.length; i++) {
            assertEquals(needs[i], testNeeds[i]);
        }
    }

    /**
     * Test getting multiple Needs when the needs file is empty
     */
    @Test
    public void testGetNeedsWhenEmpty() throws IOException {
        // Invoke
        Need[] needs = emptyNeedFileDao.getNeeds();

        // Analyze
        assertEquals(0, emptyNeeds.length);
    }

    /**
     * Test updating a need
     */
    @Test
    public void testUpdateNeed() throws IOException {
        // Setup
        Need need = new Need(72, "Ice", "H2O", 3.78f, 2);

        // Invoke
        Need result = needFileDao.updateNeed(need);

        // Analyze
        assertEquals(need, result);
    }

    /**
     * Test updating a need that does not exist
     */
    @Test
    public void testUpdateNeedNotFound() throws IOException {
        // Setup
        Need need = new Need(1, "Air", "H2O", 0.00f, 1000);

        // Invoke
        Need result = needFileDao.updateNeed(need);

        // Analyze
        assertNull(result);
    }
}

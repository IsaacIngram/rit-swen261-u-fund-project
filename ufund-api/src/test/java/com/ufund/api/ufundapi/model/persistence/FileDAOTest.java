package com.ufund.api.ufundapi.model.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Credential;
import com.ufund.api.ufundapi.model.Need;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the Need File DAO class.
 */
@Tag("Persistence-tier")
@SpringBootTest
public class FileDAOTest {

    FileDAO fileDao;
    FileDAO emptyFileDao;

    Need[] testNeeds;
    Need[] emptyNeeds;

    Credential[] testCredentials;
    Credential[] emptyCredentials;

    ObjectMapper mockNeedObjectMapper;
    ObjectMapper mockEmptyNeedObjectMapper;
    ObjectMapper mockAuthObjectMapper;
    ObjectMapper mockEmptyAuthObjectMapper;

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

        testCredentials = new Credential[3];
        testCredentials[0] = new Credential("admin", "admin");
        testCredentials[1] = new Credential("a", "a");
        testCredentials[2] = new Credential("b", "b");

        // Create a mock ObjectMapper that reads from the mock data above
        mockNeedObjectMapper = mock(ObjectMapper.class);
        mockAuthObjectMapper = mock(ObjectMapper.class);
        when(mockNeedObjectMapper.readValue(new File("anything.txt"), Need[].class))
                .thenReturn(testNeeds);
        when(mockAuthObjectMapper.readValue(new File("anything.txt"), Credential[].class))
                .thenReturn(testCredentials);
        fileDao = new FileDAO("anything.txt", mockNeedObjectMapper, "anything.txt", mockAuthObjectMapper);

        // Create an empty mock DAO and mock datafile
        emptyNeeds = new Need[0];
        emptyCredentials = new Credential[0];
        mockEmptyNeedObjectMapper = mock(ObjectMapper.class);
        mockEmptyAuthObjectMapper = mock(ObjectMapper.class);
        when(mockEmptyNeedObjectMapper.readValue(new File("anything.txt"), Need[].class))
                .thenReturn(emptyNeeds);
        when(mockEmptyAuthObjectMapper.readValue(new File("anything.txt"), Credential[].class))
                .thenReturn(emptyCredentials);
        emptyFileDao = new FileDAO("anything.txt", mockEmptyNeedObjectMapper, "anything.txt", mockEmptyAuthObjectMapper);
    }

    @Test
    public void testDeleteNeed() throws IOException {
        // Setup
        int need_to_delete = 72;

        // Invoke
        boolean result = fileDao.deleteNeed(need_to_delete);

        // Analyze
        assertTrue(result);
    }

    @Test
    public void testDeleteNeedDoesNotExist() throws IOException {
        // Setup
        int need_to_delete = 72;

        // Invoke
        boolean result = emptyFileDao.deleteNeed(need_to_delete);

        // Analyze
        assertFalse(result);
    }
        
    @Test
    public void testCreateNeed() throws IOException {
        // Setup
        Need need = new Need(0, "Water", "BEVERAGE", 1.10f, 20);

        // Invoke
        Need result = fileDao.createNeed(need);

        // Analyze
        assertEquals(result, need);
    }

    @Test
    public void testCreateNeedAlreadyExists() throws IOException {
        // Setup
        Need need = new Need(72, "Water", "BEVERAGE", 1.10f, 20);

        // Invoke
        Need result = fileDao.createNeed(need);

        // Analyze
        assertNull(result);
    }

    /**
     * Test searching needs
     */
    @Test
    public void testSearchNeeds(){
        // Setup
        String search_string = "e";

        // Invoke
        Need[] result = fileDao.findNeeds(search_string);

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
        Need[] result = fileDao.findNeeds(search_string);

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
            Need response = fileDao.getNeed(testNeed.getId());

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
        Need response = fileDao.getNeed(id);

        // Analyze
        assertNull(response);
    }

    /**
     * Test getting multiple Needs
     */
    @Test
    public void testGetNeeds() throws IOException {
        // Invoke
        Need[] needs = fileDao.getNeeds();

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
        Need[] needs = emptyFileDao.getNeeds();

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
        Need result = fileDao.updateNeed(need);

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
        Need result = fileDao.updateNeed(need);

        // Analyze
        assertNull(result);
    }

    @Test
    public void testCreateCredential() throws IOException {
        // Setup
        Credential credential = new Credential("jeff", "ffffff");
        // Invoke
        Credential result = fileDao.createCredential(credential);

        // Analyze
        assertEquals(credential, result);
    }

    @Test
    public void testCreateCredentialAlreadyExists() throws IOException {
        // Setup
        Credential credential = new Credential("admin", "ffffff");
        // Invoke
        Credential result = fileDao.createCredential(credential);

        // Analyze
        assertNull(result);
    }

    @Test
    public void testUpdateCredential() throws IOException {
        // Setup
        Credential credential = new Credential("a", "ffffff");

        // Invoke
        Credential result = fileDao.updateCredential(credential);

        // Analyze
        assertEquals(credential, result);
    }

    @Test
    public void testUpdateCredentialDoesNotExist() throws IOException {
        // Setup
        Credential credential = new Credential("jeff", "ffffff");

        // Invoke
        Credential result = fileDao.updateCredential(credential);

        // Analyze
        assertNull(result);
    }

    @Test
    public void testCompareCredentialCorrect() throws IOException {
        // Setup
        Credential credential = new Credential("admin", "admin");

        // Invoke
        boolean result = fileDao.compareCredential(credential);

        // Analyze
        assertTrue(result);
    }

    @Test
    public void testCompareCredentialIncorrect() throws IOException {
        // Setup
        Credential credential = new Credential("admin", "test");

        // Invoke
        boolean result = fileDao.compareCredential(credential);

        // Analyze
        assertFalse(result);
    }
    
    @Test
    public void testCompareCredentialDoesNotExist() throws IOException {
        // Setup
        Credential credential = new Credential("jeff", "test");

        // Invoke
        boolean result = fileDao.compareCredential(credential);

        // Analyze
        assertFalse(result);
    }

    @Test
    public void testDeleteCredential() throws IOException {
        // Setup
        String user = "a";

        // Invoke
        boolean result = fileDao.deleteCredential(user);

        // Analyze
        assertTrue(result);
    }

    @Test
    public void testDeleteCredentialDoesNotExist() throws IOException {
        // Setup
        String user = "affffff";

        // Invoke
        boolean result = fileDao.deleteCredential(user);

        // Analyze
        assertFalse(result);
    }

    @Test
    public void testGetCredential() throws IOException {
        // Setup
        String user = "admin";
        Credential expected = new Credential("admin", "admin");

        // Invoke
        Credential result = fileDao.getCredential(user);

        // Analyze
        assertEquals(expected, result);
    }

    @Test
    public void testGetCredentialDoesNotExist() throws IOException {
        // Setup
        String user = "jeff";
        Credential expected = new Credential("", "");

        // Invoke
        Credential result = fileDao.getCredential(user);

        // Analyze
        assertEquals(expected, result);
    }

}

package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Credential;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.AuthenticationDAO;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test the Authentication Controller class
 */
@Tag("Controller-tier")
@SpringBootTest
public class AuthenticationControllerTest {

    private AuthenticationController authenticationController;
    private AuthenticationDAO mockAuthenticationDao;

    /**
     * Before each test, create a new AuthenticationController object and inject a mock
     * Authentication DAO.
     */
    @BeforeEach
    public void setupAuthenticationController() {
        mockAuthenticationDao = mock(AuthenticationDAO.class);
        authenticationController = new AuthenticationController(mockAuthenticationDao);
    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testCreateCredentialOk() throws IOException{
        Credential credential = new Credential("joe", "john");
        when(mockAuthenticationDao.createCredential(credential)).thenReturn(credential);
        ResponseEntity<Credential> response = authenticationController.createCredential(credential);
        assertEquals(credential, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    /** 
    * @throws IOException
    */
    @Test
    public void testCreateCredentialConflict() throws IOException{
        ResponseEntity<Credential> response = authenticationController.createCredential(null);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /** 
    * @throws IOException
    */
    @Test
    public void testCreateCredentialError() throws IOException{
        Credential credential = new Credential("joe", "john");
        when(mockAuthenticationDao.createCredential(credential)).thenThrow(new IOException());
        ResponseEntity<Credential> response = authenticationController.createCredential(credential);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

   /**
    * 
    * @throws IOException
    */
    @Test
    public void testUpdateCredentialOk() throws IOException{
        Credential credential = new Credential("joe", "john");
        when(mockAuthenticationDao.updateCredential(credential)).thenReturn(credential);
        ResponseEntity<Credential> response = authenticationController.updateCredential(credential);
        assertEquals(credential, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testUpdateCredentialConflict() throws IOException{
        ResponseEntity<Credential> response = authenticationController.updateCredential(null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testUpdateCredentialError() throws IOException{
        Credential credential = new Credential("joe", "john");
        when(mockAuthenticationDao.updateCredential(credential)).thenThrow(new IOException());
        ResponseEntity<Credential> response = authenticationController.updateCredential(credential);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testDeleteCredentialOk() throws IOException{
        String user = "john";
        when(mockAuthenticationDao.deleteCredential(user)).thenReturn(true);
        ResponseEntity<Credential> response = authenticationController.deleteCredential(user);
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testDeleteCredentialConflict() throws IOException{
        String user = "john";
        when(mockAuthenticationDao.deleteCredential(user)).thenReturn(false);
        ResponseEntity<Credential> response = authenticationController.deleteCredential(user);
        assertNotEquals(false, response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testDeleteCredentialError() throws IOException{
        String user = "john";
        when(mockAuthenticationDao.deleteCredential(user)).thenThrow(new IOException());
        ResponseEntity<Credential> response = authenticationController.deleteCredential(user);
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testGetCredentialOk() throws IOException{
        String user = "john";
        Credential credential = new Credential("john", "apple");
        when(mockAuthenticationDao.getCredential(user)).thenReturn(credential);
        ResponseEntity<Credential> response = authenticationController.getCredential(user);
        assertEquals(credential, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
    * 
    * @throws IOException
    */
    @Test
    public void testGetCredentialError() throws IOException{
        String user = "john";
        Credential credential = new Credential("john", "apple");
        when(mockAuthenticationDao.getCredential(user)).thenThrow(new IOException());
        ResponseEntity<Credential> response = authenticationController.getCredential(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}



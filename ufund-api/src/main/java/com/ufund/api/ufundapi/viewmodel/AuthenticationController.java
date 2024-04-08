package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Credential;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.AuthenticationDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private static final Logger LOG = Logger.getLogger(NeedController.class.getName());
    private AuthenticationDAO authDAO;

    /**
     * Creates a REST API controller to respond to REST requests for Authentication
     * @param authDAO The AuthenticationDAO responsible for performing CRUD operations
     * This dependency is injected by the Sprint framework.
     */
    public AuthenticationController(AuthenticationDAO authDAO) {
        this.authDAO = authDAO;
    }

    /**
     * Create a credential
     * @param credential credential to create
     * @return ResponseEntity with HTTP Status of OK if the creating was successful, CONFLIC if there
     * was a conflict, and INTERNAL_SERVER_ERROR if an error was encountered
     */
    @PostMapping("")
    public ResponseEntity<Credential> createCredential(@RequestBody Credential credential) {
        LOG.info("POST /auth" + credential);

        try {
            Credential result = authDAO.createCredential(credential);
            if(result == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<Credential>(result, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update a credential
     * @param credential credential to update
     * @return ResponseEntity with HTTP status of OK if successful, NOT_FOUND if the credential
     * was not found, and INTERNAL_SERVER_ERROR if an error was encountered
     */
    @PutMapping("")
    public ResponseEntity<Credential> updateCredential(@RequestBody Credential credential) {
        LOG.info("PUT /auth");

        try {
            Credential result = authDAO.updateCredential(credential);
            if(result == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Credential>(result, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a credential with the given user
     * @param user a user
     * @return ResponseEntity with HTTP status of OK if successful, NOT_FOUND if the credential
     * was not found, and INTERNAL_SERVER_ERROR if an error was encountered.
     */
    @DeleteMapping("/{user}")
    public ResponseEntity<Credential> deleteCredential(@PathVariable String user) {
        LOG.info("DELETE /user " + user);
        try {
            boolean result = authDAO.deleteCredential(user);
            if(result) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{user}")
    public ResponseEntity<Credential> getCredential(@PathVariable String user) {
        LOG.info("GET /user/" + user);
        try {
            Credential result = authDAO.getCredential(user);
            return new ResponseEntity<Credential>(result, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

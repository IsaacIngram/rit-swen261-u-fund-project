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


    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody Credential credential) {
        LOG.info("LOGIN (POST /auth/login) " + credential.getUsername());

        try {
            boolean credentialsMatch = authDAO.compareCredential(credential);
            if(credentialsMatch) {
                // Credentials accepted
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // Credentials not accepted
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @PutMapping("")
    public ResponseEntity<Credential> updateCredential(@RequestBody Credential credential) {
        return null;
    }

}

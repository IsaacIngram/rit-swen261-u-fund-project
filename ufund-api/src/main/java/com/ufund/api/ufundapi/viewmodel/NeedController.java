package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/needs")
public class NeedController {
    private static final Logger LOG = Logger.getLogger(NeedController.class.getName());
    private NeedDAO needDao;

    /**
     * Create a REST API controller to respond to REST requests
     * @param needDao The NeedDAO (data access objects) responsible for
     * performing CRUD operations
     * This dependency is injected by the Spring framework.
     */
    public NeedController(NeedDAO needDao) {
        this.needDao = needDao;
    }

    /**
     * Deletes a {@linkplain Need} with the given id
     *
     * @param id The id of the {@link Need} to deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /needs/" + id);

        try {
            boolean result = needDao.deleteNeed(id);
            if(result) {
                // Need was deleted
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // ID did not exist
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Need need} with the provided need object
     *
     * @param need - The {@link Need need} to create
     *
     * @return ResponseEntity with created {@link Need need} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        LOG.info("POST /needs " + need);

        try {
            Need result = needDao.createNeed(need);
            // I think this is supposed to be a null check? Docstring for createNeed
            // says it returns "false" if creating a need wasn't successful, but that
            // obviously isn't true because it returns a Need object...
            if(result == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<Need>(result, HttpStatus.OK);
            }
        } catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Need needs} whose name contains
     * the text in name
     * @param name The name parameter which contains the text used to find the {@link Need needs}
     * @return ResponseEntity with array of {@link Need hero} objects (may be empty) and
     * HTTP status of OK
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * Example: Find all needs that contain the text "ma"
     * GET http://localhost:8080/needs/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam String name) {
        LOG.info("GET /needs/?name=" + name);

        try {
            Need[] result = needDao.findNeeds(name);
            return new ResponseEntity<Need[]>(result, HttpStatus.OK);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Need need} for the given id
     *
     * @param id The id used to locate the {@link Need hero}
     *
     * @return ResponseEntity with {@link Need hero} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        LOG.info("GET /needs/" + id);
        try {
            Need result = needDao.getNeed(id);
            if (result != null)
                return new ResponseEntity<Need>(result, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to a GET request for all Needs
     * @return ResponseEntity with array of Need objects (maybe empty) and an
     * and HTTP status of OK. ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR
     * otherwise
     */
    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds() {
        LOG.info("GET /needs");
        try {
            Need[] needs = needDao.getNeeds();
            return new ResponseEntity<Need[]>(needs, HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the need with the provided object if it exists
     * @param need The need to update
     * @return ResponseEntity with updated Need object and HTTP status of OK if
     * updated. ResponseEntity with HTTP status of NOT_FOUND if not found.
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /needs " + need);

        try {
            Need result = needDao.updateNeed(need);
            if(result == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Need>(result, HttpStatus.OK);
            }
        } catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

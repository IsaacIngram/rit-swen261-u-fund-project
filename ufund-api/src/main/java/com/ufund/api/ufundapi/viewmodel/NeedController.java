package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("needs")
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

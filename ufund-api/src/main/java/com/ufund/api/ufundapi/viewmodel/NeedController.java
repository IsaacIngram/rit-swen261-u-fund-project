package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.persistence.NeedDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                // Hero was deleted
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
}

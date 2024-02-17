package com.ufund.api.ufundapi.viewmodel;

import com.ufund.api.ufundapi.model.persistence.NeedDAO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

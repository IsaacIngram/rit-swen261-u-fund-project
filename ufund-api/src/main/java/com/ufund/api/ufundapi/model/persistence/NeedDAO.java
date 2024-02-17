package com.ufund.api.ufundapi.model.persistence;

import com.ufund.api.ufundapi.model.Need;

import java.io.IOException;

public interface NeedDAO {

    /**
     * Retrieves all Needs
     * @return An array of Needs, may be empty
     * @throws IOException If an issue with underlying storage
     */
    Need[] getNeeds() throws IOException;

}

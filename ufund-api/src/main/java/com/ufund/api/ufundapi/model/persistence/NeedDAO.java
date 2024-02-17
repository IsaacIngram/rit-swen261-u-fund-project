package com.ufund.api.ufundapi.model.persistence;

import com.ufund.api.ufundapi.model.Need;

import java.io.IOException;

public interface NeedDAO {

    /**
     * Updates and saves a Need
     * @param need Need to be updated and saved
     * @return Updated Need if successful, null if it could not be found
     * @throws IOException If underlying storage can not be accessed
     */
    Need updateNeed(Need need) throws IOException;

}

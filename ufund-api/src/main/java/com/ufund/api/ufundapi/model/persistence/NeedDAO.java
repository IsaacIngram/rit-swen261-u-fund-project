package com.ufund.api.ufundapi.model.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Need;

public interface NeedDAO {
    /**
     * Creates and saves a {@linkplain Need need}
     *
     * @param need {@linkplain Need need} object to be created and saved
     * <br>
     * The id of the need object is ignored and a new unique id is assigned
     *
     * @return new {@link Need need} if successful, false otherwise
     *
     * @throws IOException if an issue with underlying storage
     */
    Need createNeed(Need need) throws IOException;
}

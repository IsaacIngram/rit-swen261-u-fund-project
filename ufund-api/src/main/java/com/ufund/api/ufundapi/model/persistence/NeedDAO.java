package com.ufund.api.ufundapi.model.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

public interface NeedDAO {
    /**
     * Deletes a {@linkplain Need need} with the given id
     *
     * @param id The id of the {@link Need need}
     *
     * @return true if the {@link Need need} was deleted
     * <br>
     * false if need with the given id does not exist
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteNeed(int id) throws IOException;
}

package com.ufund.api.ufundapi.model.persistence;

public interface NeedDAO {
    /**
     * Retrieves a {@linkplain Need need} with the given id
     * @param id The id of the {@link Need need} to get
     * @return a {@link Need need} object with the matching id
     * null if no {@link Need need} with a matching id is found
     * @throws IOException if an issue with underlying storage
     */
    Need getNeed(int id) throws IOException;
}

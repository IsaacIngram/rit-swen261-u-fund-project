package com.ufund.api.ufundapi.model.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

public interface NeedDAO {
    /**
     * Finds all {@linkplain Need needs} whose name contains the given text
     * @param containsText The text to match against
     * @return An array of {@link Need needs} whose needs contains the given text, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Need[] findNeeds(String containsText) throws IOException;
}

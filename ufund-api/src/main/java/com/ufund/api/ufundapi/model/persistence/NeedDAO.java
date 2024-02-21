package com.ufund.api.ufundapi.model.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Need;
import java.io.IOException;

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

    /**
     * Retrieves a {@linkplain Need need} with the given id
     * @param id The id of the {@link Need need} to get
     * @return a {@link Need need} object with the matching id
     * null if no {@link Need need} with a matching id is found
     * @throws IOException if an issue with underlying storage
     */
    Need getNeed(int id) throws IOException;

    /**
     * Retrieves all Needs
     * @return An array of Needs, may be empty
     * @throws IOException If an issue with underlying storage
     */
    Need[] getNeeds() throws IOException;

    /**
     * Updates and saves a Need
     * @param need Need to be updated and saved
     * @return Updated Need if successful, null if it could not be found
     * @throws IOException If underlying storage can not be accessed
     */
    Need updateNeed(Need need) throws IOException;

    /**
     * Finds all {@linkplain Need needs} whose name contains the given text
     * @param containsText The text to match against
     * @return An array of {@link Need needs} whose needs contains the given text, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Need[] findNeeds(String containsText) throws IOException;

}

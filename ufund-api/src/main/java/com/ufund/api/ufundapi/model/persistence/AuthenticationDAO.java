package com.ufund.api.ufundapi.model.persistence;

import com.ufund.api.ufundapi.model.Credential;

import java.io.IOException;

public interface AuthenticationDAO {

    /**
     * Creates and saves a Credential
     * @param credential Credential object to be created and saved
     * @return new Credential if successful, Null otherwise
     * @throws IOException If underlying storage cannot be accessed
     */
    Credential createCredential(Credential credential) throws IOException;

    /**
     * Update a credential
     * @param credential Credential object to update
     * @return The Credential after saving if successful, Null otherwise
     * @throws IOException If underlying storage cannot be accessed
     */
    Credential updateCredential(Credential credential) throws IOException;

    /**
     * Check if the given credential matches what is in the file for it
     * @param credential Credential object with username/password combo to compare
     * @return True if it matches what is in the datafile, false if it doesn't
     * @throws IOException If underlying storage cannot be accessed
     */
    boolean compareCredential(Credential credential) throws IOException;

    /**
     * Delete the given credential if it exists
     * @param user user to delete
     * @return True if the deletion was successful, false otherwise
     * @throws IOException If underlying storage cannot be accessed
     */
    boolean deleteCredential(String user) throws IOException;


    Credential getCredential(String user) throws  IOException;

}

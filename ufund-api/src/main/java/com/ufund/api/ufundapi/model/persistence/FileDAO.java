package com.ufund.api.ufundapi.model.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.ufund.api.ufundapi.model.Credential;
import com.ufund.api.ufundapi.model.Need;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@Component
public class FileDAO implements NeedDAO, AuthenticationDAO {

    Map<Integer, Need> needs; // Local cache of needs, so we don't need to read/write file constantly
    Map<String, Credential> credentials; // Local cache of credentials so we don't need to read/write file constantly
    private ObjectMapper needObjectMapper; // Conversion between Need and JSON text for writing to file
    private ObjectMapper credentialObjectMapper; // Conversion between Credential and JSON text for writing to file

    private static int needNextId;

    private String needFilePath; // File path to read/write data to
    private String credentialFilePath; // File path to read/write credential data to

    public FileDAO(
            @Value("${needs.file}") String needFilePath,
            ObjectMapper needObjectMapper,
            @Value("data/credentials.json") String credentialFilePath,
            ObjectMapper credentialObjectMapper
    ) throws IOException {
        this.needFilePath = needFilePath;
        this.needObjectMapper = needObjectMapper;
        this.credentialFilePath = credentialFilePath;
        this.credentialObjectMapper = credentialObjectMapper;
        load();
    }

    /**
     * Loads Needs from the JSON file into the map and sets
     * the nextID to one more than the greatest ID found in the JSON file
     * @return Boolean: True if the file was read successfully
     * @throws IOException When the file cannot be accessed or read
     */
    private boolean load() throws IOException {

        // LOAD NEEDS
        needs = new TreeMap<>();
        needNextId = 0;
        Need[] needArray;
        // Attempt to read file
        try {
            needArray = needObjectMapper.readValue(new File(needFilePath), Need[].class);
        } catch(EOFException | MismatchedInputException e) {
            // Handle case where file is empty
            needArray = new Need[0];
        }
        // Check if the JSON we read in actually contains data before trying
        // to run a for loop on it
        if(needArray.length != 0) {
            for (Need need: needArray) {
                needs.put(need.getId(), need);
                if(need.getId() > needNextId)
                    needNextId = need.getId();
            }
            ++needNextId;
        }


        // LOAD CREDENTIALS
        credentials = new TreeMap<>();
        Credential[] credentialArray;
        try {
            credentialArray = credentialObjectMapper.readValue(new File(credentialFilePath), Credential[].class);
        } catch (EOFException | MismatchedInputException e) {
            credentialArray = new Credential[0];
        }
        // Check if the JSON we read in actually contains data before trying
        // to run a for loop on it
        for (Credential credential : credentialArray) {
            credentials.put(credential.getUsername(), credential);
        }

        return true;
    }

    /**
     * Generate the next ID for a new {Need}
     * @return Integer next id
     */
    private synchronized static int nextNeedId() {
        int id = needNextId;
        ++needNextId;
        return id;
    }

    /**
     * Generates an array of Needs from the tree map
     * @return The array of needs, may be empty
     */
    private Need[] getNeedsArray() {
        return getNeedsArray(null);
    }

    /**
     * Generates an array of needs from the tree map for any needs that contain
     * the text specified by contains text.
     * If contains text is null, the array contains all the Needs in the tree map
     * @param containsText String contains text
     * @return An array of Needs, may be empty
     */
    private Need[] getNeedsArray(String containsText) {
        ArrayList<Need> needArrayList = new ArrayList<>();

        for(Need need : needs.values()) {
            if(containsText == null || need.getName().contains(containsText)) {
                needArrayList.add(need);
            }
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    /**
     * Saves the Needs from the map into the file as JSON objects
     * @return Boolean, true if success
     * @throws IOException When file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Need[] needArray = getNeedsArray();
        needObjectMapper.writeValue(new File(needFilePath), needArray);
        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteNeed(int id) throws IOException {
        synchronized (needs) {
            if (needs.containsKey(id)) {
                needs.remove(id);
                return save();
            } else
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Need createNeed(Need need) throws IOException {
        synchronized(needs) {
            // We create a new need object because the id field is immutable
            // and we need to assign the next unique id
            if(needs.containsKey(need.getId())){
                return null;
            }
            Need newNeed = new Need(nextNeedId(),need.getName(),need.getType(), need.getPrice(), need.getQuantity());
            needs.put(newNeed.getId(),newNeed);
            save(); // may throw an IOException
            return newNeed;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Need[] getNeeds() {
        synchronized (needs) {
            return getNeedsArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Need[] findNeeds(String containsText) {
        synchronized (needs) {
            return getNeedsArray(containsText);
        }
    }

    public Need getNeed(int id) {
        synchronized(needs) {
            if (needs.containsKey(id))
                return needs.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    public Need updateNeed(Need need) throws IOException {
        synchronized (needs) {
            if(needs.containsKey(need.getId()) == false) {
                // Need does not exist; cannot be updated
                return null;
            }
            needs.put(need.getId(), need);
            save();
            return need;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Credential createCredential(Credential credential) throws IOException {
        synchronized (credentials) {
            if (credentials.containsKey(credential.getUsername())) {
                // Username already exists
                return null;
            }
            credentials.put(credential.getUsername(), credential);
            save();
            return credential;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Credential updateCredential(Credential credential) throws IOException {
        synchronized (credentials) {
            if(!credentials.containsKey(credential.getUsername())) {
                // Credential does not exist; cannot be updated
                return null;
            }
            credentials.put(credential.getUsername(), credential);
            save();
            return credential;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean compareCredential(Credential credential) throws IOException {
        synchronized (credentials) {
            // Check if this username exists
            if(credentials.containsKey(credential.getUsername())) {
                return credentials.get(credential.getUsername()).comparePassword(credential.getPassword());
            }
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteCredential(String user) throws IOException {
        synchronized (credentials) {
            // Check if this username exists
            if(credentials.containsKey(user)) {
                credentials.remove(user);
                return save();
            } else {
                return false;
            }
        }
    }


}

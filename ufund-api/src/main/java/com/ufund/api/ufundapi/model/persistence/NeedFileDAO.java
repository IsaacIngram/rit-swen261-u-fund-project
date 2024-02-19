package com.ufund.api.ufundapi.model.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class NeedFileDAO implements NeedDAO {

    Map<Integer, Need> needs; // Local cache of needs, so we don't need to read/write file constantly
    private ObjectMapper objectMapper; // Conversion between Need and JSON text for writing to file

    private static int nextId;

    private String filePath; // File path to read/write data to

    public NeedFileDAO(@Value("${needs.file}") String filePath, ObjectMapper objectMapper) throws IOException {
        this.filePath = filePath;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Loads Needs from the JSON file into the map and sets
     * the nextID to one more than the greatest ID found in the JSON file
     * @return Boolean: True if the file was read successfully
     * @throws IOException When the file cannot be accessed or read
     */
    private boolean load() throws IOException {
        needs = new TreeMap<>();
        nextId = 0;

        try {

            Need[] needArray = objectMapper.readValue(new File(filePath), Need[].class);
            for (Need need: needArray) {
                needs.put(need.getId(), need);
                if(need.getId() > nextId)
                    nextId = need.getId();
            }
            ++nextId;
        } catch(EOFException e) {
            Need[] needArray = new Need[0];
        }
        return true;
    }

    /**
     * Generate the next ID for a new {Need}
     * @return Integer next id
     */
    private synchronized static int getNextId() {
        int id = nextId;
        ++nextId;
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
        objectMapper.writeValue(new File(filePath), needArray);
        return true;
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

}

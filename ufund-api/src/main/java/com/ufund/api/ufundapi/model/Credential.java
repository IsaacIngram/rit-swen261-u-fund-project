package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credential {

    static final String STRING_FORMAT = "Credential [username=%s, password=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    /**
     * Create a credential with the given properties
     * @param username The username
     * @param password The password hash as a string
     */
    public Credential(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password
    ) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get the username of this credential
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Check whether this credentials password hash equals a string
     * @param comparison String to compare
     * @return True if they match, False otherwise
     */
    public boolean comparePassword(String comparison) {
        return comparison.equals(password);
    }

    /**
     * Get the password hash from this credential
     * @return String password hash
     */
    public String getPassword() {
        return password;
    }

    /**
     * Check if this credential equals another (by username only)
     * @param o the object to compare
     * @return True if the other object is a Credential with the same username,
     * false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Credential) {
            Credential object = (Credential)o;
            return this.username.equals(object.getUsername());
        }
        return false;
    }

}

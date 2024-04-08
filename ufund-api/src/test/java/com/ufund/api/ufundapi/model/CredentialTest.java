package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@Tag("Model-tier")
public class CredentialTest {

    @Test
    public void testGetAttributes() {
        // Setup
        String expected_username = "jeff";
        String expected_password = "5afa9fjjadf188341fadfda88323";

        // Invoke
        Credential credential = new Credential(
                expected_username,
                expected_password
        );

        // Analyze
        assertEquals(expected_username, credential.getUsername());
        assertEquals(expected_password, credential.getPassword());
    }

    @Test
    public void testComparePassword() {
        // Setup
        String username = "jeff";
        String password = "5afa9fjjadf188341fadfda88323";
        Credential credential = new Credential(
                username,
                password
        );

        // Invoke
        boolean matches_result = credential.comparePassword(password);
        boolean does_not_match_result = credential.comparePassword("password");

        // Analyze
        assertTrue(matches_result);
        assertFalse(does_not_match_result);
    }

    @Test
    public void testEquals() {
        // Setup
        String user_one_name = "jeff";
        String password_one = "5afa9fjjadf188341fadfda88323";
        String user_two_name = "bob";
        String password_two = "5afa9fjjadf188341fadfda88323";
        String user_three_name = "jeff";
        String password_three = "password";
        Credential user_one = new Credential(
                user_one_name, password_one
        );
        Credential user_two = new Credential(
                user_two_name, password_two
        );
        Credential user_three = new Credential(
                user_three_name, password_three
        );

        // Invoke
        boolean should_match = user_one.equals(user_three);
        boolean should_not_match = user_one.equals(user_two);

        // Analyze
        assertTrue(should_match);
        assertFalse(should_not_match);
    }


}

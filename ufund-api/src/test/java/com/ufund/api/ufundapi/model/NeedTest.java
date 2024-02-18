package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test suite for the Need class.
 */
@Tag("Model-tier")
public class NeedTest {

    /**
     * Test instantiating a new Need and getting its attributes to ensure that
     * the data is correctly stored.
     */
    @Test
    public void testGetAttributes() {
        // Setup
        int expected_id = 3;
        String expected_name = "Water";
        float expected_price = 3.21f;
        int expected_quantity = 5;

        // Invoke
        Need need = new Need(
                expected_id,
                expected_name,
                expected_price,
                expected_quantity
        );

        // Analyze
        assertEquals(expected_id, need.getId());
        assertEquals(expected_name, need.getName());
        assertEquals(expected_price, need.getPrice());
        assertEquals(expected_quantity, need.getQuantity());
    }

    public void testSetId() {

    }

}

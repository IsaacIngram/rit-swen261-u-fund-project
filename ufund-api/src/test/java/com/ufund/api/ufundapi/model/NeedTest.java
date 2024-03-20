package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

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
        String expected_type = "BEVERAGE";
        float expected_price = 3.21f;
        int expected_quantity = 5;

        // Invoke
        Need need = new Need(
                expected_id,
                expected_name,
                expected_type,
                expected_price,
                expected_quantity
        );

        // Analyze
        assertEquals(expected_id, need.getId());
        assertEquals(expected_name, need.getName());
        assertEquals(expected_price, need.getPrice());
        assertEquals(expected_quantity, need.getQuantity());
    }

    /**
     * Test setting the name of a Need
     */
    @Test
    public void testSetName() {
        // Setup
        int id = 3;
        String old_name = "Water";
        String new_name = "Ice";
        String type = "H2O";
        float price = 3.21f;
        int quantity = 5;

        // Invoke
        Need need = new Need(
                id,
                old_name,
                type,
                price,
                quantity
        );
        need.setName("Ice");

        // Analyze
        assertEquals(new_name, need.getName());
    }

    /**
     * Test setting the type of Need
     */
    @Test
    public void testSetType() {
        // Setup
        int id = 3;
        String name = "Water";
        String old_type = "BEVERAGE";
        String new_type = "GROCERY";
        float price = 3.21f;
        int quantity = 5;

        // Invoke
        Need need = new Need(
                id,
                name,
                old_type,
                price,
                quantity
        );
        need.setType(new_type);

        // Analyze
        assertEquals(new_type, need.getType());
    }

    /**
     * Test setting the price of a Need
     */
    @Test
    public void testSetPrice() {
        // Setup
        int id = 3;
        String name = "Water";
        String type = "BEVERAGE";
        float old_price = 3.21f;
        float new_price = 6.78f;
        int quantity = 5;
        Need need = new Need(
                id,
                name,
                type,
                old_price,
                quantity
        );

        // Invoke
        need.setPrice(new_price);

        // Analyze
        assertEquals(new_price, need.getPrice());
    }

    /**
     * Test setting the quantity of a Need
     */
    @Test
    public void testSetQuantity() {
        // Setup
        int id = 3;
        String name = "Water";
        String type = "BEVERAGE";
        float price = 3.21f;
        int old_quantity = 5;
        int new_quantity = 8;
        Need need = new Need(
                id,
                name,
                type,
                price,
                old_quantity
        );

        // Invoke
        need.setQuantity(new_quantity);

        // Analyze
        assertEquals(new_quantity, need.getQuantity());
    }

    /**
     * Test getting the string of a Need
     */
    @Test
    public void testToString() {
        // Setup
        int id = 3;
        String name = "Water";
        String type = "BEVERAGE";
        float price = 3.21f;
        int quantity = 5;
        String expected_string = String.format(Need.STRING_FORMAT, id, name, price, quantity);
        Need need = new Need(
                id,
                name,
                type,
                price,
                quantity
        );

        // Invoke
        String actual_string = need.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

    /**
     * Test instantiating a new Need and getting its attributes to ensure that
     * the data is correctly stored.
     */
    @Test
    public void testEquals() {
        // Setup
        int expected_id = 3;
        String expected_name = "Water";
        String expected_type = "BEVERAGE";
        float expected_price = 3.21f;
        int expected_quantity = 5;

        // Invoke
        Need need = new Need(
                expected_id,
                expected_name,
                expected_type,
                expected_price,
                expected_quantity
        );

        Need need2 = new Need(
                expected_id,
                expected_name,
                expected_type,
                expected_price,
                expected_quantity
        );

        Need need3 = new Need(
                expected_id + 1,
                expected_name,
                expected_type,
                expected_price,
                expected_quantity
        );

        Integer i = 0;

        // Analyze
        assertEquals(false , need == need2);
        assertEquals(false , need == need3);
        assertEquals(false , need.equals(i));
    }

}

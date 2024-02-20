package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Need {

    static final String STRING_FORMAT = "Need [id=%d, name=%s, price=%f, quantity=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("type") private String type;
    @JsonProperty("price") private float price;
    @JsonProperty("quantity") private int quantity;

    /**
     * Create a need with the given properties
     * @param id The id
     * @param name The name
     * @param price The price
     * @param quantity The quantity
     * {@literal @}JsonProperty is used to read JSON objects into Java objects
     * in the mapping fields. If a field is not provided the Java field gets
     * the default value.
     */
    public Need(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("price") float price,
            @JsonProperty("quantity") int quantity
            ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Get the ID of this need
     * @return Integer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of this need
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this need
     * @param name String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the type of this need
     * @param type String type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the type of this need
     * @return String type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the price of this need
     * @return Float price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Get the quantity of this need
     * @return Integer quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the price of this need
     * @param price Float price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Set the quantity of this need
     * @param quantity Integer quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, price, quantity);
    }

}

package com.santander.pricefeedclient.util;

import com.santander.pricefeedclient.model.Price;

/**
 * A PriceDeserializer is responsible for deserializing a message string into a {@link Price} object.
 */
public interface PriceDeserializer {

    /**
     * Deserialize the given message string into a Price object.
     *
     * @param message the message string to deserialize
     * @return the Price object created from the deserialized message
     * @throws IllegalArgumentException if the message string is not in the expected format
     */
    Price deserialize(String message) throws IllegalArgumentException;
}

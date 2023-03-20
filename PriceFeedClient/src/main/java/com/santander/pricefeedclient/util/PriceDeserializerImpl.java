package com.santander.pricefeedclient.util;

import com.santander.pricefeedclient.model.Price;

import java.util.UUID;

/**
 * An implementation of the {@link PriceDeserializer} interface that deserializes price messages in a comma-separated format.
 */
public class PriceDeserializerImpl implements PriceDeserializer {

    private static final String COLUMN_DELIMITER = ",";
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int BID_INDEX = 2;
    private static final int ASK_INDEX = 3;
    private static final int TIMESTAMP_INDEX = 4;

    /**
     * Creates a new instance of the {@code PriceDeserializerImpl} class.
     */
    public PriceDeserializerImpl() {
    }

    /**
     * Deserializes a price message in the expected format and returns a new {@link Price} object.
     *
     * @param message the price message to deserialize
     * @return a new {@code Price} object representing the deserialized price message
     * @throws IllegalArgumentException if the message is in an invalid format
     */
    @Override
    public Price deserialize(String message) {
        String[] fields = message.split(COLUMN_DELIMITER);
        if (fields.length != 5) {
            throw new IllegalArgumentException("Invalid message format: " + message);
        }
        try {
            UUID id = UUID.fromString(fields[ID_INDEX]);
            String name = fields[NAME_INDEX];
            double bid = Double.parseDouble(fields[BID_INDEX]);
            double ask = Double.parseDouble(fields[ASK_INDEX]);
            long timestamp = Long.parseLong(fields[TIMESTAMP_INDEX]);
            return new Price(id, name, bid, ask, timestamp);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid message format: " + message, e);
        }
    }
}

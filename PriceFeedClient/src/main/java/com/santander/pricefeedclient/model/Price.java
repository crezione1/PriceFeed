package com.santander.pricefeedclient.model;

import java.util.UUID;

/**
 * A class representing a financial instrument price.
 */
public class Price {
    private UUID id;
    private String instrumentName;
    private double bid;
    private double ask;
    private long timestamp;

    /**
     * Creates a new instance of the {@code Price} class.
     *
     * @param id the unique identifier for this price
     * @param instrumentName the name of the financial instrument
     * @param bid the bid price for this instrument
     * @param ask the ask price for this instrument
     * @param timestamp the timestamp for this price (in milliseconds)
     */
    public Price(UUID id, String instrumentName, double bid, double ask, long timestamp) {
        this.id = id;
        this.instrumentName = instrumentName;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    /**
     * Gets the unique identifier for this price.
     *
     * @return the unique identifier for this price
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this price.
     *
     * @param id the unique identifier for this price
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the name of the financial instrument.
     *
     * @return the name of the financial instrument
     */
    public String getInstrumentName() {
        return instrumentName;
    }

    /**
     * Sets the name of the financial instrument.
     *
     * @param instrumentName the name of the financial instrument
     */
    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    /**
     * Gets the bid price for this instrument.
     *
     * @return the bid price for this instrument
     */
    public double getBid() {
        return bid;
    }

    /**
     * Sets the bid price for this instrument.
     *
     * @param bid the bid price for this instrument
     */
    public void setBid(double bid) {
        this.bid = bid;
    }

    /**
     * Gets the ask price for this instrument.
     *
     * @return the ask price for this instrument
     */
    public double getAsk() {
        return ask;
    }

    /**
     * Sets the ask price for this instrument.
     *
     * @param ask the ask price for this instrument
     */
    public void setAsk(double ask) {
        this.ask = ask;
    }

    /**
     * Gets the timestamp for this price.
     *
     * @return the timestamp for this price (in milliseconds)
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp for this price.
     *
     * @param timestamp the timestamp for this price (in milliseconds)
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns a string representation of this {@code Price} instance.
     *
     * @return a string representation of this {@code Price} instance
     */
    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", instrumentName='" + instrumentName + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timestamp=" + timestamp +
                '}';
    }
}

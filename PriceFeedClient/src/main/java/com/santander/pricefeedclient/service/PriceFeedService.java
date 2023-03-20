package com.santander.pricefeedclient.service;

import com.santander.pricefeedclient.model.Price;
import com.santander.pricefeedclient.util.PriceDeserializer;
import com.santander.pricefeedsource.PriceFeedSource;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * A service that subscribes to a price feed, applies a margin to the received prices and
 * stores them in a snapshot.
 */
public class PriceFeedService {

    /**
     * The deserializer used to deserialize the price feed messages.
     */
    private final PriceDeserializer priceDeserializer;

    /**
     * The map that stores the latest price for each instrument.
     */
    private final Map<String, Price> priceFeedSnapshot;

    /**
     * The default margin amount, in percentages.
     */
    private final double defaultMarginAmount;

    /**
     * A random number generator used to retrieve the margin amount.
     */
    private final Random random;

    /**
     * Constructs a new instance of the price feed service.
     *
     * @param priceDeserializer The deserializer used to deserialize the price feed messages.
     * @throws InterruptedException If the thread is interrupted while waiting for the snapshot to be populated.
     */
    public PriceFeedService(PriceDeserializer priceDeserializer) throws InterruptedException {
        this(priceDeserializer, new ConcurrentHashMap<>(), 0.1, new Random());
    }

    /**
     * Constructs a new instance of the price feed service.
     *
     * @param priceDeserializer The deserializer used to deserialize the price feed messages.
     * @param priceFeedSnapshot The map that stores the latest price for each instrument.
     * @param defaultMarginAmount The default margin amount, in percentages.
     * @param random A random number generator used to retrieve the margin amount.
     * @throws InterruptedException If the thread is interrupted while waiting for the snapshot to be populated.
     */
    public PriceFeedService(PriceDeserializer priceDeserializer, Map<String, Price> priceFeedSnapshot,
                            double defaultMarginAmount, Random random) throws InterruptedException {
        this.priceDeserializer = priceDeserializer;
        this.priceFeedSnapshot = priceFeedSnapshot;
        this.defaultMarginAmount = defaultMarginAmount;
        this.random = random;

        // Start the price feed subscription in a new thread
        Thread priceFeedThread = new Thread(this::subscribeToPriceFeed);
        priceFeedThread.start();

        // Wait for a short time to allow some values to be added to the map
        Thread.sleep(1000);
    }

    /**
     * Subscribes to the price feed and updates the price feed snapshot with the latest prices.
     */
    public void subscribeToPriceFeed() {
        initPriceFeedSubscription().forEach(message -> {
            for (String line : message.split("\n")) {
                Price price = priceDeserializer.deserialize(line);
                double margin = retrieveMargin();
                System.out.printf("BEFORE ADDING THE MARGIN: bid: %1$s, ask: %2$s%n", price.getBid(), price.getAsk());
                priceFeedSnapshot.put(price.getInstrumentName(), addMargin(price, retrieveMargin()));
            }
        });
    }

    /**
     * Returns the latest price for the specified instrument.
     *
     * @param instrumentName The name of the instrument.
     * @return The latest price for the specified instrument, or null if no price is available.
     */
    public Price getLatestPrice(String instrumentName) {
        return priceFeedSnapshot.get(instrumentName);
    }

    /**
     * Returns a map that contains the latest prices for all instruments.
     *
     * @return A map that contains the latest prices for all instruments.
     */
    public Map<String, Price> getLatestPriceFeedSnapshot() {
        return priceFeedSnapshot;
    }

    /**
     Adds a margin to the given price, based on the given margin percentage.
     @param price the price to which the margin should be added
     @param marginInPercentages the percentage of the margin to add
     @return a new price object with the margin added
     */
    public Price addMargin(Price price, double marginInPercentages) {
        double bid = price.getBid() - marginInPercentages;
        double ask = price.getAsk() + marginInPercentages;
        System.out.printf("AFTER ADDING THE MARGIN: bid: %1$s, ask: %2$s%n", bid, ask);
        return new Price(UUID.randomUUID(), price.getInstrumentName(), bid, ask, price.getTimestamp());
    }

    /**
     * Initializes the subscription to the price feed by running the {@link PriceFeedSource#runPriceFeed()} method,
     * which returns a stream of price feed messages.
     *
     * @return a stream of price feed messages
     */
    public Stream<String> initPriceFeedSubscription() {
        return PriceFeedSource.runPriceFeed();
    }

    /**
     * Retrieves a random margin percentage using the {@link Random#nextDouble()} method, or returns the default {@link PriceFeedService#defaultMarginAmount} margin
     * amount if the random number generated is zero.
     *
     * @return the margin percentage to be used for the current price feed message
     */
    public double retrieveMargin() {
        double margin = random.nextDouble();
        if (isZero(margin)) {
            return defaultMarginAmount;
        }
        return margin;
    }

    /**
     * Returns true if the given double value is equal to zero, and false otherwise.
     *
     * @param value the double value to be checked
     * @return true if the given value is equal to zero, and false otherwise
     */
    public boolean isZero(double value) {
        return value == 0;
    }
}

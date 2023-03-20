package com.santander.pricefeedsource;

import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * The PriceFeedSource class generates a stream of price feed messages in the format:
 * <p>
 * UUID,CURRENCY_PAIR,BID,ASK,TIMESTAMP.
 * <p>
 * It generates random prices for different currency pairs and adds them to a message queue. If there
 * <p>
 * are no messages in the queue, it generates new messages using the {@link #generateMessage()} method
 * <p>
 * and adds them to the queue.
 */
public class PriceFeedSource {

    private static final Random RANDOM = new Random();
    private static final String MESSAGE_FORMAT = "%s,%s,%.4f,%.4f,%d";
    private static final String[] CURRENCY_PAIRS = {"EUR/USD", "GBP/USD", "USD/JPY", "USD/CHF", "AUD/USD", "NZD/USD"};

    private static final Queue<String> MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();

    /**
     * This method generates a stream of price feed messages. If there are no messages in the queue,
     * <p>
     * it generates new messages using the {@link #generateMessage()} method and adds them to the queue.
     *
     * @return a stream of price feed messages in the format UUID,CURRENCY_PAIR,BID,ASK,TIMESTAMP
     */
    public static Stream<String> runPriceFeed() {
        return Stream.generate(() -> {
            String message = MESSAGE_QUEUE.poll();
            if (message == null) {
                message = generateMessage();
            }
            MESSAGE_QUEUE.offer(generateMessage());
            return message;

        }).limit(100);
    }

    /**
     * This method generates a message consisting of a random number of lines, where each line represents
     * a price for a currency pair. It uses the {@link #generateLine()} method to generate each line.
     *
     * @return a message consisting of a random number of lines, where each line represents a price for a
     * currency pair
     */
    private static String generateMessage() {
        int liensCount = RANDOM.nextInt(5) + 1;
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < liensCount; i++) {
            message.append(generateLine()).append("\n");
        }
        return message.toString();
    }

    /**
     * This method generates a random price for a currency pair in the format UUID,CURRENCY_PAIR,BID,ASK,TIMESTAMP.
     *
     * @return a random price for a currency pair in the format UUID,CURRENCY_PAIR,BID,ASK,TIMESTAMP
     */
    private static String generateLine() {
        String currencyPair = CURRENCY_PAIRS[(int) (Math.random() * CURRENCY_PAIRS.length)];
        double bid = RANDOM.nextDouble() * 2 + 1;
        double ask = bid + RANDOM.nextDouble() * 0.005;
        long timestamp = System.currentTimeMillis();
        return String.format(MESSAGE_FORMAT, UUID.randomUUID(), currencyPair, bid, ask, timestamp);
    }
}
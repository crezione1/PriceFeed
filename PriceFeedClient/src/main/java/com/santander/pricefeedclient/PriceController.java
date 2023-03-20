package com.santander.pricefeedclient;

import com.santander.pricefeedclient.model.Price;
import com.santander.pricefeedclient.service.PriceFeedService;
import com.santander.pricefeedclient.util.PriceDeserializer;
import com.santander.pricefeedclient.util.PriceDeserializerImpl;

import java.util.Map;

public class PriceController {
    private static PriceFeedService service;
    private static final String INSTRUMENT_NAME = "EUR/USD";

    // emulating of the endpoint which returns the LatestPrice far call for one provided instrument(EUR/USD)
    public static Price getLatestPrice(String instrumentName) {
        return service.getLatestPrice(instrumentName);
    }

    // emulating of the endpoint which returns the PriceFeed for all instruments
    public static Map<String, Price> getLatestPriceFeedSnapshot() {
        return service.getLatestPriceFeedSnapshot();
    }

    // here we emulate HTTP call to rest controller
    public static void main(String[] args) throws InterruptedException {
        PriceDeserializer priceDeserializer = new PriceDeserializerImpl();
        service = new PriceFeedService(priceDeserializer);
        System.out.println(getLatestPrice(INSTRUMENT_NAME));
        System.out.println(getLatestPriceFeedSnapshot());
    }
}

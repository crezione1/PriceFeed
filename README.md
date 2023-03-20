***The project consists of two Maven modules:***

***PriceFeedSource***

This module is an emulator of an infinite process of generating CSV-formatted strings which represent price attributes (id, instrumentName, bid, ask, timestamp).
For simplification price feed emulator is java infinity stream limited by 100 values.

***PriceFeedClient***

This module is a subscriber for the PriceFeed, receiving strings generated by PriceFeedSource. It has two fake endpoints which return the latest price for a particular instrument or the latest snapshot of the price feed.
The subscription is running from a separate thread for do not block the main thread

***Assumptions:***
This is simple implementation without using any external libraries such as lombock, spring-boot and some others,
addMargin() method placed in PriceFeedService for simplicity, but for the production, I`d like to move this method as part of the model class Price
and to use it like price.addMargin(margin).
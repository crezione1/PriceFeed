import com.santander.pricefeedclient.service.PriceFeedService;
import com.santander.pricefeedclient.model.Price;
import com.santander.pricefeedclient.util.PriceDeserializer;
import com.santander.pricefeedclient.util.PriceDeserializerImpl;
import org.junit.Test;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class PriceFeedServiceTest {
    private static final String EURUSD_PAIR_NAME = "EUR/USD";
    private PriceDeserializer priceDeserializer = new PriceDeserializerImpl();
    @Test
    public void testGetLatestPrice() throws InterruptedException {
        PriceFeedService service = new PriceFeedService(priceDeserializer);
        Map<String, Price> snapshot = service.getLatestPriceFeedSnapshot();
        Price price = service.getLatestPrice(EURUSD_PAIR_NAME);
        assertNotNull(price);
        assertEquals(price, snapshot.get(EURUSD_PAIR_NAME));
    }

    @Test
    public void testGetLatestPriceWithInvalidInstrumentName() throws InterruptedException {
        PriceFeedService service = new PriceFeedService(priceDeserializer);
        Map<String, Price> snapshot = service.getLatestPriceFeedSnapshot();
        Price price = service.getLatestPrice("INVALID_INSTRUMENT_NAME");
        assertNull(price);
        assertFalse(snapshot.containsKey("INVALID_INSTRUMENT_NAME"));
    }

    @Test
    public void testGetLatestPriceFeedSnapshot() throws InterruptedException {
        PriceFeedService service = new PriceFeedService(priceDeserializer);
        Map<String, Price> snapshot = service.getLatestPriceFeedSnapshot();
        assertNotNull(snapshot);
        assertFalse(snapshot.isEmpty());
    }

    @Test
    public void testAddMargin() throws InterruptedException {
        PriceFeedService service = new PriceFeedService(priceDeserializer);
        Price price = new Price(UUID.randomUUID(), EURUSD_PAIR_NAME, 1.0, 1.1, new Date().getTime());
        Price marginPrice = service.addMargin(price, 0.1);
        assertNotNull(marginPrice);
        assertEquals(0.9, marginPrice.getBid(), 0.0001);
        assertEquals(1.2, marginPrice.getAsk(), 0.0001);
    }
}

import com.santander.pricefeedclient.model.Price;
import com.santander.pricefeedclient.util.PriceDeserializer;
import com.santander.pricefeedclient.util.PriceDeserializerImpl;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PriceDeserializerTest {
    private final PriceDeserializer priceDeserializer = new PriceDeserializerImpl();
    @Test
    public void testDeserializeValidMessage() {
        String message = "fc754b5f-1af9-4559-b90e-62a0cc2b0f96,AUD/USD,0.77,0.78,1647744800";
        Price price = priceDeserializer.deserialize(message);
        assertEquals(UUID.fromString("fc754b5f-1af9-4559-b90e-62a0cc2b0f96"), price.getId());
        assertEquals("AUD/USD", price.getInstrumentName());
        assertEquals(0.77, price.getBid(), 0.001);
        assertEquals(0.78, price.getAsk(), 0.001);
        assertEquals(1647744800L, price.getTimestamp());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeInvalidMessageFormat() {
        String message = "fc754b5f-1af9-4559-b90e-62a0cc2b0f96,AUD/USD,0.77,0.78";
        priceDeserializer.deserialize(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeInvalidTimestampValueFormat() {
        String message = "fc754b5f-1af9-4559-b90e-62a0cc2b0f96,AUD/USD,0.77,0.78,invalid_number";
        priceDeserializer.deserialize(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeInvalidAskValueFormat() {
        String message = "fc754b5f-1af9-4559-b90e-62a0cc2b0f96,AUD/USD,0.77,sting,12344566";
        priceDeserializer.deserialize(message);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeInvalidBidValueFormat() {
        String message = "fc754b5f-1af9-4559-b90e-62a0cc2b0f96,AUD/USD,sting,0.77,12344566";
        priceDeserializer.deserialize(message);
    }

}
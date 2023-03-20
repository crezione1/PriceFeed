import com.santander.pricefeedsource.PriceFeedSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.santander.pricefeedsource.PriceFeedSource.runPriceFeed;

public class PriceFeedSourceTest {
    private static final int MESSAGE_COUNT = 100;
    private static final int COLUMNS_COUNT = 5;

    @Test
    public void shouldGenerateInfiniteStreamOfMessages() throws InterruptedException {
        List<String> messages = PriceFeedSource.runPriceFeed().limit(MESSAGE_COUNT).collect(Collectors.toList());
        Assert.assertEquals(MESSAGE_COUNT, messages.size());
    }

    @Test
    public void shouldGenerateUniqueMessages() throws InterruptedException {
        Set<String> uniqueMessages = PriceFeedSource.runPriceFeed().limit(MESSAGE_COUNT).collect(Collectors.toSet());
        Assert.assertEquals(MESSAGE_COUNT, uniqueMessages.size());
    }

    @Test
    public void shouldGenerateMessagesWithValidFormat() throws InterruptedException {
        List<String> messages = runPriceFeed().limit(MESSAGE_COUNT).collect(Collectors.toList());
        for (String message : messages) {
            for (String line: message.split("\n")) {
                String[] parts = line.split(",");
                Assert.assertEquals(COLUMNS_COUNT, parts.length);
                Assert.assertTrue(parts[0].matches("[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}"));
                Assert.assertTrue(parts[1].matches("^[A-Z]{3}/[A-Z]{3}$"));
                Assert.assertTrue(parts[2].matches("\\d+\\.\\d{4}"));
                Assert.assertTrue(parts[3].matches("\\d+\\.\\d{4}"));
                Assert.assertTrue(parts[4].matches("\\d+"));
            }
        }
    }
}
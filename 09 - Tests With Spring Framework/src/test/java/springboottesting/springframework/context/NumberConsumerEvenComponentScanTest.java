package springboottesting.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles({"component-scan"})
@SpringJUnitConfig(classes = NumberConsumerEvenComponentScanTest.TestConfig.class)
class NumberConsumerEvenComponentScanTest {
    @Profile("component-scan")
    @Configuration
    @ComponentScan("springboottesting.springframework.context")
    static class TestConfig {}

    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(2, numberConsumer.consumeNumber());
    }
}
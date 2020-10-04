package springboottesting.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("odd")
@SpringJUnitConfig(classes = NumberConsumerOddActiveProfilesTest.TestConfig.class)
class NumberConsumerOddActiveProfilesTest {
    @Profile("odd")
    @Configuration
    @ComponentScan("springboottesting.springframework.context")
    static class TestConfig {}

    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(1, numberConsumer.consumeNumber());
    }
}
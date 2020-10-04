package springboottesting.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("even")
@SpringJUnitConfig(classes = NumberConsumerEvenActiveProfilesTest.TestConfig.class)
class NumberConsumerEvenActiveProfilesTest {
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
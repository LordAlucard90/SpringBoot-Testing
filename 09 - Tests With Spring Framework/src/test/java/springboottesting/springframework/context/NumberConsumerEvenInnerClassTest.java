package springboottesting.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("inner-class")
@SpringJUnitConfig(classes = NumberConsumerEvenInnerClassTest.TestConfig.class)
class NumberConsumerEvenInnerClassTest {
    @Profile("inner-class")
    @Configuration
    static class TestConfig {
        @Bean
        NumberConsumer numberConsumer() {
            return new NumberConsumer(new EvenNumberProducer());
        }
    }

    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(2, numberConsumer.consumeNumber());
    }
}
package springboottesting.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("classpath:number.properties")
@ActiveProfiles("property")
@SpringJUnitConfig(classes = NumberConsumerOddPropertyTest.TestConfig.class)
class NumberConsumerOddPropertyTest {
    @Configuration
    @ComponentScan("springboottesting.springframework.context")
    static class TestConfig {}

    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(42, numberConsumer.consumeNumber());
    }
}
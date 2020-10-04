package springboottesting.springframework.context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboottesting.springframework.common.BaseConfig;
import springboottesting.springframework.common.OddConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("base")
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {BaseConfig.class, OddConfig.class})
//@SpringJUnitConfig(classes = {BaseConfig.class, OddConfig.class})
class NumberConsumerOddAnnotationTest {
    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(1, numberConsumer.consumeNumber());
    }
}
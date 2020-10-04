package springboottesting.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import springboottesting.springframework.common.BaseConfig;
import springboottesting.springframework.common.EvenConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("base")
//@ExtendWith({SpringExtension.class})
//@ContextConfiguration(classes = {BaseConfig.class, EvenConfig.class})
@SpringJUnitConfig(classes = {BaseConfig.class, EvenConfig.class})
class NumberConsumerEvenAnnotationTest {
    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(2, numberConsumer.consumeNumber());
    }
}
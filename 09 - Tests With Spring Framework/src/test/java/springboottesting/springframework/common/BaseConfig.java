package springboottesting.springframework.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import springboottesting.springframework.context.NumberConsumer;
import springboottesting.springframework.context.NumberProducer;

@ActiveProfiles("base")
@Configuration
public class BaseConfig {

    @Bean
    NumberConsumer numberConsumer(NumberProducer numberProducer) {
        return new NumberConsumer(numberProducer);
    }
}

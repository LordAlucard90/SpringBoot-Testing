package springboottesting.springframework.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import springboottesting.springframework.context.EvenNumberProducer;
import springboottesting.springframework.context.NumberProducer;

@ActiveProfiles("base")
@Configuration
public class EvenConfig {

    @Bean
    NumberProducer numberProducer() {
        return new EvenNumberProducer();
    }
}

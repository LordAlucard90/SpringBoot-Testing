package springboottesting.springframework.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import springboottesting.springframework.context.NumberProducer;
import springboottesting.springframework.context.OddNumberProducer;

@ActiveProfiles("base")
@Configuration
public class OddConfig {

    @Bean
    NumberProducer numberProducer() {
        return new OddNumberProducer();
    }
}

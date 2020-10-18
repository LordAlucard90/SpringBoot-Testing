package springboottesting.springframework.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RestTemplateExampleHelper {
    RestTemplate restTemplate;
    String baseUrl;

    public RestTemplateExampleHelper(RestTemplateBuilder restTemplateBuilder, String baseUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.baseUrl = baseUrl;
    }

    public void sendExample(ExampleEntity entity) {
        try {
            restTemplate.postForObject(baseUrl + "/api/v1/examples/", entity, ExampleEntity.class);
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
        }
    }
}

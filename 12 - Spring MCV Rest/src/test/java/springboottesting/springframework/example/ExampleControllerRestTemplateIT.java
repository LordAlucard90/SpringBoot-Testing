package springboottesting.springframework.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleControllerRestTemplateIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ExampleService exampleServiceMock;

    // Stub Data
    private ExampleEntity exampleEntity1;
    private ExampleEntity exampleEntity2;
    private List<ExampleEntity> exampleEntities;

    @BeforeEach
    void setUp() {
        // Initialize stub data
        var now = LocalDateTime.now();
        exampleEntity1 = new ExampleEntity(1L, "first example", now);
        exampleEntity2 = new ExampleEntity(2L, "second example", now);
        exampleEntities = List.of(exampleEntity1, exampleEntity2);
    }

    @AfterEach
    void tearDown() {
        reset(exampleServiceMock);
    }

    @Test
    void getExampleMvc() throws Exception {
        given(exampleServiceMock.getExample(any())).willReturn(exampleEntity1);

        ExampleEntity exampleEntity = restTemplate.getForObject("/api/v1/examples/" + exampleEntity1.getId(), ExampleEntity.class);

        assertEquals(exampleEntity1, exampleEntity);
    }

    @Test
    void getAllExamplesMvc() {
        given(exampleServiceMock.getAllExamples()).willReturn(exampleEntities);

        List exampleEntities = restTemplate.getForObject("/api/v1/examples", List.class);

        assertEquals(exampleEntities.size(), exampleEntities.size());
    }
}
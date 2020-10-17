package springboottesting.springframework.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExampleController.class)
class ExampleControllerWebMvcSliceTest {

    @MockBean
    private ExampleService exampleServiceMock;

    @Autowired
    MockMvc mockMvc;

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

        mockMvc.perform(get("/api/v1/examples/" + exampleEntity1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(exampleEntity1.getId()))
                .andExpect(jsonPath("$.example").value(exampleEntity1.getExample()));
    }

    @Test
    void getAllExamplesMvc() throws Exception {
        given(exampleServiceMock.getAllExamples()).willReturn(exampleEntities);

        mockMvc.perform(get("/api/v1/examples"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(exampleEntities.size())))
                .andExpect(jsonPath("$[0].id").value(exampleEntity1.getId()))
                .andExpect(jsonPath("$[0].example").value(exampleEntity1.getExample()))
                .andExpect(jsonPath("$[1].id").value(exampleEntity2.getId()))
                .andExpect(jsonPath("$[1].example").value(exampleEntity2.getExample()));
    }
}
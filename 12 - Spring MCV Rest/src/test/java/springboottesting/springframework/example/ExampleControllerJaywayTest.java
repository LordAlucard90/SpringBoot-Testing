package springboottesting.springframework.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExampleControllerJaywayTest {

    @Mock
    private ExampleService exampleServiceMock;

    @InjectMocks
    private ExampleController exampleController;

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

        mockMvc = MockMvcBuilders.standaloneSetup(exampleController).build();
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
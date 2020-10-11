package springboottesting.springframework.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExampleControllerTest {

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
        exampleEntity1 = new ExampleEntity(1L, "first example");
        exampleEntity2 = new ExampleEntity(2L, "second example");
        exampleEntities = List.of(exampleEntity1, exampleEntity2);

        mockMvc = MockMvcBuilders.standaloneSetup(exampleController).build();
    }

    @Test
    void getAllExamplesMvc() throws Exception {
        given(exampleServiceMock.getAllExamples()).willReturn(exampleEntities);
        mockMvc.perform(get("/examples"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("examples"))
                .andExpect(model().attribute("examples", exampleEntities))
                .andExpect(view().name("examples/examplesList"));
    }

    @Test
    void getExampleMvc() throws Exception {
        given(exampleServiceMock.getExample(any())).willReturn(exampleEntity1);
        mockMvc.perform(get("/example").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("example"))
                .andExpect(model().attribute("example", exampleEntity1))
                .andExpect(view().name("examples/exampleView"));
    }
}
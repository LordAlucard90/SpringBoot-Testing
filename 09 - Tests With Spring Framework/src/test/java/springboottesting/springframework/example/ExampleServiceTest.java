package springboottesting.springframework.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ExampleServiceTest {
    @Mock
    private ExampleRepository exampleRepositoryMock;

    @InjectMocks
    private ExampleService exampleService;

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

        // given
        given(exampleRepositoryMock.findAll()).willReturn(exampleEntities);
    }

    @Test
    void getAllExamples() {
        // when
        var response = exampleService.getAllExamples();

        // then
        then(exampleRepositoryMock).should().findAll();
        assertEquals(exampleEntities, response);
    }
}
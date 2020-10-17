package springboottesting.springframework.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExampleControllerCustomObjectMapperTest {

    @Mock
    private ExampleService exampleServiceMock;

    @InjectMocks
    private ExampleController exampleController;

    MockMvc mockMvc;

    // Stub Data
    private final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
    private ExampleEntity exampleEntity;
    private DateTimeFormatter dateTimeFormatter;

    @BeforeEach
    void setUp() {
        // Initialize stub data
        var now = LocalDateTime.now();
        exampleEntity = new ExampleEntity(1L, "first example", now);

        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

        mockMvc = MockMvcBuilders
                .standaloneSetup(exampleController)
                .setMessageConverters(getObjectMatter())
                .build();

    }

    private MappingJackson2HttpMessageConverter getObjectMatter() {
        var module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.registerModule(module);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Test
    void getExampleMvc() throws Exception {
        given(exampleServiceMock.getExample(any())).willReturn(exampleEntity);

        mockMvc.perform(get("/api/v1/examples/" + exampleEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.createdAt").value(dateTimeFormatter.format(exampleEntity.getCreatedAt())));
    }
}
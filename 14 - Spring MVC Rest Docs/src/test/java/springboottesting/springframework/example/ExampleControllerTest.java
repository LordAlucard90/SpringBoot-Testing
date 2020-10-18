package springboottesting.springframework.example;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(
        uriScheme = "http",
        uriHost = "127.0.0.1",
        uriPort = 80
)
@WebMvcTest(ExampleController.class)
class ExampleControllerTest {
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
        exampleEntity1 = new ExampleEntity(1L, "first example");
        exampleEntity2 = new ExampleEntity(2L, "second example");
        exampleEntities = List.of(exampleEntity1, exampleEntity2);
    }

    @AfterEach
    void tearDown() {
        reset(exampleServiceMock);
    }

    @Test
    void getExampleMvc() throws Exception {
        given(exampleServiceMock.getExample(any())).willReturn(exampleEntity1);

        mockMvc.perform(
                get("/api/v1/examples/{exampleId}", exampleEntity1.getId())
                        .param("isAnExample", "true"))
                .andExpect(status().isOk())
                .andDo(document("v1/examples-get",
                        pathParameters(
                                parameterWithName("exampleId").description("Id of the example.")
                        ),
                        requestParameters(
                                parameterWithName("isAnExample").description("true if it is an example.")
                        ),
                        responseFields( // all fields are needed
                                fieldWithPath("id").description("Example Id."),
                                fieldWithPath("example").description("Example content.")
                        )
                ));
    }

    @Test
    void saveAnExampleMvc() throws Exception {
        given(exampleServiceMock.saveExample(any())).willAnswer(invocation -> {
            var entity = (ExampleEntity) invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });

        var fieldsDescriptor = new ConstrainedFields(ExampleEntity.class);

        mockMvc.perform(post("/api/v1/examples/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\": 0, \"example\":\"A new example.\"}"))
                .andExpect(status().isOk())
                .andDo(document("v1/examples-post",
                        requestFields( // all fields are needed
                                fieldsDescriptor.withPath("id").ignored(),
                                fieldsDescriptor.withPath("example").description("Example content.")
                        )
                ));
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}
# Spring Mock MVC

## Content 

- [Spring MVC Standalone](#spring-mvc-standalone)
- [Working With Parameters](#working-with-parameters)

--- 

## Spring MVC Standalone

The standalone version can be created in the setup and used to check response creation:

```java
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

        // given
        given(exampleServiceMock.getAllExamples()).willReturn(exampleEntities);

        mockMvc = MockMvcBuilders.standaloneSetup(exampleController).build();
    }

    @Test
    void getAllExamplesMvc() throws Exception {
        mockMvc.perform(get("/examples"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("examples"))
                .andExpect(model().attribute("examples", exampleEntities))
                .andExpect(view().name("examples/examplesList"));
    }
}
```

--- 

## Working With Parameters

Params to the controller can be easily added one by one in the call definition:
```java
@ExtendWith(MockitoExtension.class)
class ExampleControllerTest {

    @Mock
    private ExampleService exampleServiceMock;

    @InjectMocks
    private ExampleController exampleController;

    MockMvc mockMvc;

    // Stub Data
    private ExampleEntity exampleEntity1;

    @BeforeEach
    void setUp() {
        // Initialize stub data
        exampleEntity1 = new ExampleEntity(1L, "first example");

        mockMvc = MockMvcBuilders.standaloneSetup(exampleController).build();
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
```

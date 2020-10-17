# Spring MVC Rest

## Content 

- [Jayway JsonPath](#jayway-jsonpath)
- [Custom Object Mapper](#custom-object-mapper)
- [WebMvc Test Slice](#webmvc-test-slice)
- [SpringBootTest](#springboottest)

--- 

## Jayway JsonPath

[Jayway JsonPath](https://github.com/json-path/JsonPath/blob/master/README.md) is a tool for reading JSON documents.

It can be used in rest controller test to easily parse and check the content of the response:
```java
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
        exampleEntity1 = new ExampleEntity(1L, "first example");
        exampleEntity2 = new ExampleEntity(2L, "second example");
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
```

--- 

## Custom Object Mapper

It is possible to include in the controller a custom object mapper used to serialize/deserialize the Json Data:
```java
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
```
--- 

## WebMvc Test Slice

It is possible load the Spring context only for the controller under test injecting all the dependencies automatically:
```java
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
```

--- 

## SpringBootTest

It is possible to load all the spring context, included the database, using `@SpringBootTest`:
```java
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
```
In order to really test the integration with the db it is needed a context loader that loads default data in the database,
since I did not create it I just use mockito, therefore is not a full integration test.


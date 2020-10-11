# Tests With Spring Framework

## Content 

- [Spring Framework Testing Features](#spring-framework-testing-features)
- [Maven Dependencies](#maven-dependencies)
- [Base testing](#base-testing)
- [Set Context Using Annotations](#set-context-using-annotations)
- [Set Context Using Inner Class](#set-context-using-inner-class)
- [Set Context Using ComponentScan](#set-context-using-componentscan)
- [Set Context Using Active Profiles](#set-context-using-active-profiles)
- [Set Context Using Properties](#set-context-using-properties)

--- 

## Spring Framework Testing Features

Spring framework provides a lot of mock object to use in testing:
- `Environment`: environment and source properties
- `JNDI`: JNDI
- `Servlet API`: web environment
- `Spring Web Reactive`: reactive web environment

It also provides reflection test utils to modify private fields.

--- 

## Maven Dependencies

In addition to all the main spring dependencies, the dependencies needed just for testing are:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <scope>test</scope>
</dependency>
```

--- 

## Base Testing

The main functionalities of controllers and services can be tested just using mockito like in the previous sections:
```java
@ExtendWith(MockitoExtension.class)
class ExampleControllerTest {

    @Mock
    private ExampleService exampleServiceMock;

    @InjectMocks
    private ExampleController exampleController;

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
    }

    @Test
    void getAllExamples() {
        // when
        var response = exampleController.getAllExamples();

        // then
        then(exampleServiceMock).should().getAllExamples();
        assertEquals(exampleEntities, response);
    }
}
```

--- 

## Set Context Using Annotations

It is possible to configure the context used by a component using:
```java
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {BaseConfig.class, EvenConfig.class})
// or
@SpringJUnitConfig(classes = {BaseConfig.class, EvenConfig.class})
```
Now using the `@Autowire` annotation is possible to inject the correct bean:
```java
class NumberConsumerEvenAnnotationTest {
    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(2, numberConsumer.consumeNumber());
    }
}
```

--- 

## Set Context Using Inner Class

It is also possible to create an inner configuration class to use:
```java
@SpringJUnitConfig(classes = NumberConsumerEvenInnerClassTest.TestConfig.class)
class NumberConsumerEvenInnerClassTest {
    @Configuration
    static class TestConfig {
        @Bean
        NumberConsumer numberConsumer() {
            return new NumberConsumer(new EvenNumberProducer());
        }
    }

    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(2, numberConsumer.consumeNumber());
    }
}
```

--- 

## Set Context Using ComponentScan

It is possible to use the default component in the system, the one marked as primary, using the ComponentScan:
```java
@SpringJUnitConfig(classes = NumberConsumerEvenComponentScanTest.TestConfig.class)
class NumberConsumerEvenComponentScanTest {
    @Configuration
    @ComponentScan("springboottesting.springframework.context")
    static class TestConfig {}

    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(2, numberConsumer.consumeNumber());
    }
}
```

--- 

## Set Context Using Active Profiles

It is also possible to configure a profile with a component:
```java
@ActiveProfiles("odd")
@SpringJUnitConfig(classes = NumberConsumerOddActiveProfilesTest.TestConfig.class)
class NumberConsumerOddActiveProfilesTest {
    @Profile("odd")
    @Configuration
    @ComponentScan("springboottesting.springframework.context")
    static class TestConfig {}

    @Autowired
    NumberConsumer numberConsumer;

    @Test
    void consumeNumber() {
        assertEquals(1, numberConsumer.consumeNumber());
    }
}
```
since component scan load everything, to avoid overlaps
it is necessary to disable all the other configurations using a profile for each other test.

--- 

## Set Context Using Properties

It is possible to change the context also using properties file:
```java
@TestPropertySource("classpath:number.properties")
@ActiveProfiles("property")
@SpringJUnitConfig(classes = NumberConsumerOddPropertyTest.TestConfig.class)
class NumberConsumerOddPropertyTest {
    @Configuration
    @ComponentScan("springboottesting.springframework.context")
    static class TestConfig {}

    @Autowired
    NumberConsumer numberConsumer; 

    @Test
    void consumeNumber() {
        assertEquals(42, numberConsumer.consumeNumber());
    }
}
```

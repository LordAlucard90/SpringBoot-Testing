# Advanced JUnit5

## Content

- [Tags And Filtering](#tags-and-filtering)
- [Nested Tests](#nested-tests)
- [Test Interfaces](#test-interfaces)
- [Repeating Tests](#repeating-tests)
- [Parametrized Tests](#parametrized-tests)
- [Unit vs Integration Tests](#unit-vs-integration-tests)
- [Extensions](#extensions)

---

## Tags And Filtering

It is possible to tag a test using the annotation `@Tag`:
```java
@Tag("example")
class TagExampleTest {
    /* ... */
}
```
Then it is possible in IntelliJ Idea to create a configuration to run only tests with a specific tag:
1. `Run` > `Edit Configurations..`
1. `+` > choose `JUnit`
1. `Test kind` > choose `Tags`
1. `Tag expression` > insert the tag name, in this case `example`

It is also possible to run all the test in a project (like this) selecting:
1. `Test kind` > choose `All in package`
1. `Search for tests` > choose `in whole project`

---

## Nested Tests

It is possible to create nested tests classes inside a main test class and even go deeper.

Each test in a nested class will be run after that all the `@BeforeEach` are run,
the order goes from the outer to the inner ones.
In the same way are run the `@AfterEach`, starting from the inner to the outer ones.

```java
@DisplayName("NestingExampleTest")
class NestingExampleTest {
    @BeforeEach
    void setUp() {
        System.out.println("Before Each in NestingExampleTest.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each in NestingExampleTest.");

    }

    @Test
    void test() {
        System.out.println("NestingExampleTest test.");
    }

    @Nested
    @DisplayName("NestedExample")
    class NestedExample {
        @BeforeEach
        void setUp() {
            System.out.println("Before Each in NestedExampleTest.");
        }

        @AfterEach
        void tearDown() {
            System.out.println("After Each in NestedExampleTest.");

        }

        @Test
        void test() {
            System.out.println("NestedExampleTest test.");
        }

        @Nested
        @DisplayName("NestedNestedExample")
        class NestedNestedExample {
            @BeforeEach
            void setUp() {
                System.out.println("Before Each in NestedNestedExample.");
            }

            @AfterEach
            void tearDown() {
                System.out.println("After Each in NestedNestedExample.");

            }

            @Test
            void test() {
                System.out.println("NestedNestedExample test.");
            }
        }
    }
}
```

---

## Test Interfaces

Junit 5 allows to use interfaces to add common properties to test classes like the previous defined Tag annotation:
```java
@Tag("example-interface")
public interface TaggedInterfaceTest {
}

class TaggedInterfaceImplTest implements TaggedInterfaceTest {
    @Test
    void tagInterfaceTest() {
        System.out.println("this is a teg interface test");
    }
}
```

In order to define in an interface some `SetUp` or `TearDown` method it is necessary to annotate the 
interface with `@TestInstance` and assign it a life cycle:
- `Lifecycle.PER_CLASS`
- `Lifecycle.PER_METHOD`
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface BeforeAllInterfaceTest {
    @BeforeAll
    default void beforeAll() {
        System.out.println("Default before all method.");
    }
}

public class BeforeAllInterfaceImpTest implements BeforeAllInterfaceTest {
    @Test
    void aTest1() {
        System.out.println("this is aTest1");
    }

    @Test
    void aTest2() {
        System.out.println("this is aTest2");
    }
}
```

## Repeating Tests

It is possible to repeat a specific test multiple times using the annotation `@RepeatedTest`:
```java
public class RepeatTestExampleTest {
    @RepeatedTest(
            value = 2,
            name = "{displayName} repetition {currentRepetition} of {totalRepetitions}"
    )
    @DisplayName("Repeated test")
    void repeatedTest() {
        System.out.println("Repeated test.");
    }
}
```

---
 
## Dependency Injection

Junit allows to inject some properties inside the test method:
- TestInfo: name, method, tags, and so on
- RepetitionInfo: info about the test repetition
- TestReporter: allows to push runtime information for test reporting

an example base on the previous repetition topic is:
```java
@RepeatedTest(2)
void repeatedTestWithInfo(TestInfo testInfo, RepetitionInfo repetitionInfo) {
    System.out.println(testInfo.getDisplayName() + "-> " + repetitionInfo.getCurrentRepetition());
}
```

---

## Parametrized Tests

Parametrize tests feature needs an additional dependency:
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>
```

There are a lot a different sources that can be used for a parametrized test:
- ValueSource
- EnumSource
- CsvSource
- CsvFileSource
- MethodSource
- ArgumentsSource

#### ValueSource
```java
public class ParametrizedExampleTest {
    @DisplayName("Value Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @ValueSource(strings = {"First", "Second", "Third"})
    void valueSourceTest(String val) {
        System.out.println("Using val: " + val);
    }
}
```

#### EnumSource
```java
public class ParametrizedExampleTest {
    @DisplayName("Enum Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @EnumSource(EnumTestParameter.class)
    void enumSourceTest(EnumTestParameter enumVal) {
        System.out.println("Using enum value: " + enumVal.name());
    }
}
```

#### CsvSource
```java
public class ParametrizedExampleTest {
    @DisplayName("Csv Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @CsvSource({
            "First, 1",
            "Second, 2",
            "Third, 3",
    })
    void csvSourceTest(String stringVal, int intVal) {
        System.out.println("Using csv values: (" + stringVal + ", " + intVal + ")");
    }
}
```

#### CsvFileSource
```java
public class ParametrizedExampleTest {
    @DisplayName("Csv File Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @CsvFileSource(
            resources = "/input.csv",
            numLinesToSkip = 1,
            delimiter = ';'
    )
    void csvFileSourceTest(String stringVal, int intVal) {
        System.out.println("Using csv file values: (" + stringVal + ", " + intVal + ")");
    }
}
```

#### MethodSource
```java
public class ParametrizedExampleTest {
    @DisplayName("Method Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @MethodSource(value = "getArgs")
    void methodSourceTest(String stringVal, int intVal) {
        System.out.println("Using method values: (" + stringVal + ", " + intVal + ")");
    }

    static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of("First", 1),
                Arguments.of("Second", 2),
                Arguments.of("Third", 3)
        );
    }
}
```

#### ArgumentsSource
```java
public class ParametrizedExampleTest {
    @DisplayName("Arguments Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @ArgumentsSource(value = CustomArgumentsProvider.class)
    void argumentsSourceTest(String stringVal, int intVal) {
        System.out.println("Using class values: (" + stringVal + ", " + intVal + ")");
    }
}

public class CustomArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("First", 1),
                Arguments.of("Second", 2),
                Arguments.of("Third", 3)
        );
    }
}
```


---

## Unit vs Integration Tests

JUnit differentiates between Unit and Integration Tests.
Unit tests finish the class name with Test, integration tests with IT. 

This differentiation allows to run only certain test during maven goals. 

---

## Extensions

Extensions can be used from third parties to extend junit base api.
```java
@ExtendWith(ExtensionExample.class)
public class ExtensionExampleTest {
    @Test
    void aTest() {
        System.out.println("Extension Test.");
    }
}
```

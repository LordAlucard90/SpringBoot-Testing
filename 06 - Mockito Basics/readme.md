# Mockito Basics

## Content

- [Introduction](#introduction)
- [Maven Dependencies](#maven-dependencies)
- [Inline Mocks](#inline-mocks)
- [Annotation Mocks](#annotation-mocks)
- [Junit Mockito Extension](#junit-mockito-extension)
- [Mocks Injection](#mocks-injection)
- [Verify Mocks Interaction](#verify-mocks-interaction)
- [Returning Values Form Mocks](#returning-values-form-mocks)
- [Argument Matchers](#argument-matchers)

---

## Introduction

Mockito is the most popular mocking framework for java.
Mockito has the following types of mocks:
- **Dummy**: objects used to get the code compile.
- **Fake**: object with an implementation not ready for production.
- **Stub**: object with pre-define answers to method calls.
- **Mock**: object with predefined answers and excepted executions.
  Can throw an exception if detect an unexpected invocation.
- **Spy**: object that wraps the actual object.

There are some operations that can be performed on and with mocks:
- **Verify**: used to check the number of times a mock is called
- **Argument Matcher**: used to match the data passed to a mocked method.
- **Argument Captor**: used to captured arguments passed to a mocked method.

Some basic annotations:
- **@Mock**: creates a mock.
- **@Spy**: creates a spy.
- **@InjectMocks**: injects mock/spys into the class under test.
- **@Captor**: captures arguments to mock.

---

## Maven Dependencies

The dependencies needed for mockito are:
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
</dependency>

```

---

## Inline Mocks

It is possible to mock a class inline in this way:

```java
class InlineMockitoExampleTest {

    @Test
     void mapMockTest() {
        Map mapMock = mock(Map.class);
        assertEquals(0, mapMock.size());
    }
}
```

Some basic answers are already configured.

---

## Annotation Mocks

It is possible to define an instance mock variable that will be initialized 
as mock with either the `@mock` annotation and the mock setup call in the `setUp` method:
```java
class AnnotationMockitoExampleTest {
    @Mock
    Map<String, Object> mapMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void mapMockTest() {
        assertEquals(0, mapMock.size());
    }
}
```

---

## Junit Mockito Extension

It is possible to init all the mocks inside a class automatically just adding the Mockito extension to the class:
```java
@ExtendWith(MockitoExtension.class)
class JunitMockitoExtensionExampleTest {
    @Mock
    Map<String, Object> mapMock;

    @Test
    void mapMockTest() {
        assertEquals(0, mapMock.size());
    }
}
```

---

## Mocks Injection

Using the annotation `@InjectMocks` it is possible to automatically injects the needed mocks: 
```java
@ExtendWith(MockitoExtension.class)
class MocksInjectionExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void injectMockTest() {
        testedClass.testMe();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMe() {
            injectedClass.doSomething();
        }
    }

    private static class InjectedClass {
        public void doSomething() {
            // dummy code
        }
    }
}
```

---

## Verify Mocks Interaction

The `verify()` method allows checking if a certain method has been called a specific number of times:
```java
@ExtendWith(MockitoExtension.class)
class VerifyMocksInteractionExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void verifyMockInteractionTest() {
        testedClass.testMe();
        verify(injectedClassMock, times(1)).doSomething();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMe() {
            injectedClass.doSomething();
        }
    }

    private static class InjectedClass {
        public void doSomething() {
            // dummy code
        }
    }
}
```
It is possible to specify an exact number, at least or at most a certain number or even never.

---

## Returning Values Form Mocks

Mockito allows to return data depending on the test needs:
```java
@ExtendWith(MockitoExtension.class)
class ReturningValuesFormMocksExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void evenTest() {
        when(injectedClassMock.generateAnIntValue()).thenReturn(2);

        assertTrue(testedClass.isEven());
    }

    @Test
    void oddTest() {
        when(injectedClassMock.generateAnIntValue()).thenReturn(1);

        assertFalse(testedClass.isEven());
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public boolean isEven() {
            return injectedClass.generateAnIntValue() % 2 == 0;
        }
    }

    private static class InjectedClass {
        public int generateAnIntValue() {
            return 0;
        }
    }
}
```

---

## Argument Matchers

Mockito allows checking the type parameters passed to a mocked method with the ArgumentMatchers:
```java
@ExtendWith(MockitoExtension.class)
class ArgumentMatcherExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void valueIsForwarded() {
        int baseValue = 42;

        testedClass.forwardAnIntValue(baseValue);

        verify(injectedClassMock, times(1)).processAValue(anyInt());
        verify(injectedClassMock, never()).processAValue(anyFloat());
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void forwardAnIntValue(int value) {
            injectedClass.processAValue(value * 2);
        }
    }

    private static class InjectedClass {
        public void processAValue(int value) {
            // do something here'
        }

        public void processAValue(float value) {
            // do something here'
        }
    }
}
```
There are a lot of already defined matchers, and it is possible also to create matchers for specific classes.

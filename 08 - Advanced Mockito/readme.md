# Advanced Mockito

## Content 

- [Throwing Exceptions](#throwing-exceptions)
- [Lambda Argument Matcher](#lambda-argument-matcher)
- [Argument Capture](#argument-capture)
- [Answers](#answers)
- [Verify Interactions Order](#verify-interactions-order)
- [Verify Interactions Within Specified Time](#verify-interactions-within-specified-time)
- [Verify Zero Or No More Interactions](#verify-zero-or-no-more-interactions)
- [Spies](#spies)

--- 

## Throwing Exceptions

It is possible to mock the generation of an exception with mockito in this way:
```java
@ExtendWith(MockitoExtension.class)
class ThrowingExceptionsExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void throwsAnExceptionTest() {
        doThrow(new RuntimeException("Test")).when(injectedClassMock).mockedMethod();

        assertThrows(
                RuntimeException.class,
                () -> testedClass.testMethod()
        );
    }

    @Test
    void throwsAnExceptionBDDTest() {
        // given
        willThrow(new RuntimeException("Test")).given(injectedClassMock).mockedMethod();

        // then
        assertThrows(
                RuntimeException.class,
                // when
                () -> testedClass.testMethod()
        );
        then(injectedClassMock).should().mockedMethod();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod() {
            injectedClass.mockedMethod();
        }
    }

    private static class InjectedClass {
        public void mockedMethod() { }
    }
}
```

--- 

## Lambda Argument Matcher

Mockito allows choosing which value return using an argument mather,
by default the matcher throws an exception id there is no match,
in order to avoid this behaviour it is possible to set `lenient = true`:
```java
@ExtendWith(MockitoExtension.class)
class LambdaArgumentMatcherExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void argumentMatchBDDTest() {
        // given
        final String expectedParam = "expected";

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = expectedParam;

        given(injectedClassMock.mockedMethod(argThat(argument -> argument.param.equals(expectedParam)))).willReturn(expectedParam);

        // when
        var response = testedClass.testMethod(argumentClass);

        // then
        then(injectedClassMock).should().mockedMethod(any());
        assertEquals(expectedParam, response);
    }

    @Test
    void argumentDoesNotMatchBDDTest() {
        // given
        final String expectedParam = "expected";

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = "not" + expectedParam;

        given(injectedClassMock.mockedMethod(argThat(argument -> argument.param.equals(expectedParam)))).willReturn(expectedParam);

        // when
        var response = testedClass.testMethod(argumentClass);

        // then
        then(injectedClassMock).should().mockedMethod(any());
        assertNull(response);
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public String testMethod(ArgumentClass argumentClass) {
            return injectedClass.mockedMethod(argumentClass);
        }

        private static class ArgumentClass {
            private String param;
        }
    }

    private static class InjectedClass {
        public String mockedMethod(TestedClass.ArgumentClass argumentClass) { return ""; }
    }
}
```

--- 

## Argument Capture

Mockito allows to capture a value passed to a mock in order to verify some property on it in this way:  
```java
@ExtendWith(MockitoExtension.class)
class ArgumentCaptureExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Captor
    private ArgumentCaptor<TestedClass.ArgumentClass> argumentClassCaptorAnnotated;

    @Test
    void argumentCaptureBDDTest() {
        // given
        final String expectedParam = "param";
        final ArgumentCaptor<TestedClass.ArgumentClass> argumentClassCaptor = ArgumentCaptor.forClass(TestedClass.ArgumentClass.class);

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = expectedParam;

        given(injectedClassMock.mockedMethod(argumentClassCaptor.capture())).willReturn(expectedParam);

        // when
        testedClass.testMethod(argumentClass);

        // then
        TestedClass.ArgumentClass captured = argumentClassCaptor.getValue();
        assertEquals(argumentClass, captured);
    }

    @Test
    void argumentCaptureWithAnnotationBDDTest() {
        // given
        final String expectedParam = "param";

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = expectedParam;

        given(injectedClassMock.mockedMethod(argumentClassCaptorAnnotated.capture())).willReturn(expectedParam);

        // when
        testedClass.testMethod(argumentClass);

        // then
        TestedClass.ArgumentClass captured = argumentClassCaptorAnnotated.getValue();
        assertEquals(argumentClass, captured);
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public String testMethod(ArgumentClass argumentClass) {
            return injectedClass.mockedMethod(argumentClass);
        }

        private static class ArgumentClass {
            private String param;
        }
    }

    private static class InjectedClass {
        public String mockedMethod(TestedClass.ArgumentClass argumentClass) { return ""; }
    }
}
```

--- 

## Answers

Answers are a powerful way to put logic inside the behaviour of a mock: 
```java
@ExtendWith(MockitoExtension.class)
class AnswersExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Captor
    private ArgumentCaptor<TestedClass.ArgumentClass> argumentClassCaptorAnnotated;

    // mock data
    private TestedClass.ArgumentClass argumentClass;

    @BeforeEach
    void setUp() {
        // given
        argumentClass = new TestedClass.ArgumentClass();

        given(injectedClassMock.mockedMethod(argumentClassCaptorAnnotated.capture()))
                .willAnswer(invocation -> {
                    TestedClass.ArgumentClass argument = invocation.getArgument(0);
                    if (argument.param == null) {
                        throw new RuntimeException("Must be not null");
                    }
                    return argument.param;
                });
    }

    @Test
    void answerBDDTest() {
        // given
        argumentClass.param = "param";

        // when
        testedClass.testMethod(argumentClass);

        // then
        TestedClass.ArgumentClass captured = argumentClassCaptorAnnotated.getValue();
        assertEquals(argumentClass, captured);
    }

    @Test
    void answerThrowsBDDTest() {
        // given
        argumentClass.param = null;

        // then
        assertThrows(
                RuntimeException.class,
                // when
                () -> testedClass.testMethod(argumentClass)
        );
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public String testMethod(ArgumentClass argumentClass) {
            return injectedClass.mockedMethod(argumentClass);
        }

        private static class ArgumentClass {
            private String param;
        }
    }

    private static class InjectedClass {
        public String mockedMethod(TestedClass.ArgumentClass argumentClass) { return ""; }
    }
}
```

--- 

## Verify Interactions Order

When some operation must follow a certain sequence it is possible to verify the order in this way:
```java
@ExtendWith(MockitoExtension.class)
class VerifyInteractionsOrderExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void orderBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).first();
        willDoNothing().given(injectedClassMock).second();

        InOrder inOrder = Mockito.inOrder(injectedClassMock);

        // when
        testedClass.testMethod();

        // then
        inOrder.verify(injectedClassMock).first();
        inOrder.verify(injectedClassMock).second();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod() {
            injectedClass.first();
            injectedClass.second();
        }
    }

    private static class InjectedClass {
        public void first() { }
        public void second() { }
    }
}
```

--- 

## Verify Interactions Within Specified Time

When a certain operation has to be performed before a certain amount of time it is possible to use a timeout:
```java
@ExtendWith(MockitoExtension.class)
class VerifyInteractionsWithinSpecifiedTimeExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void interactionsWithinSpecifiedTimeBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).someMethod();

        // when
        testedClass.testMethod();

        // then
        then(injectedClassMock).should(timeout(100)).someMethod();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod() {
            injectedClass.someMethod();
        }
    }

    private static class InjectedClass {
        public void someMethod() { }
    }
}
```

--- 

## Verify Zero Or No More Interactions

It is possible to check that after a certain execution there are no more interaction with a mock in this way:
```java
@ExtendWith(MockitoExtension.class)
class VerifyZeroOrNoMoreInteractionExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void noMoreInteractionsBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).positive();

        // when
        testedClass.testMethod(true);

        // then
        then(injectedClassMock).should().positive();
        verifyNoMoreInteractions(injectedClassMock);
    }

    @Test
    void noInteractionsBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).positive();

        // when
        testedClass.testMethod(false);

        // then
        verifyNoInteractions(injectedClassMock);
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod(boolean condition) {
            if (condition) {
                injectedClass.positive();
            }
            // do nothing
        }
    }

    private static class InjectedClass {
        public void positive() { }
    }
}
```

--- 

## Spies

Spies are mocks that can provide a more control to on the mocking strategy for specific use cases:
```java
@ExtendWith(MockitoExtension.class)
class SpiesExampleTest {
    @Spy
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void spyRealMethodBDDTest() {
        // given
        String test = "test";
        given(injectedClassMock.method(anyString())).willCallRealMethod();

        // when
        String response = testedClass.testMethod(test);

        // then
        then(injectedClassMock).should().method(anyString());
        assertEquals("_" + test + "_", response);
    }

    @Test
    void spyMockBDDTest() {
        // given
        String test = "test";
        given(injectedClassMock.method(anyString())).willReturn("something else");

        // when
        String response = testedClass.testMethod(test);

        // then
        then(injectedClassMock).should().method(anyString());
        assertNotEquals("_" + test + "_", response);
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public String testMethod(String string) {
            return injectedClass.method(string);
        }

        private static class ArgumentClass {
            private String param;
        }
    }

    private static class InjectedClass {
        public String method(String string) { return "_" + string + "_"; }
    }
}
```


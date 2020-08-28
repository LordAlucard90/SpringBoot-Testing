# Behaviour Driven Development

## Content 

- [Introduction](#introduction)
- [BDDMockito](#bddmockito)
- [BDD Verification](#bdd-verification)

--- 

## Introduction

BDD was introduced to help developers work with TDD. 

In BDD tests are often written in given-when-then form:
- Given: the setup
- When: action (method) of the test
- Then: verification of expected result

Mockito supports BDD in BDDMockito class.

--- 

## BDDMockito

The main functionalities of BDDMockito are very similar to the standard ones:

```java
@ExtendWith(MockitoExtension.class)
class BDDMockitoExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void evenTest() {
        // given
        // when(injectedClassMock.generateAnIntValue()).thenReturn(2);
        given(injectedClassMock.generateAnIntValue()).willReturn(2);

        // when
        boolean isEven = testedClass.isEven();

        // then
        assertTrue(isEven);
    }

    @Test
    void oddTest() {
        // given
        // when(injectedClassMock.generateAnIntValue()).thenReturn(1);
        given(injectedClassMock.generateAnIntValue()).willReturn(1);

        // when
        boolean isEven = testedClass.isEven();

        // then
        assertFalse(isEven);
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

## BDD Verification

As the standard usage it is possible to redefine the verification in a BDD style:

```java
@ExtendWith(MockitoExtension.class)
class BDDVerificationExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void evenTest() {
        // given
        given(injectedClassMock.generateAnIntValue()).willReturn(2);

        // when
        boolean isEven = testedClass.isEven();

        // then
        assertFalse(isEven);
        // verify(injectedClassMock, times(1)).generateAnIntValue();
        then(injectedClassMock).should(times(1)).generateAnIntValue();
        then(injectedClassMock).shouldHaveNoMoreInteractions();
    }

    @Test
    void oddTest() {
        // given
        given(injectedClassMock.generateAnIntValue()).willReturn(1);

        // when
        testedClass.isEven();

        // then
        // verify(injectedClassMock, times(1)).generateAnIntValue();
        then(injectedClassMock).should(times(1)).generateAnIntValue();
        then(injectedClassMock).shouldHaveNoMoreInteractions();
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

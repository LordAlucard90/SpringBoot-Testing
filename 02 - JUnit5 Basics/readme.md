# JUnit5 Basics

## Content

- [Assertions](#assertions)
- [Grouped Assertions](#grouped-assertions)
- [Skipping Tests](#skipping-tests)
- [Test Naming](#test-naming)
- [Expected Exceptions](#expected-exceptions)
- [Timeouts](#timeouts)
- [Assumptions](#assumptions)
- [Conditional Tests](#conditional-tests)
- [](#)
- [](#)

---

## Assertions

Assertions are used to asserting some condition that must be true to pass the test.
Some basic assertions are:
- assertEquals
- assertNotEquals
- assertNull
- assertNotNull
- assertTrue
- assertFalse

It is possible to add some text to display aside a assertion failure.
```java
assertTrue(false, "Should be true.");
```

JUnit5 support also lambda expressions in certain assertions like:
- AssertThrows
- assertTimeout

There are also assertion frameworks for generic or specific problems, for example json:
- AssertJ
- Hamcrest
- Truth

---

## Grouped Assertions

It is possible to do only one assertion to group all the assertions in a test,
this can help to list all the errors in the test and not just the first one that fails:
```java
assertAll(
        "Grouped assertions",
        () -> assertEquals(...),
        () -> assertTrue(...),
        ...
);
```

If there are different objects tested in the same test, it is possible to use a two-level assert all 
to divide the output when there is a failure:
```java
assertAll(
        "Grouped assertions",
        () -> assertAll(
                "First Object",
                () -> assertEquals(...)),
                () -> assertTrue(...),
                ...
        ),
        () -> assertAll(
                "Second Object",
                () -> assertEquals(...),
                () -> assertTrue(...),
                ...
        )
);
```

---

## Skipping Tests
It is possible to skip a test or an entire test class using the `@Disabled` annotation:
```java
@Disabled(value = "Some optional reason.")
class DisabledClassTest {}

@Disabled
class DisabledMethodTest {
    @Disabled(value = "Some optional reason.")
    @Test
    void aTest(){}
}
```

---

## Test Naming
It is possible to increase the readability of a test using the `@DisplayName` annotation:
```java
@Test
@DisplayName(value = "Nice test name")
void uglyTestName() {}
```

---

## Expected Exceptions
It is possible to check an expected exception using `assertThrows` assertion with the expected throw exception class:
```java
@Test
void aTest() {
    assertThrows(
            SpecificException.class,
            () -> aMethod()
    );
}

```

---

## Timeouts
It is possible to check the execution time of a method with the `assertTimeout` assertion.
```java
@Test
void duration() {
    assertTimeout(
            Duration.ofMillis(100),
            () -> someRealTimeMethod()
    );
}
```
It is important to take care that the execution times can be very different between a laptop and a online busy test server.

---

## Assumptions
Assumptions allow to run conditional tests, if an assumption is false the test is skyped otherwise it is rnned.
```java
@Test
void skippableTest() {
    assumeTrue("some environment variable");
    /* ... */
}
```

---

## Conditional Tests
JUnit provides a lot of annotations that allows to enable/disable tests according to specific conditions:
- EnabledOnOs
- EnabledOnJre
- EnabledIfEnvironmentVariable(named
- EnabledIfSystemProperty
```java
@Test
@EnabledOnOs(OS.LINUX)
void onLinux() {
    System.out.println("onLinux");
}

@Test
@EnabledOnJre(JRE.JAVA_11)
void onJava11() {
    System.out.println("onJava11");
}

@Test
@EnabledIfEnvironmentVariable(named = "USER", matches = "pippo")
void onUserPippo() {
    System.out.println("onUserPippo");
}
```

---

## 


---

## 


# JUnit5 Basics

## Content

- [Assertions](#assertions)
- [Grouped Assertions](#grouped-assertions)
- [](#)
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

---

## 


---

##


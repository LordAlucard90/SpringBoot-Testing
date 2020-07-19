# Introduction to JUnit5

## Content

- [JUint](#junit)
- [Life Cycle](#life-cycle)
- [Maven](#maven)
- [Gradle](#gradle)

---

## JUnit

JUnit has three important modules:
- JUnit Platform: the main testing framework tools.
- JUnit Jupiter: extension to write extensions to Junit.
- JUnit Vintage: engine to run old junit tests.

## Life Cycle

A single JUnit test execution is composed by five different steps (optional except for test):

1. Before All: some code used to set up the class tests cases executed once at the beginning.
1. Before Each: some code used to set up the environment for each test.
1. Test: the actual test.
1. After Each: some code used to reset the environment after each test.
1. After All: some code used to reset the environment after that al the tests have been executed.

## Maven

It is possible to run all the tests with maven using the command
```bash
maven clean test
```
It is possible to run this command in three different ways:
- using the system maven (if installed).
- using a local maven version (if present in the repository).
- using IntelliJ Maven Plugin (if installed).

## Gradle

It is possible to do the same also with Gradle but I'll not write it because I prefer Maven.

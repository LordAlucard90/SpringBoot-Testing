# Migrating From JUnit4 to 5

## Content

- [Main Differences](#main-differences)
- [Dependencies And Configuration](#dependencies-and-configuration)
- [Converting Tests](#converting-tests)
- [Removing JUnit4](#removing-junit4)

--- 

## Main Differences

A main differences between JUnit 4 and 5 are the Test annotations:
- `@Before` -> `@BeforeEach`
- `@After` -> `@AfterEach`
- `@BeforeClass` -> `@BeforeAll`
- `@AfterClass` -> `@AfterAll`
- `@Ignored` -> `@Disabled`
- `@Category` -> `@Tag`
- `@RunWith(Runner.class)` -> `@ExtendWith(Extension.class)`
- `@Test(expected = Exception.class)` -> `Assertions.assertThrows(Exception.class...)`
- `@Test(timeout = 1)` -> `Assertions.assertTimeout(Duration...)`

It is possible to run JUnit4 test in JUnit 5 using `junit-vintage-engine`.

It is needed java 8 or higher.

Some annotations as `@Rule` and `@ClassRule` are replaced by `@ExtendWith`,
but there is an experimental library to support them natively `junit-jupiter-migrationsupport`.

---

## Dependencies And Configuration

The main dependencies to add are junit api and engine:
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.5.2</version>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.5.2</version>
</dependency>
```
then it is possible to add the JUnit4 support:
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <version>${junit.jupiter.version}</version>
</dependency>
```
and in the end add to build plugins surfire and failsafe:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>2.22.2</version>
</plugin>
```

---

## Converting Tests

In order to convert the tests it is necessary to go through all the diferent
test and update the different Tag with the new ones and use the correct
junit5 `@Test` package.

---

## Removing JUnit4

After the migration it is possible to remove the junit4 dependencies.

For example with spring framework it is necessary to exclude it from the test starter:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <version>2.3.1.RELEASE</version>
    <exclusions>
        <exclusion>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

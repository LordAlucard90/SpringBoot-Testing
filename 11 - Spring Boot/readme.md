# Spring Boot

## Content 

- [Spring Boot Test](#spring-boot-test)
- [Context Load Test](#context-load-test)

--- 

## Spring Boot Test

The main Spring Boot Test dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```
comes with a lot test dependencies:
- Junit
- Spring Test
- AssertJ
- Hamcrest
- JSONAssert
- JsonPath

The following annotations can be used:
- `@SpringBootTest`: enables Spring context
- `@ExtendWith(SpringExtension.class)`: Spring 
- `@SpringBootConfiguration`: includes `@SpringBootApplication`
- `@TestComponet`
- `@TestConfiguration`
- `@LocalServerPort`
- `@MockBean`
- `@SpyBean`

By default, the web environment is not loaded, to do so a port must be provided: 
`@SpringBootTest(webEnvironment=<option>)`, where option can be:
- MOCK
- RANDOM_PORT
- DEFINED_POST
- NONE

By default, everything is loaded, but it possible to load only specific parts using slices:
- `@DataJdbcTest`
- `@DataJpaTest`
- `@DataLdpaTest`
- `@DataMongoTest`
- `@DataNeo4jTest`
- `@DataRedisTest`
- `@JdbcTest`
- `@JooqTest`
- `@JsonTest`
- `@RestClientTest`
- `@WebFluxTest`
- `@WebMvcTest`
 
--- 

## Context Load Test

The first test is the context load test:
```java
@SpringBootTest
class MainApplicationTest {
    @Test
    void contextLoads() { }
}
```
this is enough to ensure the context loads correctly.

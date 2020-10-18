# Wiremock

## Content 

- [Intro](#intro)
- [Standalone](#standalone)
- [Recording](#recording)
- [Testing](#testing)

--- 

## Intro

Wiremock is a tool similar to mockito used appositely for http requests.

It can be configured with stubbed responses base on URL, header and/or body matcher.

It can be also used for requests verification and to run unit tests.  

--- 

## Standalone

[Wiremock Standalone](http://wiremock.org/docs/running-standalone/) can be configured with json files in oder to return 
stub responses given a request.
```shell script
java -jar wiremock-standalone-2.27.2.jar
```
Once wiremock is started it will create two directories:
- `__files`: for serving static files
- `mappings`: including json files with mock requests and responses

At this time calling the actuator's heald andpoint, the response should be:
```shell script
curl -get http://localhost:8080/actuator/health
# No response could be served as there are no stub mappings in this WireMock instance.
```
Given a stub configuration like:
```json
{
  "id": "013c235a-b309-46ce-a468-65ccc370c57c",
  "request": {
    "url": "/v1/examples/1",
    "method": "GET"
  },
  "response": {
    "status": 200,
    "body": "{\"id\":1, \"example\":\"First Example\"}"
  },
  "uuid": "013c235a-b309-46ce-a468-65ccc370c57c"
}
```
the response now is:
```shell script
curl -get http://localhost:8080/v1/examples/1
# {"id":1, "example":"First Example"}
```
--- 

## Recording

Wiremock offer the possibility to act as a proxy and in this way record and create mappings automatically.

To do so it is needed:
- open the recorder page [http://localhost:8080/__admin/recorder/](http://localhost:8080/__admin/recorder/)
- insert the target base url (just the domain)
- press record
now when a request is sent to wiremock it will be redirected to the target domain.

By clicking on stop the requests will be created in the `mappings` directory.

--- 

## Testing

It is possible also to create a mock server during a test adding the maven dependency
```xml
<dependencies>
    <dependency>
        <groupId>com.github.JensPiegsa</groupId>
        <artifactId>wiremock-extension</artifactId>
        <version>0.4.0</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
now it is possible to configure wiremock to create stub responses to a request:
```java
@ExtendWith(WireMockExtension.class)
class RestTemplateExampleHelperTest {
    @Managed
    private WireMockServer wireMockServer = with(wireMockConfig().dynamicPort());

    RestTemplateExampleHelper helper;

    @BeforeEach
    void setUp() {
        var restTemplateBuilder = new RestTemplateBuilder();
        helper = new RestTemplateExampleHelper(restTemplateBuilder, "Http://localhost:" + wireMockServer.port());
    }

    @Test
    void sendExampleTest() {
        var entity = new ExampleEntity(1L, "An example");

        wireMockServer.stubFor(post("/api/v1/examples/").willReturn(created()));

        helper.sendExample(entity);

        verify(1, postRequestedFor(urlEqualTo("/api/v1/examples/")));
    }
}
```

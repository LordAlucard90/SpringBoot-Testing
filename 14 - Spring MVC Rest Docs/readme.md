# Spring MVC Rest Docs

## Content 

- [Intro](#intro)
- [Configuration](#configuration)
- [Documenting](#documenting)
- [Documentation Generation](#documentation-generation)
- [Publish Docs](#publish-docs)

--- 

## Intro

Spring Rest Docs is a tool for generating automatically API documentation, it supports JUnit 4 and 5.

Basically tests are used to generate snippets that are elaborated by Asciidoctor and transformed into the docs.
Can be optionally used Markdown rather than Asciidoctor.

Third Party extensions include:
- `restdocs-wiremock`
- `spring-auto-restdocs`
- `restdocs-api-spec`
- etc.

--- 

## Configuration

In the pom are needed either the dependency and the related plugin:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.restdocs</groupId>
        <artifactId>spring-restdocs-mockmvc</artifactId>
        <version>2.0.5.RELEASE</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.asciidoctor</groupId>
            <artifactId>asciidoctor-maven-plugin</artifactId>
            <version>2.1.0</version>
            <executions>
                <execution>
                    <id>generate-docs</id>
                    <phase>prepare-package</phase>
                    <goals>
                        <goal>process-asciidoc</goal>
                    </goals>
                    <configuration>
                        <backend>html</backend>
                        <doctype>book</doctype>
                    </configuration>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.restdocs</groupId>
                    <artifactId>spring-restdocs-asciidoctor</artifactId>
                    <version>2.0.5.RELEASE</version>
                </dependency>
            </dependencies>
        </plugin>

    </plugins>
</build>
```
Now the controller can be annotated with the documentation extension:
```java
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(ExampleController.class)
class ExampleControllerTest {
    /* ... */
}
```
Is it important to pay attention to the right imports or the tests will not work:
```java
// wrong
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// correct
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
```

--- 

## Documenting

Finally the documentation generation can be added to the mvc test:
```java
mockMvc.perform(get("/api/v1/examples/" + exampleEntity1.getId()))
        .andExpect(status().isOk())
        .andDo(document("v1/examples/"));

```

It is possible to improve the documentation annotating:

#### Path Parameters
```java
mockMvc.perform(get("/api/v1/examples/{exampleId}", exampleEntity1.getId()))
        .andExpect(status().isOk())
        .andDo(document("v1/examples/", pathParameters(
                parameterWithName("exampleId").description("Id of the example.")
        )));
```

#### Query Parameters
````java
mockMvc.perform(
            get("/api/v1/examples/{exampleId}", exampleEntity1.getId())
                .param("isAnExample", "true"))
        .andExpect(status().isOk())
        .andDo(document("v1/examples/",
                pathParameters(
                    parameterWithName("exampleId").description("Id of the example.")
                ),
                requestParameters(
                    parameterWithName("isAnExample").description("true if it is an example.")
                )
        ));
````

#### Responses
````java
mockMvc.perform(
            get("/api/v1/examples/{exampleId}", exampleEntity1.getId())
                .param("isAnExample", "true"))
        .andExpect(status().isOk())
        .andDo(document("v1/examples/",
                pathParameters(
                    parameterWithName("exampleId").description("Id of the example.")
                ),
                requestParameters(
                    parameterWithName("isAnExample").description("true if it is an example.")
                ),
                responseFields( // all fields are needed
                         fieldWithPath("id").description("Example Id."),
                         fieldWithPath("example").description("Example content.")
                )
        ));
````

#### Requests
````java
mockMvc.perform(post("/api/v1/examples/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"id\": 0, \"example\":\"A new example.\"}"))
        .andExpect(status().isOk())
        .andDo(document("v1/examples/",
                requestFields( // all fields are needed
                         fieldWithPath("id").ignored(),
                         fieldWithPath("example").description("Example content.")
                )
        ));
````

#### Validation Constraints
First it is necessary to add a configuration file to the test resources `org/springframework/restdocs/templates/request-fields.snippet`:
```text
|===
|Path|Type|Description|Constraints

{{#fields}}
|{{path}}
|{{type}}
|{{description}}
|{{constraints}}

{{/fields}}
|===
```
Now it is possible to add the constrains info in the docs in this way:
````java
var fieldsDescriptor = new ConstrainedFields(ExampleEntity.class);

mockMvc.perform(post("/api/v1/examples/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"id\": 0, \"example\":\"A new example.\"}"))
        .andExpect(status().isOk())
        .andDo(document("v1/examples/",
                requestFields( // all fields are needed
                        fieldsDescriptor.withPath("id").ignored(),
                        fieldsDescriptor.withPath("example").description("Example content.")
                )
        ));
// ...
private static class ConstrainedFields {
    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedFields(Class<?> input) {
        this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    private FieldDescriptor withPath(String path) {
        return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                .collectionToDelimitedString(this.constraintDescriptions
                        .descriptionsForProperty(path), ". ")));
    }
}
````

#### Uri Customization
````java
@AutoConfigureRestDocs(
        uriScheme = "http",
        uriHost = "127.0.0.1",
        uriPort = 80
)
````

--- 

## Documentation Generation

In order to include the generated sources in the documentation, it is necessary to create a `asciidoc/index.adoc`
file in the main folder:
```text
= Example Service Docs
LordAlucard90;
:doctype: book
:icons: font
:source-highlighter: highlightjs

Spring REST Docs with JUnit 5 Example.

GET Example

One showing how to make a request using cURL:

include::{snippets}/v1/examples-get/curl-request.adoc[]

One showing the HTTP request:

include::{snippets}/v1/examples-get/http-request.adoc[]

And one showing the HTTP response:

include::{snippets}/v1/examples-get/http-response.adoc[]

Response Body:
include::{snippets}/v1/examples-get/response-body.adoc[]

Response Fields:
include::{snippets}/v1/examples-get/response-fields.adoc[]

NEW Example

One showing how to make a request using cURL:

include::{snippets}/v1/examples-post/curl-request.adoc[]

One showing the HTTP request:

include::{snippets}/v1/examples-post/http-request.adoc[]

And one showing the HTTP response:

include::{snippets}/v1/examples-post/http-response.adoc[]

Response Body:
include::{snippets}/v1/examples-post/response-body.adoc[]

Request Fields
include::{snippets}/v1/examples-post/request-fields.adoc[]

Response Fields:
include::{snippets}
```
It is not possible run `mcn clean compiel package` and see the documentation opening `target/generated-docs/index.html`.


--- 

## Publish Docs

It is possible to automatically serve the docs in the final jar adding the `maven-resources-plugin`:
```xml
<plugin>
    <artifactId>maven-resources-plugin</artifactId>
    <version>2.7</version>
    <executions>
        <execution>
            <id>copy-resources</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>copy-resources</goal>
            </goals>
            <configuration>
                <outputDirectory>
                    ${project.build.outputDirectory}/static/docs
                </outputDirectory>
                <resources>
                    <resource>
                        <directory>
                            ${project.build.directory}/generated-docs
                        </directory>
                    </resource>
                </resources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

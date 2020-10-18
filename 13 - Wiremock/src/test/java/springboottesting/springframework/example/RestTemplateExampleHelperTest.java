package springboottesting.springframework.example;

import com.github.jenspiegsa.wiremockextension.Managed;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

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
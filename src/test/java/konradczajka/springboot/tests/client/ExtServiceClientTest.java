package konradczajka.springboot.tests.client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ExtServiceClient.class)
@Import(ExtServiceClientConfiguration.class)
class ExtServiceClientTest {

    @Autowired
    ExtServiceClient client;

    @Autowired
    @Qualifier("extServiceRestTemplate")
    RestTemplate restTemplate;

    MockRestServiceServer server;

    @BeforeEach
    void setup() {
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void makesCorrectRequestForData() {
        server.expect(requestTo("http://ext.service/data"))
                .andExpect(method(POST))
                .andExpect(header("Content-Type", "application/json"))
                .andExpect(header("X-Custom-Secret", "xyz"))
                .andExpect(jsonPath("$.amount").value("4"))
                .andRespond(withSuccess("{\"result\":\"DDDD\"}", APPLICATION_JSON));

        var result = client.fetchData(4);

        assertThat(result).isEqualTo("DDDD");
        server.verify();
    }
}

package konradczajka.springboot.tests.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RestClientTest(ExtServiceClient)
@Import(ExtServiceClientConfiguration)
class ExtServiceClientSpec extends Specification {

    @Autowired
    ExtServiceClient client

    @Autowired
    @Qualifier("extServiceRestTemplate")
    RestTemplate restTemplate

    MockRestServiceServer server

    def setup() {
        server = MockRestServiceServer.bindTo(restTemplate).build()
    }

    def "makes correct request for data"() {
        given:
            server.expect(requestTo("http://ext.service/data"))
                    .andExpect(method(POST))
                    .andExpect(header("Content-Type", "application/json"))
                    .andExpect(header("X-Custom-Secret", "xyz"))
                    .andExpect(jsonPath('$.amount').value("4"))
                    .andRespond(withSuccess('{"result":"DDDD"}', APPLICATION_JSON))
        when:
            def result = client.fetchData(4)
        then:
            result == "DDDD"
            server.verify()
    }
}
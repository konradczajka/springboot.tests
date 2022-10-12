package konradczajka.springboot.tests.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
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
            server.expect(requestTo("http://ext.service/data?amount=4"))
                    .andExpect(header("X-Custom-Secret", "xyz"))
                    .andRespond(withSuccess("Data" * 40, MediaType.TEXT_PLAIN))
        when:
            def result = client.fetchData(4)
        then:
            result == "Data" * 40
            server.verify()
    }
}
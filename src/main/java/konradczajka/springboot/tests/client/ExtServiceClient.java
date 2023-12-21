package konradczajka.springboot.tests.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

@Component
public class ExtServiceClient {
    private final RestTemplate restTemplate;

    ExtServiceClient(@Qualifier("extServiceRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchData(int amount) {
        var headers = new HttpHeaders();
        headers.add("X-Custom-Secret", "xyz");
        headers.add("Content-Type", "application/json");

        var response = restTemplate.exchange("http://ext.service/data", POST,
                        new HttpEntity<>(new ExtRequest(amount), headers), ExtResponse.class, amount
                ).getBody();

        return response.data();
    }

    private record ExtRequest(int amount) {

    }

    private record ExtResponse(@JsonProperty("result") String data) {

    }
}

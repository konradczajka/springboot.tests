package konradczajka.springboot.tests.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ExtServiceClient {
    private final RestTemplate restTemplate;

    ExtServiceClient(@Qualifier("extServiceRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchData(int amount) {
        var headers = new HttpHeaders();
        headers.add("X-Custom-Secret", "xyz");

        return restTemplate.exchange("http://ext.service/data?amount={n}", GET,
                new HttpEntity<>(headers), String.class, amount)
            .getBody();
    }
}

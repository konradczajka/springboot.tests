package konradczajka.springboot.tests.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ExtServiceClientConfiguration {

    @Bean
    @Qualifier("extServiceRestTemplate")
    RestTemplate extServiceRestTemplate() {
        return new RestTemplate();
    }
}

package konradczajka.springboot.tests.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaxRateProviderConfiguration {

    @Bean
    TaxRateProvider taxRateProvider() {
        return new TaxRateProvider();
    }
}

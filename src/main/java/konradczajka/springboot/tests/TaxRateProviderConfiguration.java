package konradczajka.springboot.tests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaxRateProviderConfiguration {

    @Bean
    TaxRateProvider taxRateProvider() {
        return new TaxRateProvider();
    }
}

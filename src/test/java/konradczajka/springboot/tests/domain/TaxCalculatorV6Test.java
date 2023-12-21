package konradczajka.springboot.tests.domain;


import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class TaxCalculatorV6Test {

    @Autowired
    TaxCalculator calculator;

    @Autowired
    TaxRateProvider taxRateProvider;

    @ParameterizedTest(name = "tax amount for {0} priced at {1} is {2}")
    @CsvSource({
            "Marchewka,2.12,0.21",
            "Strzyżenie,45.00,4.50",
            "Długopis,8.60,0.86"
    })
    void calculatesTaxValue(String productOrService, java.math.BigDecimal price, BigDecimal expectedAmount) {
        when(taxRateProvider.taxRateFor(any())).thenReturn(10);

        var result = calculator.calculateTaxValue(productOrService, price);

        assertThat(result).isEqualTo(expectedAmount);
    }

    @TestConfiguration
    @Import(TaxCalculator.class)
    static class TestConfig {
        @Bean
        TaxRateProvider testTaxRateProvider() {
            return mock(TaxRateProvider.class);
        }
    }
}

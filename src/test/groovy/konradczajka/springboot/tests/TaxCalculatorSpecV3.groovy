package konradczajka.springboot.tests


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
class TaxCalculatorSpecV3 extends Specification {

    @Autowired
    TaxCalculator calculator

    def "tax amount for #productOrService priced at #price is #expectedAmount"(
            String productOrService, BigDecimal price, BigDecimal expectedAmount) {
        expect:
            calculator.calculateTaxValue(productOrService, price) == expectedAmount
        where:
            productOrService | price                   || expectedAmount
            "Marchewka"      | new BigDecimal("2.12")  || new BigDecimal("0.21")
            "Strzyżenie"     | new BigDecimal("45.00") || new BigDecimal("4.50")
            "Długopis"       | new BigDecimal("8.60")  || new BigDecimal("0.86")
    }

    @Configuration
    @Import(TaxCalculator)
    static class TestConfiguration {

        @Bean
        TaxRateProvider testTaxRateProvider() {
            new TaxRateProvider() {
                @Override
                int taxRateFor(String productOrService) {
                    return 10
                }
            }
        }
    }
}
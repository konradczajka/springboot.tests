package konradczajka.springboot.tests.domain


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
class TaxCalculatorSpecV4 extends Specification {

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

    @TestConfiguration
    @Import(TaxCalculator)
    static class TestConfig {

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
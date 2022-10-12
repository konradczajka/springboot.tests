package konradczajka.springboot.tests.domain

import konradczajka.springboot.tests.domain.TaxCalculator
import konradczajka.springboot.tests.domain.TaxRateProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@ContextConfiguration
class TaxCalculatorSpecV6 extends Specification {

    @Autowired
    TaxCalculator calculator

    @Autowired
    TaxRateProvider taxRateProvider

    def "tax amount for #productOrService priced at #price is #expectedAmount"(
            String productOrService, BigDecimal price, BigDecimal expectedAmount) {
        given:
            taxRateProvider.taxRateFor(_) >> 10
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
            return new DetachedMockFactory().Stub(TaxRateProvider)
        }
    }
}
package konradczajka.springboot.tests.domain

import konradczajka.springboot.tests.domain.TaxCalculator
import konradczajka.springboot.tests.domain.TaxRateProvider
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration (classes = TaxCalculator)
class TaxCalculatorSpecV5 extends Specification {

    @Autowired
    TaxCalculator calculator

    @SpringBean
    TaxRateProvider taxRateProvider = Stub()

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
}
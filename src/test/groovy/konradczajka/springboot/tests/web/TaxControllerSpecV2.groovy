package konradczajka.springboot.tests.web

import konradczajka.springboot.tests.domain.TaxCalculator
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static konradczajka.springboot.tests.domain.TaxRateProvider.ProductNotFound
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = TaxController)
class TaxControllerSpecV2 extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    TaxCalculator calculator = Mock()

    def "returns result for correct request"() {
        when:
            def result = mvc.perform(post("/tax")
                    .contentType(APPLICATION_JSON)
                    .content('{"product": "Test", "price": "10.10"}'))
        then:
            calculator.calculateTaxValue("Test", new BigDecimal("10.10")) >> new BigDecimal("12.34")
        and:
            result.andExpect(status().isOk())
                    .andExpect(content().string("12.34"))
    }

    def "returns 400 when the request is not valid"() {
        when:
            def result = mvc.perform(post("/tax")
                    .contentType(APPLICATION_JSON)
                    .content('{"price": "10.10"}')) // no product name
        then:
            result.andExpect(status().isBadRequest())
    }

    def "returns 404 when the request contains invalid product"() {
        when:
            def result = mvc.perform(post("/tax")
                    .contentType(APPLICATION_JSON)
                    .content('{"product": "XXX", "price": "10.10"}')) // no product name
        then:
            calculator.calculateTaxValue(_, _) >> { throw new ProductNotFound("XXX") }
        and:
            result.andExpect(status().isNotFound())
    }
}
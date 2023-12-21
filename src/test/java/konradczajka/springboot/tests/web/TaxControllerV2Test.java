package konradczajka.springboot.tests.web;

import konradczajka.springboot.tests.domain.TaxCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static konradczajka.springboot.tests.domain.TaxRateProvider.ProductNotFound;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaxController.class)
class TaxControllerV2Test {

    @Autowired
    MockMvc mvc;


    @MockBean
    TaxCalculator calculator;

    @Test
    void returnsResultForCorrectRequest() throws Exception {
        when(calculator.calculateTaxValue("Test", new BigDecimal("10.10")))
                .thenReturn(new BigDecimal("12.34"));

        mvc.perform(post("/tax")
                        .contentType(APPLICATION_JSON)
                        .content("{\"product\": \"Test\", \"price\": \"10.10\"}"))
                .andExpectAll(
                        status().isOk(),
                        content().string("12.34"));
    }

    @Test
    void returns400WhenTheRequestIsNotValid() throws Exception {
        mvc.perform(post("/tax")
                        .contentType(APPLICATION_JSON)
                        .content("{\"price\": \"10.10\"}")) // no product name
                .andExpect(status().isBadRequest());
    }

    @Test
    void returns404WhenTheRequestContainsNonExistingProduct() throws Exception {
        when(calculator.calculateTaxValue(any(), any()))
                .thenThrow(new ProductNotFound("XXX"));

        mvc.perform(post("/tax")
                        .contentType(APPLICATION_JSON)
                        .content("{\"product\": \"XXX\", \"price\": \"10.10\"}")) // invalid product name
                .andExpect(status().isNotFound());
    }
}

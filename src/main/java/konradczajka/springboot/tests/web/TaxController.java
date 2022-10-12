package konradczajka.springboot.tests.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import konradczajka.springboot.tests.domain.TaxCalculator;

@RestController
@RequestMapping("/tax")
@RequiredArgsConstructor
public class TaxController {
    private final TaxCalculator calculator;

    @PostMapping
    BigDecimal calculateTax(@RequestBody @Valid TaxCalculationRequest request) {
        return calculator.calculateTaxValue(request.productOrService, request.price);
    }

    @Data
    static class TaxCalculationRequest {
        @NotBlank
        @JsonProperty("product")
        private String productOrService;

        @NotNull
        @DecimalMin("0")
        private BigDecimal price;
    }
}

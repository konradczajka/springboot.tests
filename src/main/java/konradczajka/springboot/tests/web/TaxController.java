package konradczajka.springboot.tests.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import konradczajka.springboot.tests.domain.TaxCalculator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/tax")
public class TaxController {
    private final TaxCalculator calculator;

    public TaxController(TaxCalculator calculator) {
        this.calculator = calculator;
    }

    @PostMapping
    BigDecimal calculateTax(@RequestBody @Valid TaxCalculationRequest request) {
        return calculator.calculateTaxValue(request.productOrService, request.price);
    }

    record TaxCalculationRequest(
        @NotBlank
        @JsonProperty("product")
        String productOrService,

        @NotNull
        @DecimalMin("0")
        BigDecimal price
    ) {

    }
}

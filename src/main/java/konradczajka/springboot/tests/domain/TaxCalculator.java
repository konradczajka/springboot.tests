package konradczajka.springboot.tests.domain;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class TaxCalculator {
    private final TaxRateProvider taxRateProvider;

    public BigDecimal calculateTaxValue(String productOrService, BigDecimal price) {
        return new BigDecimal(taxRateProvider.taxRateFor(productOrService))
            .setScale(2, RoundingMode.HALF_EVEN)
            .divide(new BigDecimal(100), RoundingMode.HALF_EVEN)
            .multiply(price)
            .setScale(2, RoundingMode.HALF_EVEN);
    }
}

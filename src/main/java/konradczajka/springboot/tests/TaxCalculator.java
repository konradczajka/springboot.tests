package konradczajka.springboot.tests;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
class TaxCalculator {
    private final TaxRateProvider taxRateProvider;

    BigDecimal calculateTaxValue(String productOrService, BigDecimal price) {
        return new BigDecimal(taxRateProvider.taxRateFor(productOrService))
            .setScale(2, RoundingMode.HALF_EVEN)
            .divide(new BigDecimal(100), RoundingMode.HALF_EVEN)
            .multiply(price)
            .setScale(2, RoundingMode.HALF_EVEN);
    }
}

package konradczajka.springboot.tests.domain;

public class TaxRateProvider {
    public int taxRateFor(String productOrService) {
        return switch(productOrService) {
            case "Marchewka" -> 8;
            case "Strzyżenie" -> 5;
            case "Długopis" -> 23;
            default -> throw new ProductNotFound(productOrService);
        };
    }

    public static class ProductNotFound extends RuntimeException {

        public ProductNotFound(String productOrService) {
            super("No product or service with name: " + productOrService);
        }
    }
}

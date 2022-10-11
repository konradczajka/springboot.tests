package konradczajka.springboot.tests;

class TaxRateProvider {
    int taxRateFor(String productOrService) {
        return switch(productOrService) {
            case "Marchewka" -> 8;
            case "Strzyżenie" -> 5;
            case "Długopis" -> 23;
            default -> throw new IllegalArgumentException(productOrService);
        };
    }
}
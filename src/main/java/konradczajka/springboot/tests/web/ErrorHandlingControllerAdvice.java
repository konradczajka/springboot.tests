package konradczajka.springboot.tests.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import konradczajka.springboot.tests.domain.TaxRateProvider;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaxRateProvider.ProductNotFound.class)
    ResponseEntity<String> handleException(TaxRateProvider.ProductNotFound ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }
}

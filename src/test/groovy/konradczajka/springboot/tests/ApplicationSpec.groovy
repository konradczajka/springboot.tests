package konradczajka.springboot.tests

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationSpec extends Specification {

    def "context loads"() {
        expect:
            1 == 1
    }
}
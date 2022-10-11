package konradczajka.springboot.tests

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class ApplicationSpec extends Specification {

    @Autowired(required = false)
    ApplicationContext context

    def "context loads"() {
        expect:
            context
    }
}
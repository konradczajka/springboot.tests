package konradczajka.springboot.tests.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import javax.persistence.EntityManager

@DataJpaTest
class BookRepositorySpec extends Specification {

    @Autowired
    BookRepository repository
    @Autowired
    EntityManager em

    def "finds books according to the criteria"() {
        given:
            em.persist(new Book(isbn: "1", author: "Adam", title: "Bcd", pages: 100, active: true))
            em.persist(new Book(isbn: "2", author: "Adam", title: "Cde", pages: 100, active: false))
            em.persist(new Book(isbn: "3", author: "Adam", title: "Def", pages: 60, active: true))
            em.persist(new Book(isbn: "4", author: "Adam", title: "Abc", pages: 160, active: true))
            em.persist(new Book(isbn: "5", author: "Kasia", title: "Efg", pages: 300, active: true))
        when:
            def result = repository.findBooksByAuthorAndPagesGreaterThanAndActiveTrueOrderByTitle("Adam", 90)
        then:
            result.collect { it.isbn }
                    .equals(["4", "1"])
    }
}
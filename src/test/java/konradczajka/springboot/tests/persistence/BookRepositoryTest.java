package konradczajka.springboot.tests.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository repository;
    @Autowired
    EntityManager em;

    @Test
    void findsBooksMatchingCriteria() {
            em.persist(new Book("1", "Bcd", "Adam", 100, "drama", true));
            em.persist(new Book("2", "Cde", "Adam", 100, "comedy", false));
            em.persist(new Book("3", "Def", "Adam", 60, "thriller", true));
            em.persist(new Book("4", "Abc", "Adam", 160, "drama", true));
            em.persist(new Book("5", "Efg", "Kasia", 300, "thriller", true));

            var result = repository.findBooksByAuthorAndPagesGreaterThanAndActiveTrueOrderByTitle("Adam", 90);

            assertThat(result).extracting(Book::getIsbn)
                    .containsExactly("4", "1");
    }

    @Test
    void activatesTitlesByGenre() {

        em.persist(new Book("1", "Bcd", "Adam", 100, "drama", false));
        em.persist(new Book("2", "Cde", "Adam", 100, "comedy", false));
        em.persist(new Book("3", "Def", "Adam", 60, "thriller", false));
        em.persist(new Book("4", "Abc", "Adam", 160, "drama", false));
        em.persist(new Book("5", "Efg", "Kasia", 300, "thriller", false));

        repository.activateGenre("thriller", true);

        assertThat(repository.findAllByActiveIsTrue()).extracting(Book::getIsbn)
                .containsOnly("3", "5");
    }
}

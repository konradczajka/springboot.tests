package konradczajka.springboot.tests.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findBooksByAuthorAndPagesGreaterThanAndActiveTrueOrderByTitle(String author, int minPages);

    @Modifying
    @Query("update Book b set b.active = ?2 where b.genre = ?1")
    void activateGenre(String genre, boolean active);
}

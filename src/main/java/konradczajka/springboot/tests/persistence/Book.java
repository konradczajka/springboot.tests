package konradczajka.springboot.tests.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(of = "isbn")
public class Book {
    @Id
    private String isbn;
    private String title;
    private String author;
    private int pages;
    private String genre;
    private boolean active;
}

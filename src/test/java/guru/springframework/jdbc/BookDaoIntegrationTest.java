package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = "guru.springframework.jdbc.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setTitle("TestTitle");

        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setTitle("T");
        Author author = new Author();
        author.setId(3L);
        book.setAuthor(author);

        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("TitleToUpdate");
        Book updated = bookDao.updateBook(saved);

        assertThat(updated.getTitle()).isEqualTo("TitleToUpdate");
    }

    @Test
    void testSaveNewBook() {
        Book book = new Book();
        book.setTitle("NewTitle");
        Author author = new Author();
        author.setId(3L);
        book.setAuthor(author);

        Book saved = bookDao.saveNewBook(book);
        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookById() {
        Book book = bookDao.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookByTitle() {
        Book Book = bookDao.getByTitle("Clean Code");
        assertThat(Book).isNotNull();
    }
}

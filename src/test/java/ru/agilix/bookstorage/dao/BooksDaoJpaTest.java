package ru.agilix.bookstorage.dao;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(BooksDaoJpa.class)
public class BooksDaoJpaTest {

    @Autowired
    private BooksDao bookDao;

    @Autowired
    private TestEntityManager em;

    @Test
    void getAllBooksReturnsListOfBooks() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        Author gogol = new Author(2, "Николай Гоголь");
        Genre classics = new Genre(2, "Classics");
        Book viy = Create.Book()
                .Id(2)
                .Title("Вий")
                .Description("")
                .Author(gogol)
                .Genre(classics)
                .build();

        val books = bookDao.getAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(2);
        assertThat(books).hasSize(2).matches( b -> b.contains(viy));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void createBookShouldReturnIt() {
        Book inserted = bookDao.create("bible");

        assertThat(inserted.getId()).isEqualTo(3);
        assertThat(inserted.getTitle()).isEqualTo("bible");
        assertThat(inserted.getDescription()).isNull();
        assertThat(inserted.getAuthors().get(0)).isEqualTo(new Author(0, "Unknown"));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void saveBookShouldUpdateItsFields() {
        Book book = bookDao.getById(1);

        Author newAuthor = new Author(2, "Николай Гоголь");
        Genre newGenre = new Genre(3, "Science Fiction (Sci-Fi)");

        Book updatedBook = new Book(
                book.getId(),
                "new title",
                "new description",
                List.of(newAuthor),
                List.of(newGenre)
        );

        bookDao.save(updatedBook);

        Book savedBook = bookDao.getById(1);
        assertThat(savedBook.getId()).isEqualTo(1);
        assertThat(savedBook.getTitle()).isEqualTo("new title");
        assertThat(savedBook.getDescription()).isEqualTo("new description");
        assertThat(savedBook.getAuthors().get(0)).isEqualTo(newAuthor);
        assertThat(savedBook.getGenres()).isEqualTo(List.of(newGenre));
    }

    @Test
    void shouldReturnExistingBook() {
        Book book = bookDao.getById(1);

        assertThat(book.getTitle()).isEqualTo("Война и мир");
        assertThat(book.getDescription()).startsWith("«Война́ и мир» —");
        assertThat(book.getAuthors()).size().isEqualTo(1);
        assertThat(book.getAuthors().get(0)).isEqualTo(new Author(1,"Лев Толстой"));

    }

    @Test
    void shouldRaiseExceptionForNonExistingBook() {
        assertThrows(BookNotFoundException.class, () -> bookDao.getById(-1));
    }

    @Test
    void shouldDeleteBookById() {
        val existingBook = bookDao.getById(1);
        em.detach(existingBook);
        assertThrows( BookNotFoundException.class, () -> {bookDao.delete(1); bookDao.getById(1);});
    }

    @Test
    void shouldRaiseExceptionWhenDeletingNonExistingBook() {
        assertThrows(BookNotFoundException.class, () -> bookDao.delete(-1));
    }
}
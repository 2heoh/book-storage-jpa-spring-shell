package ru.agilix.bookstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.agilix.bookstorage.dao.AuthorDao;
import ru.agilix.bookstorage.dao.BooksDao;
import ru.agilix.bookstorage.dao.CommentDao;
import ru.agilix.bookstorage.dao.GenreDao;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.ui.output.BookOutputService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CLIBooksServiceTest {

    private BooksService service;

    @Mock
    private BooksDao booksDao;

    @Mock
    private AuthorDao authorDao;

    @Mock
    private BookOutputService output;

    @Mock
    private GenreDao genreDao;

    @Mock
    private InputService input;


    @BeforeEach
    void setUp() {
        this.service = new CLIBooksService(booksDao, genreDao, authorDao, output, input);
    }

    @Test
    void shouldCreateNewBook() {
        Book book = Create.Book().Id(1).Title("title").build();
        given(input.getNewBook(any(), any())).willReturn(book);
        given(booksDao.save(book)).willReturn(book);

        service.createBook();

        verify(booksDao, times(1)).save(book);
        verify(output, times(1)).showBookCreatedMessage(book);
    }

    @Test
    void shouldShowExistingBookById() {
        Book book = Create.Book().Id(1).Title("title").Description("description").build();
        given(booksDao.getById(1)).willReturn(book);

        service.retrieveBook(1);

        verify(booksDao, times(1)).getById(1);
        verify(output, times(1)).showBookInfo(book);
    }

    @Test
    void shouldNotShowNonExistingBook() {
        willThrow(new EmptyResultDataAccessException("Not found", 1)).given(booksDao).getById(-1);

        service.retrieveBook(-1);

        verify(booksDao, times(1)).getById(-1);
        verify(output, times(0)).showBookInfo(any());
        verify(output, times(1)).showBookNotFound(-1);
    }


    @Test
    void shouldUpdateBook() {
        given(booksDao.getById(anyInt())).willReturn(Create.Book().Id(1).Title("title").build());
        Book updated = Create.Book().Id(1).Title("new title").build();
        given(input.getUpdatedBookInfo(any(), any(), any())).willReturn(updated);
        given(output.showBookInfo(any())).willReturn(updated.toString());

        String result = service.updateBook(1);

        verify(booksDao).save(any());
        assertThat(result).contains("new title");
    }

    @Test
    void shouldDeleteBookWhenBookExists() {
        Book bible = Create.Book().Title("bible").build();
        given(booksDao.getById(1)).willReturn(bible);

        service.deleteBook(1);

        verify(booksDao, times(1)).delete(1);
        verify(output, times(1)).showBookDeletedMessage(1);
        verify(output, times(0)).showBookNotFound(anyInt());
    }

    @Test
    void deletingNonExistingBookDisplaysBookNotFoundMessage() {
        given(booksDao.getById(-1)).willReturn(null);

        service.deleteBook(-1);

        verify(output, times(1)).showBookNotFound(-1);
    }

}
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
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.ui.MessageCreatorService;

import java.text.ParseException;
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
    private MessageCreatorService output;

    @Mock
    private GenreDao genreDao;

    @Mock
    private InputService input;

    @Mock
    private CommentDao commentDao;

    @BeforeEach
    void setUp() {
        this.service = new CLIBooksService(booksDao, output, authorDao, genreDao, commentDao, input);
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

    @Test
    void showAuthorsShouldDisplayListOfAuthors() {
        Author pushkin = new Author(1, "Александр Пушкин");
        Author lermontov = new Author(2, "Юрий Лермонтов");
        List<Author> list = List.of(pushkin, lermontov);
        given(authorDao.getAll()).willReturn(list);

        service.showAllAuthors();

        verify(authorDao, times(1)).getAll();
        verify(output, times(1)).showAuthorsList(list);
    }

    @Test
    void shouldShowCommentsByBookId() {
        final var comments = List.of(Create.Comment().Text("first").build());
        given(commentDao.getByBookId(1)).willReturn(comments);

        service.getCommentsByBookId(1);

        verify(commentDao, times(1)).getByBookId(1);
        verify(output, times(1)).showListOfComments(comments);
    }

    @Test
    void shouldGetNewCommentAndSaveIt() throws ParseException {

        Comment comment = Create.Comment()
                .Id(0)
                .Text("text")
                .Author("somebody")
                .Date("2020-01-19 10:00:00")
                .build();

        given(input.getNewComment(1)).willReturn(comment);

        service.addCommentByBookId(1);

        verify(input, times(1)).getNewComment(1);
        verify(commentDao, times(1)).save(comment);
    }
}
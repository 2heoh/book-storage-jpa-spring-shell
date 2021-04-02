package ru.agilix.bookstorage.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.domain.Genre;
import ru.agilix.bookstorage.service.ConsoleInputService;
import ru.agilix.bookstorage.service.InputService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConsoleInputServiceTest {

    private InputService input;

    @Mock
    private UpdateService updateService;

    @BeforeEach
    void setUp() {
        input = new ConsoleInputService(updateService);
    }

    @Test
    void shouldReturnUpdatedBook() {
        Author author = new Author(1, "author");
        Genre genre = new Genre(1, "genre");
        Book existingBook = Create.Book()
                .Id(1)
                .Title("title")
                .Description("description")
                .build();
        given(updateService.getNewValueFor("title", existingBook.getTitle())).willReturn("new title");
        given(updateService.getNewValueFor("description", existingBook.getDescription())).willReturn("new description");
        given(updateService.getNewAuthor(any())).willReturn(List.of(author));
        given(updateService.getNewGenre(any())).willReturn(genre);

        Book updatedBook = input.getUpdatedBookInfo(existingBook, new ArrayList<>(), new ArrayList<>());

        verify(updateService, times(2)).getNewValueFor(any(), any());
        verify(updateService, times(1)).getNewAuthor(any());
        verify(updateService, times(1)).getNewGenre(any());
        assertThat(updatedBook.getTitle()).isEqualTo("new title");
        assertThat(updatedBook.getDescription()).isEqualTo("new description");
    }

    @Test
    void shouldReturnNewBook() {
        Author author = new Author(1, "author");
        Genre genre = new Genre(1, "genre");
        given(updateService.getNewValueFor("title", "")).willReturn("new title");
        given(updateService.getNewValueFor("description", "")).willReturn("new description");
        given(updateService.getNewAuthor(any())).willReturn(List.of(author));
        given(updateService.getNewGenre(any())).willReturn(genre);

        Book updatedBook = input.getNewBook(new ArrayList<>(), new ArrayList<>());

        verify(updateService, times(2)).getNewValueFor(any(), any());
        verify(updateService, times(1)).getNewAuthor(any());
        verify(updateService, times(1)).getNewGenre(any());
        assertThat(updatedBook.getId()).isEqualTo(0);
        assertThat(updatedBook.getTitle()).isEqualTo("new title");
        assertThat(updatedBook.getDescription()).isEqualTo("new description");
    }


    @Test
    void shouldReturnNewComment() {
        given(updateService.getNewValueFor("text", "")).willReturn("some");
        given(updateService.getNewValueFor("author", "")).willReturn("body");

        final var comment = input.getNewComment(1);

        assertThat(comment.getId()).isEqualTo(0);
        assertThat(comment.getAuthor()).isEqualTo("body");
        assertThat(comment.getText()).isEqualTo("some");
        assertThat(comment.getBookId()).isEqualTo(1);
        assertThat(comment.getDate()).isEqualTo(null);
    }

    @Test
    void shouldReturnUpdatedComment() {
        Comment oldComment = Create.Comment()
                                        .Id(1)
                                        .BookId(2)
                                        .Text("old text")
                                        .Author("old author")
                                        .build();

        given(updateService.getNewValueFor("text", oldComment.getText())).willReturn("new text");
        given(updateService.getNewValueFor("author", oldComment.getAuthor())).willReturn("new author");

        final var comment = input.getUpdatedComment(oldComment);

        assertThat(comment.getId()).isEqualTo(1);
        assertThat(comment.getAuthor()).isEqualTo("new author");
        assertThat(comment.getText()).isEqualTo("new text");
        assertThat(comment.getBookId()).isEqualTo(2);
    }
}

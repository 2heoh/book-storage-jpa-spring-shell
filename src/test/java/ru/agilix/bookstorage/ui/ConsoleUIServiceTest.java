package ru.agilix.bookstorage.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConsoleUIServiceTest {
    private UiService ui;

    @Mock
    private UpdateService updateService;

    @BeforeEach
    void setUp() {
        ui = new ConsoleUiService(updateService);
    }

    @Test
    void shouldDisplayBooksNotFoundMessageOnEmptyBookList() {
        String result = ui.showBooksList(new ArrayList<>());

        assertThat(result).contains("Books not found.");
    }

    @Test
    void shouldDisplayListOfBooks() {
        Book bible = Create.Book().Id(1).Title("the bible").build();
        Book codeComplete = Create.Book().Id(2).Title("code complete").build();

        String result = ui.showBooksList(List.of(bible, codeComplete));

        assertThat(result)
                .contains("List of books:")
                .contains("the bible")
                .contains("code complete");
    }

    @Test
    void shouldDisplayMessageWithTitleOfInsertedBook() {
        Book bible = Create.Book().Id(1).Title("the bible").build();

        String result = ui.showBookCreatedMessage(bible);

        assertThat(result)
                .contains("Created book:")
                .contains(bible.getTitle());
    }

    @Test
    void shouldDisplayFullBookInfo() {
        Author author1 = new Author(1, "author #1");
        Author author2 = new Author(2, "author #2");
        Genre genre = new Genre(1, "genre");
        Book bible = Create.Book()
                .Id(1)
                .Title("the bible")
                .Description("some long description")
                .Author(author1)
                .Author(author2)
                .Genre(genre)
                .build();

        assertThat(ui.showBookInfo(bible))
                .contains("#1")
                .contains("the bible")
                .contains("some long description")
                .contains("author #1")
                .contains("author #2")
                .contains("genre");
    }

    @Test
    void shouldDisplayDescriptionIsNotSetForBookWithoutIt() {
        Book bible = Create.Book().Id(1).Title("the bible").build();

        assertThat(ui.showBookInfo(bible))
                .contains("#1")
                .contains("the bible")
                .contains("not set");

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
        given(updateService.getNewValueFor("title", "title")).willReturn("new title");
        given(updateService.getNewValueFor("description", "description")).willReturn("new description");
        given(updateService.getNewAuthor(any())).willReturn(author);
        given(updateService.getNewGenre(any())).willReturn(genre);

        Book updatedBook = ui.getUpdatedBookInfo(existingBook, new ArrayList<>(), new ArrayList<>());

        verify(updateService, times(2)).getNewValueFor(any(), any());
        verify(updateService, times(1)).getNewAuthor(any());
        verify(updateService, times(1)).getNewGenre(any());
        assertThat(updatedBook.getTitle()).isEqualTo("new title");
        assertThat(updatedBook.getDescription()).isEqualTo("new description");
    }


    @Test
    void shouldRenderListOfAuthors() {
        List<Author> list = List.of(
                new Author(1, "Александр Пушкин"),
                new Author(2, "Юрий Лермонтов")
        );

        String result = ui.showAuthorsList(list);

        assertThat(result)
                .contains("List of authors")
                .contains("Александр Пушкин")
                .contains("Юрий Лермонтов");
    }

    @Test
    void shouldDisplayEmptyListOfAuthors() {
        String result = ui.showEmptyAuthorsList();

        assertThat(result).contains("Authors not found.");
    }

    @Test
    void shouldDisplayListOfGenres() {
        Genre one = new Genre(1, "One");
        Genre two = new Genre(2, "Two");

        String result = ui.showGenreList(List.of(one, two));

        assertThat(result)
                .contains("List of genres")
                .contains("One")
                .contains("Two");
    }
}

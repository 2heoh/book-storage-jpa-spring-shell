package ru.agilix.bookstorage.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Genre;
import ru.agilix.bookstorage.ui.output.BookOutputService;
import ru.agilix.bookstorage.ui.output.TextBookOutputService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TextBookOutputServiceTests {
    private BookOutputService ui;

    @BeforeEach
    void setUp() {
        ui = new TextBookOutputService();
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




}

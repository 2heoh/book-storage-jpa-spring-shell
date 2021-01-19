package ru.agilix.bookstorage.ui;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.domain.Genre;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MessageCreatorServiceTests {
    private MessageCreatorService ui;

    @BeforeEach
    void setUp() {
        ui = new TextOutputService();
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

    @Test
    void showListOfComments() throws ParseException {
        Comment first = Create.Comment()
                .Id(1)
                .Author("John Doe")
                .Text("first")
                .Date("2021-01-19 10:00:00")
                .build();

        Comment second = Create.Comment()
                .Id(2)
                .Author("John Doe")
                .Text("second")
                .Date("2021-01-19 11:00:00")
                .build();

        val comments = List.of(first, second);


        val result = ui.showListOfComments(comments);

        assertThat(result)
                .contains("Comments:")
                .contains("#1 'John Doe' said at 2021-01-19 10:00:00")
                .contains("first")
                .contains("#2 'John Doe' said at 2021-01-19 11:00:00")
                .contains("second");
    }

    @Test
    void shouldDisplayNewComment() throws ParseException {
        val comment = Create.Comment()
                .Id(1)
                .Text("text")
                .Author("author")
                .Date("2021-01-19 19:00:00")
                .build();

        final var result = ui.showCommentInfo(comment);

        assertThat(result)
                .contains("New comment")
                .contains("author said at 2021-01-19 19:00:00")
                .contains("text");
    }
}

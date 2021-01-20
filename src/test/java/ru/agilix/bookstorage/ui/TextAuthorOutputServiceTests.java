package ru.agilix.bookstorage.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.ui.output.AuthorOutputService;
import ru.agilix.bookstorage.ui.output.BookOutputService;
import ru.agilix.bookstorage.ui.output.TextAuthorOutputService;
import ru.agilix.bookstorage.ui.output.TextBookOutputService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TextAuthorOutputServiceTests {

    private AuthorOutputService ui;

    @BeforeEach
    void setUp() {
        ui = new TextAuthorOutputService();
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
}

package ru.agilix.bookstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.dao.AuthorDao;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.ui.output.AuthorOutputService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CLIAuthorsServiceTest {

    @Mock
    private AuthorDao authorDao;

    private CLIAuthorsService service;

    @Mock
    private AuthorOutputService ui;

    @BeforeEach
    void setUp() {
        this.service = new CLIAuthorsService(authorDao, ui);
    }


    @Test
    void showAuthorsShouldDisplayListOfAuthors() {
        Author pushkin = new Author(1, "Александр Пушкин");
        Author lermontov = new Author(2, "Юрий Лермонтов");
        List<Author> list = List.of(pushkin, lermontov);
        given(authorDao.getAll()).willReturn(list);

        service.showAllAuthors();

        verify(authorDao, times(1)).getAll();
        verify(ui, times(1)).showAuthorsList(list);
    }
}

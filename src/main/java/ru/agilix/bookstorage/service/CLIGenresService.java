package ru.agilix.bookstorage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agilix.bookstorage.dao.GenreDao;
import ru.agilix.bookstorage.ui.output.GenreOutputService;

@Service
public class CLIGenresService implements GenresService {

    private GenreOutputService ui;
    private GenreDao genreDao;

    public CLIGenresService(GenreOutputService ui, GenreDao genreDao) {
        this.ui = ui;
        this.genreDao = genreDao;
    }

    @Override
    @Transactional(readOnly = true)
    public String showAllGenres() {
        return ui.showGenreList(genreDao.getAll());
    }
}

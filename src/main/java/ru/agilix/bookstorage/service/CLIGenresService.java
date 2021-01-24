package ru.agilix.bookstorage.service;

import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.dao.GenreDao;
import ru.agilix.bookstorage.ui.output.GenreOutputService;

@Service
public class CLIGenresService implements GenresService {
    private final GenreOutputService ui;
    private final GenreDao genreDao;

    public CLIGenresService(GenreOutputService ui, GenreDao genreDao) {
        this.ui = ui;
        this.genreDao = genreDao;
    }

    @Override
    public String showAllGenres() {
        return ui.showGenreList(genreDao.getAll());
    }
}

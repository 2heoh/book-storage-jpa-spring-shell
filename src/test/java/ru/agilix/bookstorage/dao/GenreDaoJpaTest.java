package ru.agilix.bookstorage.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    @Autowired
    GenreDao dao;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldGetAll() {
        em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);

        List<Genre> genres = dao.getAll();

        assertThat(genres)
                .contains(new Genre(0, "Unknown"))
                .contains(new Genre(1, "Action"))
                .contains(new Genre(2, "Classics"))
                .contains(new Genre(3, "Science Fiction (Sci-Fi)"))
                .size().isEqualTo(4);
    }
}
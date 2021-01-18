package ru.agilix.bookstorage.dao;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.agilix.bookstorage.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldGetAll() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val authors = authorDao.getAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
        assertThat(authors)
                .contains(new Author(0, "Unknown"))
                .contains(new Author(1, "Лев Толстой"))
                .contains(new Author(2, "Николай Гоголь"))
                .size().isEqualTo(3);
    }
}
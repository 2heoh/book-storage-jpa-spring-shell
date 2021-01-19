package ru.agilix.bookstorage.dao;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentDaoJpa.class, BooksDaoJpa.class})
class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BooksDao bookDao;
    private Comment first;
    private Comment second;

    @BeforeEach
    void setUp() throws ParseException {
        em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);

        first = Create.Comment()
                    .Id(1)
                    .Text("first")
                    .Author("Gomer Simpson")
                    .BookId(1)
                    .Date("2021-01-19 10:00:00")
                    .build();

        second = Create
                .Comment()
                .Id(2)
                .Text("second")
                .Author("Gomer Simpson")
                .Date("2021-01-19 10:01:00")
                .BookId(1)
                .build();

    }

    @Test
    void shouldGetAllCommentsByBookId() {

        val comments = commentDao.getByBookId(1);

        assertThat(comments)
                .hasSize(2)
                .contains(first)
                .contains(second);
    }

    @Test
    void shouldGetCommentsFromBook() {

        final var book = bookDao.getById(1);

        assertThat(book.getComments())
                .hasSize(2)
                .contains(first)
                .contains(second);

    }

    @Test
    void shouldReturnCommentsInReverseDateOrder() {

        val expectedOrder= List.of(second, first);

        final var comments = commentDao.getByBookId(1);

        assertThat(comments).containsExactlyElementsOf(expectedOrder);
    }

    @Test
    void shouldInsertNewComment() {

        Comment comment = Create.Comment()
                                    .Id(0)
                                    .Text("text")
                                    .Author("somebody")
                                    .BookId(1)
                                    .build();

        comment = commentDao.save(comment);

        int id = comment.getId();
        em.detach(comment);

        val saved = em.find(Comment.class, id);
        assertThat(saved.getId()).isEqualTo(id);
        assertThat(saved.getText()).isEqualTo("text");
        assertThat(saved.getAuthor()).isEqualTo("somebody");
        assertThat(saved.getBookId()).isEqualTo(1);
    }
}
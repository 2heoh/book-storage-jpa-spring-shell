package ru.agilix.bookstorage.dao;


import org.springframework.stereotype.Repository;
import ru.agilix.bookstorage.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getByBookId(int bookId) {
        TypedQuery<Comment> query = em.createQuery(
            "select c from Comment c where c.bookId=:book_id order by c.date desc",
            Comment.class
        );
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            comment.setDate(new Timestamp(System.currentTimeMillis()));
            em.persist(comment);
            return comment;
        }
        return null;
    }
}

package ru.agilix.bookstorage.dao;

import org.springframework.stereotype.Repository;
import ru.agilix.bookstorage.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BooksDaoJpa implements BooksDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {

        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Book getById(int id) {
        Book book = em.find(Book.class, id);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @Override
    public void delete(int id) {
        em.remove(getById(id));
    }
}

package ru.agilix.bookstorage.dao;

import org.springframework.stereotype.Repository;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public Book create(String title) {
        return null;
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
    public void save(Book updatedBook) {

    }

    @Override
    public void delete(int id) {

        getById(id);

        Query query = em.createQuery("delete from Book b where b.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}

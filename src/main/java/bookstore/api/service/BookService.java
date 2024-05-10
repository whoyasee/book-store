package bookstore.api.service;

import bookstore.api.exception_handling.NotFoundException;
import bookstore.api.model.Book;

import java.util.List;

public interface BookService {

    Book getById(Long id) throws NotFoundException;
    List<Book> findAll();

    void deleteBook(Long id);
    Book save(Book book);
}

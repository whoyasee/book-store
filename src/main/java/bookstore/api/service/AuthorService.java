package bookstore.api.service;

import bookstore.api.model.Author;

import java.util.List;

public interface AuthorService {

    Author getById(Long id);

    List<Author> findAll();

    Author save(Author author);

    void deleteAuthor(Long id);

    Author assignBookToAuthor(Long authorId, Long bookId);
}

package bookstore.api.service.impl;

import bookstore.api.exception_handling.NotFoundException;
import bookstore.api.model.Author;
import bookstore.api.repository.AuthorRepository;
import bookstore.api.repository.BookRepository;
import bookstore.api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id = " + id + " doesn't exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAllFetchBooks();
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author assignBookToAuthor(Long authorId, Long bookId) {
        Author author = authorRepository.findByIdFetchBooks(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id = " + authorId + " doesn't exist"));
        author.addBook(bookRepository.findByIdFetchComments(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id = " + bookId + " doesn't exist")));
        return authorRepository.save(author);
    }
}

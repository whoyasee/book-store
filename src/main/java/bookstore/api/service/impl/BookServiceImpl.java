package bookstore.api.service.impl;

import bookstore.api.exception_handling.NotFoundException;
import bookstore.api.model.Book;
import bookstore.api.repository.BookRepository;
import bookstore.api.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id = " + id + " doesn't exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll(){
        return bookRepository.findAllFetchComments();
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book save(Book book){
        return bookRepository.save(book);
    }
}

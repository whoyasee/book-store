package bookstore.api.service.impl;

import bookstore.api.exception_handling.NotFoundException;
import bookstore.api.model.BookComment;
import bookstore.api.repository.BookCommentRepository;
import bookstore.api.service.BookCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    @Autowired
    public BookCommentServiceImpl(BookCommentRepository bookCommentRepository) {
        this.bookCommentRepository = bookCommentRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public BookComment getById(Long id) {
        return bookCommentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment doesn't exist"));
    }
}

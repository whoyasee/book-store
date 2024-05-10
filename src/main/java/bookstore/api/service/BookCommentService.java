package bookstore.api.service;

import bookstore.api.model.BookComment;

public interface BookCommentService {

    BookComment getById(Long id);
}

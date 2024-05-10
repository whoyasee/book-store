package bookstore.api.controller;

import bookstore.api.dto.BookCommentDTO;
import bookstore.api.dto.BookDTO;
import bookstore.api.model.Book;
import bookstore.api.model.BookComment;
import bookstore.api.service.BookCommentService;
import bookstore.api.service.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookCommentService bookCommentService;
    private final ModelMapper modelMapper;

    public BookController(BookService bookService, BookCommentService bookCommentService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.bookCommentService = bookCommentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll(){
        return ResponseEntity.ok(bookService.findAll()
                .stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable Long id){
        return ResponseEntity.ok(modelMapper.map(bookService.getById(id), BookDTO.class));
    }


    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO){
        Book book = bookService.save(modelMapper.map(bookDTO, Book.class));
        return new ResponseEntity<>(modelMapper.map(book, BookDTO.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<BookCommentDTO>> getComments(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getById(id).getComments()
                .stream()
                .map(comment -> modelMapper.map(comment, BookCommentDTO.class))
                .collect(Collectors.toList()));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<BookCommentDTO> addComment(@PathVariable Long id,
                                                     @RequestBody @Valid BookCommentDTO bookCommentDTO) {
       Book book =  bookService.getById(id);
       bookCommentDTO.setCreatedAt(LocalDateTime.now());
       book.addComment(modelMapper.map(bookCommentDTO, BookComment.class));
       bookService.save(book);
       return new ResponseEntity<>(bookCommentDTO, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}/comments/{commentId}")
    public void removeComment(@PathVariable Long id, @PathVariable Long commentId){
        Book book = bookService.getById(id);
        book.removeComment(bookCommentService.getById(commentId));
        bookService.save(book);
    }

}

package bookstore.api.controller;

import bookstore.api.dto.AuthorDTO;
import bookstore.api.model.Author;
import bookstore.api.service.AuthorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorController(AuthorService authorService, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAll(){
        return ResponseEntity.ok(authorService.findAll()
                .stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable Long authorId){
        return ResponseEntity.ok(modelMapper.map(authorService.getById(authorId), AuthorDTO.class));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody @Valid AuthorDTO authorDTO){
        Author author = authorService.save(modelMapper.map(authorDTO, Author.class));
        return new ResponseEntity<>(modelMapper.map(author, AuthorDTO.class), HttpStatus.CREATED);    }

    @PutMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<AuthorDTO> assignBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId){
        return ResponseEntity.ok(modelMapper.map(authorService.assignBookToAuthor(authorId, bookId), AuthorDTO.class));
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthorById(@PathVariable Long authorId){
        authorService.deleteAuthor(authorId);
    }
}

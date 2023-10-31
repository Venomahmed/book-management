package com.ness.bookmanagement.bookmanagement.service.book;

import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.AuthorNotFoundException;
import com.ness.bookmanagement.bookmanagement.exception.BookNotFoundException;
import com.ness.bookmanagement.bookmanagement.exception.CannotChangeAuthorException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final BookEntityRepository bookEntityRepository;
    private final AuthorEntityRepository authorEntityRepository;

    @Autowired
    public BookServiceImpl(BookEntityRepository bookEntityRepository, AuthorEntityRepository authorEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
        this.authorEntityRepository = authorEntityRepository;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        AuthorEntity authorEntity = authorEntityRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with ID: " + bookDTO.getAuthorId()));

        BookEntity bookEntity = bookDTO.buildBookEntity();
        bookEntity.setAuthorEntity(authorEntity);

        BookEntity savedBookEntity = bookEntityRepository.save(bookEntity);
        return BookDTO.buildDTO(savedBookEntity);
    }

    @Override
    public BookDTO getBook(Long id) {
        BookEntity bookEntity = bookEntityRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
        return BookDTO.buildDTO(bookEntity);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO updatedBookDTO) {
        BookEntity existingBookEntity = bookEntityRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        if (!Objects.equals(updatedBookDTO.getAuthorId(), existingBookEntity.getAuthorEntity().getId())) {
            throw new CannotChangeAuthorException("Author of the Book cannot be changed");
        }

        existingBookEntity.setSummary(updatedBookDTO.getSummary());
        existingBookEntity.setTitle(updatedBookDTO.getTitle());
        existingBookEntity.setIsbn(updatedBookDTO.getIsbn());
        existingBookEntity.setPublicationDate(updatedBookDTO.getPublicationDate());

        BookEntity updatedBookEntity = bookEntityRepository.save(existingBookEntity);
        return BookDTO.buildDTO(updatedBookEntity);
    }

    @Override
    public void deleteBook(Long id) {
        bookEntityRepository.deleteById(id);
    }

}

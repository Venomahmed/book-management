package com.ness.bookmanagement.bookmanagement.service.book;

import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.BookNotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookEntityRepository bookEntityRepository;

    @Autowired
    public BookServiceImpl(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookDTO.buildBookEntity();
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

        existingBookEntity.setSummary(updatedBookDTO.getSummary());
        existingBookEntity.setTitle(updatedBookDTO.getTitle());

        BookEntity updatedBookEntity = bookEntityRepository.save(existingBookEntity);
        return BookDTO.buildDTO(updatedBookEntity);
    }

    @Override
    public void deleteBook(Long id) {
        bookEntityRepository.deleteById(id);
    }

}

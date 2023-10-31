package com.ness.bookmanagement.bookmanagement.respository;

import com.ness.bookmanagement.bookmanagement.entity.Author;
import com.ness.bookmanagement.bookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
}

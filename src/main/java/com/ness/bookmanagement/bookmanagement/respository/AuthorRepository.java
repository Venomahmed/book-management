package com.ness.bookmanagement.bookmanagement.respository;

import com.ness.bookmanagement.bookmanagement.entity.Author;
import com.ness.bookmanagement.bookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

package com.ness.bookmanagement.respository;

import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthorEntity(AuthorEntity authorEntity);

    Optional<BookEntity> findBooksByIsbn(String isbn);

}

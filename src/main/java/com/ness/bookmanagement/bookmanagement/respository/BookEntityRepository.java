package com.ness.bookmanagement.bookmanagement.respository;

import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByAuthorEntity(AuthorEntity authorEntity);
}

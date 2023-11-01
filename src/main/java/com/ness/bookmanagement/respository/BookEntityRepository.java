package com.ness.bookmanagement.respository;

import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthorEntity(AuthorEntity authorEntity);

    @Query("SELECT b FROM BookEntity b WHERE b.authorEntity IN :authors")
    List<BookEntity> findBooksByAuthorsIn(Set<AuthorEntity> authors);

    Optional<BookEntity> findBooksByIsbn(String isbn);
}

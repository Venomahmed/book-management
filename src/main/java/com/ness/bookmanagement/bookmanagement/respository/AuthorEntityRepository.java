package com.ness.bookmanagement.bookmanagement.respository;

import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorEntityRepository extends JpaRepository<AuthorEntity, Long> {

    List<AuthorEntity> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
}

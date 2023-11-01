package com.ness.bookmanagement.respository;

import com.ness.bookmanagement.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEntityRepository extends JpaRepository<AuthorEntity, Long> {
}

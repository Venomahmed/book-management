package com.ness.bookmanagement.respository;

import com.ness.bookmanagement.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookEntityRepositoryImpl {
    @Autowired
    private EntityManager entityManager;

    public List<BookEntity> searchBooksByCriteria(Pageable pageable,
                                                  String title,
                                                  String firstName,
                                                  String lastName) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> query = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> root = query.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%")
            );
        }

        if (firstName != null && !firstName.trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("authorEntity").get("firstName")),
                    "%" + firstName.toLowerCase() + "%")
            );
        }

        if (lastName != null && !lastName.trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("authorEntity").get("lastName")),
                    "%" + lastName.toLowerCase() + "%")
            );
        }

        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        System.out.println("OFFSET: " + offset);

        query.select(root).where(predicates.toArray(new Predicate[0]));
        return entityManager
                .createQuery(query)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(offset)
                .getResultList();
    }
}
package com.scw.electronicgradebook.dao.impl;

import com.scw.electronicgradebook.dao.GradeRepository;
import com.scw.electronicgradebook.domain.entities.Grade;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class GradeRepositoryImpl implements GradeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Grade grade) {
        entityManager.persist(grade);
    }

    @Override
    public void update(Grade grade) {
        entityManager.merge(grade);
    }

    @Override
    public void delete(Grade grade) {
        entityManager.remove(grade);
    }

    @Override
    public Optional<Grade> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Grade.class, id));
    }
}

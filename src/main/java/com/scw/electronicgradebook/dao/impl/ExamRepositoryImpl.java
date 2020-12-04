package com.scw.electronicgradebook.dao.impl;

import com.scw.electronicgradebook.dao.ExamRepository;
import com.scw.electronicgradebook.domain.entities.Exam;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ExamRepositoryImpl implements ExamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Exam exam) {
        entityManager.persist(exam);
    }

    @Override
    public void update(Exam exam) {
        entityManager.merge(exam);
    }

    @Override
    public void delete(Exam exam) {
        entityManager.remove(exam);
    }

    @Override
    public Optional<Exam> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Exam.class, id));
    }
}

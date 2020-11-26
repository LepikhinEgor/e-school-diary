package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Exam;
import com.scw.electronicgradebook.domain.entities.Lesson;
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

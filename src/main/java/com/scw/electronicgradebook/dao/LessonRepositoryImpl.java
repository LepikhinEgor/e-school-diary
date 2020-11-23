package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Lesson;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class LessonRepositoryImpl implements LessonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Lesson lesson) {
        entityManager.persist(lesson);
    }

    @Override
    public void update(Lesson lesson) {
        entityManager.merge(lesson);
    }

    @Override
    public void delete(Lesson lesson) {
        entityManager.remove(lesson);
    }

    @Override
    public Optional<Lesson> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Lesson.class, id));
    }
}

package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Lesson;

import java.util.Optional;

public interface LessonRepository {
    void create(Lesson lesson);

    void update(Lesson lesson);

    void delete(Lesson id);

    Optional<Lesson> getById(Long id);
}

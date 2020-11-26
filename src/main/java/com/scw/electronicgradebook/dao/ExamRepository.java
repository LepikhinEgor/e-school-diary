package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Exam;

import java.util.Optional;

public interface ExamRepository {
    void create(Exam exam);

    void update(Exam exam);

    void delete(Exam exam);

    Optional<Exam> getById(Long id);
}

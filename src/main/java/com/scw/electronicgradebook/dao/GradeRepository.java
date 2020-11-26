package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Grade;

import java.util.Optional;

public interface GradeRepository {

    void create(Grade grade);

    void update(Grade grade);

    void delete(Grade grade);

    Optional<Grade> getById(Long id);
}
